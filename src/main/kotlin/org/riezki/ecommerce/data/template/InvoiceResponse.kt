package org.riezki.ecommerce.data.template

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvoiceResponse(
    val id: String,
    val externalId: String,
    val userId: String,
    val status: String,
    val merchantName: String,
    val merchantProfilePicture: String,
    val amount: Int,
    val description: String,
    val expiryDate: String,
    val invoiceUrl: String,
    val availableBank: List<Bank>,
    val availableRetailOutlet: List<RetailOutlet>,
    val availableEwallet: List<Ewallet>,
    val availableQrCode: List<QrCode>,
    val availableDirectDebit: List<DirectDebit>,
    val availablePaylater: List<Paylater>,
    val shouldExcludeCreditCard: Boolean,
    val shouldSendEmail: Boolean,
    val successRedirectUrl: String,
    val failureRedirectUrl: String,
    val created: String,
    val updated: String,
    val currency: String,
    val items: List<Item>,
    val fees: List<Fee>,
    val customer: Customer,
    val customerNotificationPreference: CustomerNotificationPreference,
    val metadata: Map<String, String>
) {
    data class Bank(
        val bankCode: String,
        val collectionType: String,
        val transferAmount: Int,
        val bankBranch: String,
        val accountHolderName: String,
        val identityAmount: Int
    )

    data class RetailOutlet(
        val retailOutletName: String,
    )

    data class Ewallet(
        val ewalletType: String
    )

    data class QrCode(
        val qrCodeType: String
    )

    data class DirectDebit(
        val directDebitType: String
    )

    data class Paylater(
        val paylaterType: String
    )

    data class Item(
        val name: String,
        val quantity: Int,
        val price: Int,
        val category: String,
        val url: String
    )

    data class Fee(
        val type: String,
        val value: Int
    )

    data class Customer(
        val givenName: String,
        val phoneNumber: String
    )

    data class CustomerNotificationPreference(
        val invoiceCreated: List<String>,
        val invoiceReminder: List<String>,
        val invoicePaid: List<String>
    )
}
