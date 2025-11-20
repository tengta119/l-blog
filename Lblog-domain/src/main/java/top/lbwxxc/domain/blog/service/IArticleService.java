package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishUpdateArticleEntity;

import java.time.LocalDate;
import java.util.List;

public interface IArticleService {

    int publishArticle(PublishUpdateArticleEntity publishUpdateArticleEntity);

    int deleteArticle(Long articleId);

    List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDate startDate, LocalDate endDate);

    int findArticleSize();

    ArticleDetailEntity findArticleDetail(Long articleId);

    int updateArticle(PublishUpdateArticleEntity publishUpdateArticleEntity);
}
