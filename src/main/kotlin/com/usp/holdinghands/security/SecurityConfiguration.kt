package com.usp.holdinghands.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .addFilterAfter(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user/create").permitAll()
                .antMatchers(HttpMethod.POST,"/api/user/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/user").permitAll()
                .anyRequest().authenticated()
    }
}
