package project.shop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
