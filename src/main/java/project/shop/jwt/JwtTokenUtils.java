package project.shop.jwt;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.*;
import project.shop.entity.user.RefreshToken;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.RefreshTokenRepository;
import project.shop.security.CustomUserDetails;

@Component
public class JwtTokenUtils {

    private final Key signingKey;
    private final JwtParser jwtParser;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenUtils(@Value("${jwt.secret}") String secretKeyPlain, UserDetailsService userDetailsService, RefreshTokenRepository refreshTokenRepository) {

        this.signingKey = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtParser = Jwts.parserBuilder().setSigningKey(signingKey).build();;
        this.userDetailsService = userDetailsService;
    }

    /*
     * 로그인시 JWT 토큰 발급(Access, Refresh Token)
     */
    public JwtTokenDto generateToken(CustomUserDetails customUserDetails) {

        // JWT의 페이로드에 사용자의 email 저장
        Claims claims = Jwts.claims().setSubject(customUserDetails.getEmail());

        // Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 30)))
                .signWith(signingKey)
                .compact();

        // Refresh Token
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60)))
                .signWith(signingKey)
                .compact();

        return new JwtTokenDto(refreshToken, accessToken);
    }

    /*
     * 요청 헤더에서 JWT 가져오기
     */
    public String getTokenFromRequest(HttpServletRequest request) {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header != null && header.startsWith("Bearer ")) {
            return header.split(" ")[1];
        } else {
            throw new JwtException("요청 헤더에 토큰이 없습니다");
        }
    }

    /*
     * JWT 유효성 검사
     */
    public void validateToken(String token) {

        try {
            // Token 파싱
            jwtParser.parseClaimsJws(token);
        } catch (MalformedJwtException e) {
            throw new JwtException("유효하지 않은 토큰입니다");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 토큰입니다");
        } catch (io.jsonwebtoken.JwtException e) {
            throw new JwtException("잘못된 토큰입니다");
        }
    }

    /*
     * JWT 토큰을 통해 Authentication 생성
     */
    public Authentication getAuthentication(String accessToken) {

        String email = jwtParser.parseClaimsJws(accessToken).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // email, password, 권한 정보를 통해 인증이 완료된 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        return authentication;
    }

    /*
     * request -> token -> 사용자 정보(email) 획득
     */
    public String getEmailFromHeader(HttpServletRequest request) {

        String token = getTokenFromRequest(request);
        String email = jwtParser.parseClaimsJws(token).getBody().getSubject();
        return email;
    }

    /*
     * DB에 저장된 Refresh Token과 일치하는지 확인
     */
    public boolean compareRefreshToken(String email, String refreshToken) {

        RefreshToken entity = refreshTokenRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return (entity.getRefreshToken().equals(refreshToken)) ? true : false;
    }
}
