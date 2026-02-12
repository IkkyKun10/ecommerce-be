package org.riezki.ecommerce.controller.order

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.OrderEntity
import org.riezki.ecommerce.data.req.OrderExecuteRequest
import org.riezki.ecommerce.data.template.InvoiceWebhookRequest
import org.riezki.ecommerce.service.order.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping
    fun getOrders(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ) : BaseResponse<PagingInfo<OrderEntity>> {
        val orders = orderService.getOrders(page, size)
        return BaseResponse(message = "Success", status = true, data = orders)
    }

    @PostMapping
    fun createOrder(
        @RequestParam ids: String
    ) : BaseResponse<OrderEntity> {
        val orderEntity = orderService.createOrder(ids)
        return BaseResponse(message = "Success", status = true, data = orderEntity)
    }

    @PostMapping("/execute")
    fun executeOrder(
        @RequestBody request: OrderExecuteRequest
    ) : BaseResponse<OrderEntity> {
        val orderEntity = orderService.executeOrder(request.orderId, request.password)
        return BaseResponse(message = "Success", status = true, data = orderEntity)
    }

    @PostMapping("/webhook")
    fun webhook(
        @RequestHeader(name = "x-callback-token") callbackToken: String,
        @RequestBody webhookRequest: InvoiceWebhookRequest
    ) : BaseResponse<OrderEntity> {
        val orderEntity = orderService.handleWebHook(webhookRequest, callbackToken)
        return BaseResponse(message = "Success", status = true, data = orderEntity)
    }
}