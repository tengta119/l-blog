package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;

import java.time.LocalDate;
import java.util.List;

public interface IArticleRepository {

    int publishArticle(PublishArticleEntity publishArticleEntity);

    int deleteArticleById(long articleId);

    List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDate startDate, LocalDate endDate);

    int findArticleSize();

    ArticleDetailEntity findArticleDetailById(long articleId);
}
