package org.riezki.ecommerce.component

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.riezki.ecommerce.data.entity.UserEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtils(
    @param:Value("\${jwt.secret}") private val secret: String
) {

    fun secretKey() : SecretKey {
        val bytes = Decoders.BASE64URL.decode(secret)
        return Keys.hmacShaKeyFor(bytes)
    }

    fun extractAllClaims(token: String) : Claims {
        return Jwts.parser()
            .verifyWith(secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> extractClaim(token: String, resolver: (Claims) -> T) : T {
        val claims = extractAllClaims(token)
        return resolver(claims)
    }

    private fun isTokenExpired(token: String) : Boolean {
        val expiration = extractClaim(token) { it.expiration }
        return expiration.before(Date())
    }

    fun isValidToken(token: String, userDetails: UserDetails) : Boolean {
        val phoneNumber = extractClaim(token) { it.subject }
        return phoneNumber == userDetails.username && !isTokenExpired(token)
    }

    fun generateToken(userEntity: UserEntity) : String {
        val expiration = 1000L * 60 * 60 * 60 * 24
        return Jwts.builder()
            .subject(userEntity.phoneNumber)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey())
            .compact()
    }
}