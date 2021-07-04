package com.example.userservice.security

import com.example.userservice.filter.AuthenticationFilter
import com.example.userservice.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurity(val userService: UserService, val bCryptPasswordEncoder: BCryptPasswordEncoder, val env: Environment) :
    WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http?.let {
            it.csrf().disable()
                .authorizeRequests().antMatchers("/users/**")
                .hasIpAddress("192.168.0.10")
                .and()
                .addFilter(getAuthenticationFilter())
            it.headers().frameOptions().disable()
        }
    }

    fun getAuthenticationFilter(): AuthenticationFilter {
        return AuthenticationFilter(authenticationManager(), userService, env)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userService)
            ?.passwordEncoder(bCryptPasswordEncoder)
    }
}