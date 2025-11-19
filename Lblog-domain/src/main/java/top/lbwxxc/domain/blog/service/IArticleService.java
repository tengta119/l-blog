package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;

public interface IArticleService {

    int publishArticle(PublishArticleEntity publishArticleEntity);

    int deleteArticle(Long articleId);
}
