package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.mypage.AddressListResponse;
import project.shop.dto.mypage.CreateAddressRequest;
import project.shop.service.MypageService;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    /**
     * [POST] /mypage/edit/address/create
     * 주소 등록 및 추가
     */
    @PostMapping("/mypage/edit/address/create")
    public AddressListResponse editAddress(HttpServletRequest request,
                                           @RequestBody CreateAddressRequest dto) {

        return mypageService.create(request, dto);
    }

//    /**
//     * [POST] /mypage/edit/address
//     * 주소 삭제
//     */
//    @PostMapping("/mypage/edit/address/create")
//    public void editAddress(HttpServletRequest request,
//                            @RequestBody CreateAddressRequest dto) {
//
//        addressService.
//    }
}
