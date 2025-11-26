package top.lbwxxc.domain.blog.service.article;


import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.event.ReadArticleEvent;
import top.lbwxxc.domain.blog.service.IArticleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Resource
    private IArticleRepository articleRepository;
    @Resource
    private ApplicationEventPublisher eventPublisher;

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

    @Override
    public int findArticleSizeByTagId(long tagId) {
        return articleRepository.findArticleSizeByTagId(tagId);
    }

    @Override
    public List<ArticleEntity> findArticlePageListByTagId(long tagId, int current, int size) {
        return articleRepository.findArticleByTagId(tagId, current, size);
    }

    @Override
    public ArticleDetailEntity findArticleDetailByArticleId(long articleId) {

        ArticleDetailEntity articleDetailById = articleRepository.findArticleDetailById(articleId);
        eventPublisher.publishEvent(new ReadArticleEvent(this, articleId));

        return articleDetailById;
    }

    @Override
    public ArticleEntity findPreArticleByArticleId(long articleId) {
        return articleRepository.findPreArticleByArticleId(articleId);
    }

    @Override
    public ArticleEntity findNextArticleByArticleId(long articleId) {
        return articleRepository.findNextArticleByArticleId(articleId);
    }

    @Override
    public int addArticleReadNum(long articleId) {
        return articleRepository.addArticleReadNum(articleId);
    }
}
