package top.lbwxxc.types.common;


public class RedisConstants {

    public static final String USER_INFO = "user:info";

    public static String buildUserInfoKey(Long userId) {
        return USER_INFO + ":" + userId;
    }

    public static final String CATEGORY_INFO_PAGE = "category:info:page";
    public static String buildCategoryPageKey(int page) {
        return CATEGORY_INFO_PAGE + ":" + page;
    }
}
