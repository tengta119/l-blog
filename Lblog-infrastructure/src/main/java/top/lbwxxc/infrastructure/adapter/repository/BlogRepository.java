package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.infrastructure.dao.CategoryDao;
import top.lbwxxc.infrastructure.dao.po.Category;

import java.time.LocalDateTime;

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
}
