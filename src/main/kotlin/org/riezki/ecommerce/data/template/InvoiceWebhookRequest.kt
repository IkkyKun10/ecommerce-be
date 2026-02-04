package org.riezki.ecommerce.data.template

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvoiceWebhookRequest(
    val id: String,
    val externalId: String,
    val userId: String,
    val isHigh: String,
    val paymentMethod: String,
    val status: String,
    val merchantName: String,
    val amount: Int,
    val paidAmount: Int,
    val bankCode: String,
    val paidAt: String,
    val payerEmail: String,
    val description: String,
    val adjustReceiveAmount: Int,
    val feesPaidAmount: Int,
    val updated: String,
    val created: String,
    val currency: String,
    val paymentChannel: String,
    val paymentDestination: String
)
