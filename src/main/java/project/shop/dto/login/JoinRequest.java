package project.shop.dto.login;

import lombok.Data;

@Data
public class JoinRequest {

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}
