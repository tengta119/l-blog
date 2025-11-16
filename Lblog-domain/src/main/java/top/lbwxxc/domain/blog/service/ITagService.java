package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.TagEntity;

import java.time.LocalDate;
import java.util.List;

public interface ITagService {

    int addTag(String name);

    List<TagEntity> findTagList(int page, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int deleteTag(long categoryId);

    List<TagEntity> findAllTag();

    int findTagSize();

    int addTags(List<String> tags);
}
