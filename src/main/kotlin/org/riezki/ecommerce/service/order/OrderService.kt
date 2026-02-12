package org.riezki.ecommerce.service.order

import org.riezki.ecommerce.component.ApplicationUtils
import org.riezki.ecommerce.component.JwtUtils
import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.OrderEntity
import org.riezki.ecommerce.data.entity.OrderProductEntity
import org.riezki.ecommerce.data.entity.OrderResultEntity
import org.riezki.ecommerce.data.repository.CartRepository
import org.riezki.ecommerce.data.repository.OrderProductRepository
import org.riezki.ecommerce.data.repository.OrderRepository
import org.riezki.ecommerce.data.repository.OrderResultRepository
import org.riezki.ecommerce.data.template.InvoiceRequest
import org.riezki.ecommerce.data.template.InvoiceWebhookRequest
import org.riezki.ecommerce.data.utils.CustomUserDetails
import org.riezki.ecommerce.service.invoice.InvoiceService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Date

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderResultRepository: OrderResultRepository,
    private val orderProductRepository: OrderProductRepository,
    private val cartRepository: CartRepository,
    private val jwtUtils: JwtUtils,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
    private val applicationUtils: ApplicationUtils,
    private val invoiceService: InvoiceService,
    @param:Value("\${xendit.webhook.secret}") private val xenditWebhookKeySecret: String
) {

    fun getOrders(page: Int, size: Int): PagingInfo<OrderEntity> {
        val userId = jwtUtils.userId()
        val pageNumber = if (page < 1) 1 else page
        val pageRequest = PageRequest.of(pageNumber - 1, size)
        val orders = orderRepository.findAllByUserId(userId, pageRequest)
        return PagingInfo.convertFromPage(orders)
    }

    fun createOrder(idAsString: String): OrderEntity {
        val userId = jwtUtils.userId()

        val productIds = idAsString.split(",").map { it.trim().toInt() }
        val cartEntities = productIds.map { cartRepository.findByUserIdAndProductId(userId, it) }

        val productsInOrder: MutableSet<OrderProductEntity> = HashSet()
        var amount: Double = 0.0

        for (cart in cartEntities) {
            val productInCart = cart?.product

            if (productIds.contains(productInCart?.id)) {
                amount += productInCart?.price!! * cart.quantity

                val productInOrder = OrderProductEntity(
                    productId = productInCart.id,
                    name = productInCart.name,
                    image = productInCart.image,
                    price = productInCart.price,
                    quantity = cart.quantity
                )

                productsInOrder.add(productInOrder)
            }
        }

        if (productsInOrder.isEmpty()) throw IllegalArgumentException("No products found in cart")

        val orderProductSaved = orderProductRepository.saveAll(productsInOrder)
        cartRepository.deleteByUserIdAndProductIds(userId, productIds)

        val order = OrderEntity(
            products = orderProductSaved.toSet(),
            userId = userId,
            status = "CREATED",
            amount = roundPrice(amount),
            createdAt = applicationUtils.dateIsoFormat(Date()),
            updatedAt = applicationUtils.dateIsoFormat(Date())
        )

        return orderRepository.save(order)
    }

    fun executeOrder(orderId: Int, password: String): OrderEntity {
        val userDetails = SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUserDetails

        val isPasswordValid = bcryptPasswordEncoder.matches(password, userDetails.password)

        if (!isPasswordValid) throw IllegalArgumentException("Invalid password")

        val order = orderRepository.findById(orderId).orElseThrow { IllegalArgumentException("Order not found") }

        if (order.result == null) {
            val items = order.products.map { mapFromOrderProductEntity(it) }

            val invoiceRequest = InvoiceRequest(
                externalId = "order-$orderId",
                description = "Invoice $orderId",
                amount = roundPriceLong(order.amount),
                customer = InvoiceRequest.Customer(
                    userDetails.name,
                    userDetails.username
                ),
                items = items
            )

            val invoiceResponse = invoiceService.createInvoice(invoiceRequest)

            val expiredDate = invoiceResponse.expiryDate
            val invoiceUrl = invoiceResponse.invoiceUrl

            val orderResult = OrderResultEntity(
                paymentUrl = invoiceUrl,
                expiredAt = expiredDate
            )

            val orderResultSaved = orderResultRepository.save(orderResult)

            val updatedOrder = order.copy(
                status = "UNPAID",
                result = orderResultSaved,
                updatedAt = applicationUtils.dateIsoFormat(Date())
            )

            return orderRepository.save(updatedOrder)
        } else {
            return order
        }
    }

    fun handleWebHook(webHookRequest: InvoiceWebhookRequest, callbackToken: String): OrderEntity {
        if (callbackToken.trim() != xenditWebhookKeySecret.trim()) {
            throw IllegalArgumentException("Invalid Token")
        }

        val orderId = webHookRequest.externalId.split("-")[1].toInt()
        val order = orderRepository.findById(orderId).orElseThrow { IllegalArgumentException("Order not found") }

        val updatedOrder = order.copy(
            status = webHookRequest.status,
            updatedAt = webHookRequest.paidAt
        )

        return orderRepository.save(updatedOrder)
    }

    companion object {
        fun roundPrice(price: Double): Double {
            return BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toDouble()
        }

        fun roundPriceLong(price: Double): Long {
            return BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toLong()
        }

        fun mapFromOrderProductEntity(product: OrderProductEntity): InvoiceRequest.Item {
            return InvoiceRequest.Item(
                name = product.name,
                quantity = product.quantity,
                price = roundPriceLong(product.price),
            )
        }
    }
}