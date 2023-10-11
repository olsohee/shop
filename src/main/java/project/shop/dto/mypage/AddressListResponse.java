package project.shop.dto.mypage;

import lombok.Data;
import project.shop.entity.user.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AddressListResponse {

    private List<AddressResponse> addressList;

    public static AddressListResponse createResponse(List<Address> addresses) {

        AddressListResponse response = new AddressListResponse();
        response.addressList = addresses.stream()
                                .map(a -> AddressResponse.createResponse(a))
                                .collect(Collectors.toList());
        return response;
    }

    @Data
    static class AddressResponse {

        private String name;
        private String city;
        private String street;
        private String zipcode;

        public static AddressResponse createResponse(Address address) {

            AddressResponse response = new AddressResponse();
            response.name = address.getName();
            response.city = address.getCity();
            response.street = address.getStreet();
            response.zipcode = address.getZipcode();
            return response;
        }
    }
}
