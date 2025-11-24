package top.lbwxxc.domain.blog.service;


import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;

import java.util.List;

public interface IArticleService {

    List<ArticleEntity> findArticlePageList(int current, int size);

    int findArticleSize();

    List<ArticleEntity> findArticlePageListByCategoryId(long categoryId, int current, int size);

    int findArticleSizeByCategoryId(long categoryId);

    int findArticleSizeByTagId(long tagId);

    List<ArticleEntity> findArticlePageListByTagId(long tagId, int current, int size);

    ArticleDetailEntity findArticleDetailByArticleId(long articleId);

    ArticleEntity findPreArticleByArticleId(long articleId);

    ArticleEntity findNextArticleByArticleId(long articleId);
}
