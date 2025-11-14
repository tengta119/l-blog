package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.CategoryEntity;

import java.time.LocalDate;
import java.util.List;

public interface ICategoryService {


    int addCategory(String name);

    List<CategoryEntity> findCategoryList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int deleteCategory(long categoryId);
}
