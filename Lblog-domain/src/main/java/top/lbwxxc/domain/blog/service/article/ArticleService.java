package top.lbwxxc.domain.blog.service.article;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.service.IArticleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Resource
    private IArticleRepository articleRepository;

    @Override
    public List<ArticleEntity> findArticlePageList(int current, int size) {

        return articleRepository.findAllArticlePageList(current, size, null, null, null);
    }

    @Override
    public int findArticleSize() {
        return articleRepository.findArticleSize();
    }

    @Override
    public List<ArticleEntity> findArticlePageListByCategoryId(long categoryId, int current, int size) {
        List<ArticleEntity> articleByCategoryId = articleRepository.findArticleByCategoryId(categoryId, current, size);
        if (articleByCategoryId == null) {
            articleByCategoryId = new ArrayList<>();
        }
        return articleByCategoryId;
    }

    @Override
    public int findArticleSizeByCategoryId(long categoryId) {
        return articleRepository.findArticleSizeByCategoryId(categoryId);
    }
}
