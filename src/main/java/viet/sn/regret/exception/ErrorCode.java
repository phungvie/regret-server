package viet.sn.regret.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    profile_exist(1008, "Không tìm thấy profile", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1009,"Username đã tồn tại" , HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1010,"Email đã tồn tại" , HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1011,"Vui lòng nhập username" , HttpStatus.BAD_REQUEST),
    USERNAME_invalid_length(1012,"Độ dài username phải dài từ 3 đến 255 kí tự" , HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
