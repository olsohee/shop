package project.shop.exception;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {

    private final LocalDateTime localDateTime = LocalDateTime.now();
    private String errorName;
    private String httpStatus;
    private String message;

    public static ResponseEntity<ErrorResponse> createErrorResponseEntity(CustomException e) {

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.builder()
                        .errorName(e.getErrorCode().name())
                        .httpStatus(e.getErrorCode().getHttpStatus().name())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}
