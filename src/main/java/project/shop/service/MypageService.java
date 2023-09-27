package project.shop.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.mypage.AddressListResponse;
import project.shop.dto.mypage.CreateAddressRequest;
import project.shop.entity.user.Address;
import project.shop.entity.user.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    @Transactional
    public AddressListResponse create(HttpServletRequest request, CreateAddressRequest dto) {

        // Address
        Address address = Address.createAddress(dto.getName(), dto.getCity(), dto.getStreet(), dto.getZipcode());

        // User
        User user = this.findUserFromRequest(request);

        // 회원 주소록에 추가
        user.addAddress(address);

        return AddressListResponse.createResponse(user.getAddresses());
    }

    @Transactional
    public AddressListResponse delete(HttpServletRequest request, Long addressId) {

        // User
        User user = this.findUserFromRequest(request);

        List<Address> addresses = user.getAddresses();
        for (Address address : addresses) {
            if(address.getId() == addressId) {
                user.getAddresses().remove(address);
                break;
            }
        }

        return AddressListResponse.createResponse(user.getAddresses());
    }

    //== private 메서드 ==//

    /*
     * request를 통해 사용자 조회
     */
    private User findUserFromRequest(HttpServletRequest request) {

        String email = jwtTokenUtils.getEmailFromHeader(request);
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
