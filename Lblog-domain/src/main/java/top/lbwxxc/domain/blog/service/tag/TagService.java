package top.lbwxxc.domain.blog.service.tag;


import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.model.entity.TagEntity;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.common.RedisConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TagService implements ITagService {

    @Resource
    private IBlogRepository blogRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IArticleRepository articleRepository;

    @Override
    public int addTag(String name) {
        return blogRepository.addTag(name);
    }

    @Override
    public List<TagEntity> findTagList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate) {

        String key = RedisConstants.buildTagPageKey(page + name);
        List<TagEntity> tagEntities;

        String tagStr = stringRedisTemplate.opsForValue().get(key);
        if (tagStr == null) {
            tagEntities = blogRepository.queryTagList(page, pageSize, name, startDate, endDate);
            //stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(categoryEntities));
            log.info("从数据库查询文章分类：{}", tagEntities);
        } else {
            log.info("查询文章分类命中缓存 {}", tagStr);
            tagEntities = JSON.parseArray(tagStr, TagEntity.class);
        }

        return tagEntities;
    }

    @Override
    public int deleteTag(long categoryId) {
        return blogRepository.deleteTag(categoryId);
    }

    @Override
    public List<TagEntity> findAllTag() {
        return blogRepository.findAllTag();
    }

    @Override
    public List<TagEntity> searchTagByKey(String key) {
        return blogRepository.searchTagByKey(key);
    }

    @Override
    public int findTagSize() {
        return blogRepository.findTagSize();
    }

    @Override
    public int addTags(List<String> tags) {
        return blogRepository.addTags(tags);
    }

    @Override
    public List<TagEntity> findTagsByArticleId(long articleId) {
        return articleRepository.findTagByArticleId(articleId);
    }
}
