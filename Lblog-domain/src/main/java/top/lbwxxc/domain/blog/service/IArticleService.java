package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.ArticleEntity;

import java.util.List;

public interface IArticleService {

    List<ArticleEntity> findArticlePageList(int current, int size);

    int findArticleSize();
}
