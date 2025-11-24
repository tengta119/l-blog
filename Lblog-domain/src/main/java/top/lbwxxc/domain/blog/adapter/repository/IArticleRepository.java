package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IArticleRepository {

    int publishArticle(PublishUpdateArticleEntity publishUpdateArticleEntity);

    int deleteArticleById(long articleId);

    List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDateTime startDate, LocalDateTime endDate);

    int findArticleSize();

    ArticleDetailEntity findArticleDetailById(long articleId);

    int updateArticle(PublishUpdateArticleEntity publishUpdateArticleEntity);

    List<TagEntity> findTagByArticleId(long articleId);

    CategoryEntity findCategoryByArticleId(long articleId);

    List<ArticleEntity> findArticleByCategoryId(long categoryId, int page, int pageSize);

    int findArticleSizeByCategoryId(long categoryId);

    int findArticleSizeByTagId(long tagId);

    List<ArticleEntity> findArticleByTagId(long tagId, int page, int pageSize);

    ArticleEntity findPreArticleByArticleId(long articleId);

    ArticleEntity findNextArticleByArticleId(long articleId);

    CategoryEntity findCategoryByCategoryId(long categoryId);

    List<TagEntity> findTagsByTagIds(List<Long> tagIds);
}
