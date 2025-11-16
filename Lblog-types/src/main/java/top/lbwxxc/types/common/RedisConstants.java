package top.lbwxxc.types.common;


public class RedisConstants {

    public static final String USER_INFO = "user:info";

    public static String buildUserInfoKey(Long userId) {
        return USER_INFO + ":" + userId;
    }

    public static final String CATEGORY_INFO_PAGE = "category:info:page";
    public static String buildCategoryPageKey(String page) {
        return CATEGORY_INFO_PAGE + ":" + page;
    }

    public static final String TAG_INFO_PAGE = "tag:info:page";
    public static String buildTagPageKey(String page) {
        return TAG_INFO_PAGE + ":" + page;
    }
}
