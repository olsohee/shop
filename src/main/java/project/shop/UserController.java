package project.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.JoinRequest;
import project.shop.dto.JoinResponse;
import project.shop.jwt.JwtTokenDto;
import project.shop.dto.LoginRequest;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * [POST] /join
     * 회원가입
     */
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {

        // 가입
        User user = User.createUser(joinRequest.getUsername(), joinRequest.getEmail(),
                passwordEncoder.encode(joinRequest.getPassword()), joinRequest.getPhoneNumber());
        Long id = userService.join(user);

        // 조회
        User findUser = userService.findById(id);
        return new JoinResponse(findUser.getUsername(), findUser.getEmail(), findUser.getPhoneNumber());
    }

    /**
     * [POST] /login
     * 로그인
     */
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
