package project.shop.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenDto {

    private String refreshToken;
    private String accessToken;
}
