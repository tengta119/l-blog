package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.CategoryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICategoryService {


    int addCategory(String name);

    List<CategoryEntity> findCategoryList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate);

    int deleteCategory(long categoryId);

    List<CategoryEntity> findAllCategory();

    int findCategorySize();
}
