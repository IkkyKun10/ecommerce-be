package org.riezki.ecommerce.service.invoice

import org.riezki.ecommerce.configuration.ApplicationConfig
import org.riezki.ecommerce.data.template.InvoiceRequest
import org.riezki.ecommerce.data.template.InvoiceResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.postForEntity
import java.net.URI

@Service
class InvoiceService(
    private val applicationConfig: ApplicationConfig,
    @param:Value("\${xendit.apikey.secret}") private val xenditApiKey: String
) {
    fun createInvoice(invoiceRequest: InvoiceRequest) : InvoiceResponse {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.setBasicAuth(xenditApiKey, "")
        val request = HttpEntity<InvoiceRequest>(invoiceRequest, httpHeaders)
        val response = applicationConfig.restTemplate().postForEntity<InvoiceResponse>(
            URI.create("https://api.xendit.co/v2/invoices"),
            request
        )

        return response.body!!
    }
}