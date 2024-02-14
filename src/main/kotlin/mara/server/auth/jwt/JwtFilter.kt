package mara.server.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(private val jwtProvider: JwtProvider) : OncePerRequestFilter() {
    private val ok: String = "ok"
    private val reissue: String = "reissue"

    private fun HttpServletRequest.getToken(): String? {
        val rawToken = this.getHeader("Authorization")
        return if (rawToken != null && rawToken.startsWith("Bearer"))
            rawToken.replace("Bearer ", "")
        else null
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val jwt = request.getToken()
        if (jwt != null) {
            if (jwtProvider.validate(jwt) == ok) {
                SecurityContextHolder.getContext().authentication =
                    jwtProvider.getAuthentication(jwt)
            }
            if (jwtProvider.validate(jwt) == reissue) {
                reissueAccessToken(request, response)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun reissueAccessToken(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val refreshToken = jwtProvider.validRefreshToken(parseRefresh(request, "Refresh-Token")).refreshToken
            val oldAccessToken = request.getToken()
            var newAccessToken = ""
            if (oldAccessToken != null) {
                newAccessToken = jwtProvider.recreateAccessToken(oldAccessToken)
            }
            SecurityContextHolder.getContext().authentication =
                jwtProvider.getAuthentication(newAccessToken)

            response.setHeader("New-Access-Token", newAccessToken)
            response.setHeader("Refresh-Token", refreshToken)
        } catch (e: Exception) {
            e.printStackTrace()
            request.setAttribute("exception", e)
        }
    }

    private fun parseRefresh(request: HttpServletRequest, headerName: String) = request.getHeader(headerName).replace("Bearer ", "")
}
