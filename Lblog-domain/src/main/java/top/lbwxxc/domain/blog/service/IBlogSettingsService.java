package top.lbwxxc.domain.blog.service;


public interface IBlogSettingsService {

    int updateBlogSettings(String logo, String name);

    int addExternalUrl(String name, String logo, String url);
}
