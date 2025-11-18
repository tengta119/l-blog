package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.UpdateBlogSettingsEntity;

public interface IBlogSettingsService {

    int updateBlogSettings(String logo, String name);
}
