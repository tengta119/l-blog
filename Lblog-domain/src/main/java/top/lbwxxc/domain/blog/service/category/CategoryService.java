package top.lbwxxc.domain.blog.service.category;


import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.common.RedisConstants;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    @Resource
    private IBlogRepository blogRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int addCategory(String name) {
        return blogRepository.addCategory(name);
    }

    @Override
    public List<CategoryEntity> findCategoryList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate) {

        String key = RedisConstants.buildCategoryPageKey(page + name);
        List<CategoryEntity> categoryEntities;

        String categoryStr = stringRedisTemplate.opsForValue().get(key);
        if (categoryStr == null) {
            categoryEntities = blogRepository.queryCategoryList(page, pageSize, name, startDate, endDate);
            //stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(categoryEntities));
            log.info("从数据库查询文章分类：{}", categoryEntities);
        } else {
            log.info("查询文章分类命中缓存 {}", categoryStr);
            categoryEntities = JSON.parseArray(categoryStr, CategoryEntity.class);
        }

        return categoryEntities;
    }

    @Override
    public int deleteCategory(long categoryId) {
        return blogRepository.deleteCategory(categoryId);
    }
}
