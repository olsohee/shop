package project.shop.dto.mypage;

import lombok.Data;

@Data
public class AddressRequest {

    private String name;
    private String city;
    private String street;
    private String zipcode;
}
