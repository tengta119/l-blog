package top.lbwxxc.types.enums.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VerificationTypeVO {

    PASSWORD("0", "password"),
    PHONE("1","phone"),
    EMAIL("2", "email")

    ;
    private final String code;
    private final String type;
}
