package top.lbwxxc.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CreateUserType {

    CREATE_USER_EMAIL("email"),
    CREATE_USER_PHONE("phone"),
    CREATE_USER_OPENID("openid")
    ;

    private final String type;
}
