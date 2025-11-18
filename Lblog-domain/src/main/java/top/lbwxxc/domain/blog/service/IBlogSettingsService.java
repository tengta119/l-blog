package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.BlogSettingsEntity;
import top.lbwxxc.domain.blog.model.entity.ExternalUrlEntity;

import java.util.List;

public interface IBlogSettingsService {

    int updateBlogSettings(String logo, String name);

    BlogSettingsEntity getBlogSettings();

    int addExternalUrl(String name, String logo, String url);

    int updateExternalUrl(Long id, String name, String logo, String url);

    List<ExternalUrlEntity> findExternalUrlList(int page, int pageSize);

    int findExternalUrlSize();
}
