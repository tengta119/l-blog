package top.lbwxxc.domain.blog.service.article;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishUpdateArticleEntity;
import top.lbwxxc.domain.blog.service.IArticleService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Resource
    private IArticleRepository articleRepository;

    @Override
    public int publishArticle(PublishUpdateArticleEntity publishUpdateArticleEntity) {
        return articleRepository.publishArticle(publishUpdateArticleEntity);
    }

    @Override
    public int deleteArticle(Long articleId) {
        return articleRepository.deleteArticleById(articleId);
    }

    @Override
    public List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDate startDate, LocalDate endDate) {

        return articleRepository.findAllArticlePageList(page, pageSize, title, startDate, endDate);
    }

    @Override
    public int findArticleSize() {
        return articleRepository.findArticleSize();
    }

    @Override
    public ArticleDetailEntity findArticleDetail(Long articleId) {
        return articleRepository.findArticleDetailById(articleId);
    }

    @Override
    public int updateArticle(PublishUpdateArticleEntity publishUpdateArticleEntity) {
        return articleRepository.updateArticle(publishUpdateArticleEntity);
    }
}
