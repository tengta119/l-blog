package top.lbwxxc.domain.blog.service.article;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;
import top.lbwxxc.domain.blog.service.IArticleService;

@Service
public class ArticleService implements IArticleService {

    @Resource
    private IArticleRepository articleRepository;

    @Override
    public int publishArticle(PublishArticleEntity publishArticleEntity) {
        return articleRepository.publishArticle(publishArticleEntity);
    }
}
