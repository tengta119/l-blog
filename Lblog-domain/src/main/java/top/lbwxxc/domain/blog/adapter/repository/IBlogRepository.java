package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.CategoryEntity;

import java.time.LocalDate;
import java.util.List;

public interface IBlogRepository {

    int addCategory(String name);

    List<CategoryEntity> queryCategoryList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int deleteCategory(long categoryId);

    List<CategoryEntity> findAllCategory();

    int findCategorySize();
}
