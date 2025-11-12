package top.lbwxxc.types.common;


public class RedisConstants {

    public static final String USER_INFO = "user:info";

    public static String buildUserInfoKey(Long userId) {
        return USER_INFO + ":" + userId;
    }
}
