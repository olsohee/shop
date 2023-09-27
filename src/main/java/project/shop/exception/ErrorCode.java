package project.shop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다"),
    NOT_FOUND_CART_PRODUCT(HttpStatus.NOT_FOUND, "장바구니에서 해당 상품을 찾을 수 없습니다"),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문 내역을 찾을 수 없습니다"),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다"),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 패스워드입니다"),
    CANNOT_DECREASE_COUNT(HttpStatus.BAD_REQUEST, "최소 수량은 1개입니다"),
    CANNOT_INCREASE_COUNT(HttpStatus.BAD_REQUEST, "해당 상품의 최대 주문 가능 수량입니다"),
    CANNOT_ORDER_CANCEL(HttpStatus.BAD_REQUEST, "배송중이거나 배송이 완료된 상품은 취소가 불가능합니다"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
