package top.lbwxxc.types.common;

public class Constants {

    public final static String USER_VERIFICATION_CODE = "user:verification:code";

    /**
     * 构建验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return USER_VERIFICATION_CODE + phone;
    }

    public final static String USER_ROLE = "user:role";
    public final static String USER_PERMISSION = "user:permission";
    public final static String USER_REL_ROLE_PERMISSION = "user:rel:role:permission";

    public static String buildUserRoleKey(Long userId) {
        return USER_ROLE + ":" + userId;
    }

    /**
     * 用户 ID
     */
    public final static String USER_ID = "userId";

    /**
     * Sa-Token 登录的 Token KEY 前缀
     */
    public static final String SA_TOKEN_TOKEN_KEY_PREFIX = "Authorization:login:token:";
}
