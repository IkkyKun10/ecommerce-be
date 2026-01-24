package org.riezki.ecommerce.component

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.riezki.ecommerce.data.dto.BaseResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.sql.Date

@Component
class ExceptionUtils {

    fun sendExceptionEntryPoint(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        if (response.status == HttpServletResponse.SC_OK) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }

        val data = mapOf(
            "timestamp" to Date(System.currentTimeMillis()).toInstant().toString(),
            "error" to exception.message,
            "path" to request.servletPath
        )

        val errorResponse = BaseResponse(message = "Error on ${request.servletPath}", status = false, data = data)

        var json: String
        try {
            json = ObjectMapper().writeValueAsString(errorResponse)
        } catch (e: Exception) {
            json = errorResponse.toString()
        }

        try {
            response.writer.write(json)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}