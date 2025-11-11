package top.lbwxxc.types.common;

public class Constants {

    public final static String WXACCESS_TOKEN = "WXAccessToken";

    public final static String USER_VERIFICATION_CODE = "user:verification:code";
    /**
     * 构建验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return USER_VERIFICATION_CODE + phone;
    }
}
