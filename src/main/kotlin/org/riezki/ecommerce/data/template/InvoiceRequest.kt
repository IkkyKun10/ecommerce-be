package org.riezki.ecommerce.data.template

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvoiceRequest(
    val externalId: String = "external_id-1",
    val amount: Long = 20000,
    val description: String = "Invoice demo #123",
    val invoiceDuration: Long = 86400L,
    val customer: Customer = Customer(),
    val customerNotificationPreference: CustomerNotificationPreference = CustomerNotificationPreference(),
    val items: List<Item> = listOf(Item()),
    val fees: List<Fee> = listOf(Fee()),
    val successRedirectUrl: String = "https://yourcompany.com/success",
    val failureRedirectUrl: String = "https://yourcompany.com/failure",
    val currency: String = "IDR"

) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Customer (
        val givenNames: String = "John",
        val mobileNumber: String = "+6287774441111"
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CustomerNotificationPreference (
        val invoiceCreated: List<String> = listOf<String>("whatsapp", "email", "viber"),
        val invoiceReminder: List<String> = listOf("whatsapp", "email", "viber"),
        val invoicePaid: List<String> = listOf("whatsapp", "email", "viber")
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Item (
        val name: String = "Air Conditioner",
        val quantity: Int = 1,
        val price: Long = 100000,
        val url: String = "https://yourcompany.com/example_item"
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Fee(
        val type: String = "ADMIN",
        val value: Int = 5000
    )
}
