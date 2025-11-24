package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.TagEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ITagService {

    int addTag(String name);

    List<TagEntity> findTagList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate);

    int deleteTag(long categoryId);

    List<TagEntity> findAllTag();

    List<TagEntity> searchTagByKey(String key);

    int findTagSize();

    int addTags(List<String> tags);

    List<TagEntity> findTagsByArticleId(long articleId);

    List<TagEntity> findTagsByTagIds(List<Long> tagIds);

}
