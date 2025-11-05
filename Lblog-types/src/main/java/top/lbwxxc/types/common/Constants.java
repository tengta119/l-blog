package top.lbwxxc.types.common;

public class Constants {

    public final static String SPLIT = ",";

    public final static String USER_REGISTER_CODE = "user:register:code";

    /**
     * 构建验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return USER_REGISTER_CODE + phone;
    }
}
