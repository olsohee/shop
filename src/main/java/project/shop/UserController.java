package project.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.JoinRequest;
import project.shop.dto.JoinResponse;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {

        log.info("컨트롤러");
        log.info("암호화된 비밀번호: {}", passwordEncoder.encode(joinRequest.getPassword()));

        // 이메일 중복 검사
        duplicateEmail(joinRequest.getEmail());

        // 가입
        User user = User.createUser(joinRequest.getUsername(), joinRequest.getEmail(),
                passwordEncoder.encode(joinRequest.getPassword()), joinRequest.getPhoneNumber());
        Long id = userService.join(user);

        // 조회
        User findUser = userService.findById(id);
        return new JoinResponse(findUser.getUsername(), findUser.getEmail(), findUser.getPhoneNumber());
    }

    private void duplicateEmail(String email) {

        if(!userService.findByEmail(email).isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
