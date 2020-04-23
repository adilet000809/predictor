package com.example.predictor.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class SuccessHandler: AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        var redirectUrl: String? = null

        val authorities = authentication!!.authorities
        for (grantedAuthority in authorities) {
            if (grantedAuthority.authority == "ROLE_USER") {
                redirectUrl = "/"
                break
            } else if (grantedAuthority.authority == "ROLE_ADMIN") {
                redirectUrl = "/admin"
                break
            }
        }
        checkNotNull(redirectUrl)
        DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl)
    }
}