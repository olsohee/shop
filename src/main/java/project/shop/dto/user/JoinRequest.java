package project.shop.dto.user;

import lombok.Data;

@Data
public class JoinRequest {

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}
