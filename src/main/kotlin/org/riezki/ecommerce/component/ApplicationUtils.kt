package org.riezki.ecommerce.component

import org.springframework.stereotype.Component
import java.util.Date

@Component
class ApplicationUtils {
    fun dateIsoFormat(date: Date) : String = date.toInstant().toString()
}