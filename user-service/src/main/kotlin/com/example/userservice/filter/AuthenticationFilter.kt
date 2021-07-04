package com.example.userservice.filter

import com.example.userservice.Log
import com.example.userservice.service.UserService
import com.example.userservice.vo.RequestLogin
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val env: Environment
) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {
    companion object : Log

    private val objectMapper = ObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val creds: RequestLogin = objectMapper.readValue(request?.inputStream, RequestLogin::class.java)
        val token = UsernamePasswordAuthenticationToken(creds.email, creds.password, ArrayList())
        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.debug("{}", authResult?.principal)
        val user: User = authResult?.principal as User
        val userDetails = userService.getUserDetailsByEmail(user.username)

        val token: String = Jwts.builder()
            .setSubject(userDetails.userId)
            .setExpiration(Date(System.currentTimeMillis() + env.getProperty("token.expiration_time")!!.toLong()))
            .signWith(SignatureAlgorithm.HS256, env.getProperty("token.secret")!!.toByteArray())
            .compact()
        response?.addHeader("token", token)
        response?.addHeader("userId", userDetails.userId)
    }
}