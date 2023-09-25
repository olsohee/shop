package project.shop.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // 요청 헤더에 토큰이 있는지 검증
            String accessToken = jwtTokenUtils.getTokenFromRequest(request);

            // 있다면 토큰이 유효한지 검증
            jwtTokenUtils.validateToken(accessToken);

            // 유효하다면 Access Token을 Authentication로 변환 후 SecurityContext에 저장
            Authentication authentication = jwtTokenUtils.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            // JwtException을 request에 담기
            request.setAttribute("jwtException", e);
        }

        filterChain.doFilter(request, response);
    }
}
