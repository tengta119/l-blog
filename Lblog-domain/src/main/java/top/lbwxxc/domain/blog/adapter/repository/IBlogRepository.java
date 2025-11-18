package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.model.entity.TagEntity;

import java.time.LocalDate;
import java.util.List;

public interface IBlogRepository {

    int addCategory(String name);

    List<CategoryEntity> queryCategoryList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int deleteCategory(long categoryId);

    List<CategoryEntity> findAllCategory();

    int findCategorySize();

    int addTag(String name);

    int addTags(List<String> tags);

    List<TagEntity> queryTagList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int deleteTag(long categoryId);

    List<TagEntity> findAllTag();

    int findTagSize();

    int updateBlogSettings(String logo, String name);

    int addExternalUrl(String name, String logo, String url);

    int updateExternalUrl(Long id, String name, String logo, String url);
}
