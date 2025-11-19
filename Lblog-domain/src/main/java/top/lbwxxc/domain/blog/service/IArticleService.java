package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;

import java.time.LocalDate;
import java.util.List;

public interface IArticleService {

    int publishArticle(PublishArticleEntity publishArticleEntity);

    int deleteArticle(Long articleId);

    List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDate startDate, LocalDate endDate);

    int findArticleSize();
}
