package top.lbwxxc.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SelectUserType {

    SELECT_USER_PHONE("phone"),
    SELECT_USER_EMAIL("email"),
    SELECT_USER_OPENID("password"),
    SELECT_USER_ID("id")
    ;

    private final String type;
}
