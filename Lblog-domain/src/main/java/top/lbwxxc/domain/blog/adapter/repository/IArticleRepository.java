package top.lbwxxc.domain.blog.adapter.repository;


import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;

public interface IArticleRepository {

    int publishArticle(PublishArticleEntity publishArticleEntity);
}
