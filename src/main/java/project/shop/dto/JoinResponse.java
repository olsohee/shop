package project.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinResponse {

    private String username;
    private String email;
    private String phoneNumber;
}
