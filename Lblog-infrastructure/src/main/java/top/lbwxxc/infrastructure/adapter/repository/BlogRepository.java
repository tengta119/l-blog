package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.infrastructure.dao.CategoryDao;
import top.lbwxxc.infrastructure.dao.po.Category;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogRepository implements IBlogRepository {


    @Resource
    private CategoryDao categoryDao;

    @Override
    public int addCategory(String name) {

        Category category = new Category();
        category.setName(name);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setIsDeleted(0);
        return categoryDao.insert(category);
    }

    @Override
    public List<CategoryEntity> queryCategoryList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate) {

        List<Category> categories = categoryDao.selectCategoryByPageAndData((page - 1) * pageSize, pageSize, name, startDate, endDate);
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (Category category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(category.getId());
            categoryEntity.setName(category.getName());
            categoryEntity.setCreateTime(category.getCreateTime());
            categoryEntities.add(categoryEntity);
        }

        return categoryEntities;
    }
}
