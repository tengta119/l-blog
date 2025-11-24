package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.BlogSettingsEntity;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.model.entity.ExternalUrlEntity;
import top.lbwxxc.domain.blog.model.entity.TagEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IBlogRepository {

    int addCategory(String name);

    List<CategoryEntity> queryCategoryList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate);

    int deleteCategory(long categoryId);

    List<CategoryEntity> findAllCategory();

    int findCategorySize();

    int addTag(String name);

    int addTags(List<String> tags);

    List<TagEntity> queryTagList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate);

    int deleteTag(long categoryId);

    List<TagEntity> findAllTag();

    List<TagEntity> searchTagByKey(String key);

    int findTagSize();

    int updateBlogSettings(String logo, String name);

    BlogSettingsEntity getBlogSettings();

    int addExternalUrl(String name, String logo, String url);

    int updateExternalUrl(Long id, String name, String logo, String url);

    List<ExternalUrlEntity> findExternalUrlList(int page, int pageSize);

    int findExternalUrlSize();

    BlogSettingsEntity findBlogSettings();
}
