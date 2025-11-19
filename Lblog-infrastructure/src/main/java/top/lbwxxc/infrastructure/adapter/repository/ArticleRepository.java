package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;
import top.lbwxxc.infrastructure.dao.*;
import top.lbwxxc.infrastructure.dao.po.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ArticleRepository implements IArticleRepository {

    @Resource
    private ArticleDao articleDao;
    @Resource
    private ArticleContentDao articleContentDao;
    @Resource
    private ArticleCategoryRelDao articleCategoryRelDao;
    @Resource
    private TagDao tagDao;
    @Resource
    private ArticleTagRelDao articleTagRelDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int publishArticle(PublishArticleEntity publishArticleEntity) {

        Article article = Article.builder()
                .title(publishArticleEntity.getTitle())
                .cover(publishArticleEntity.getCover())
                .summary(publishArticleEntity.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(0)
                .readNum(0)
                .build();
        int insertArticle = articleDao.insert(article);

        Long articleId = article.getId();
        log.info("插入文章成功。id 为 {}", articleId);

        ArticleContent articleContent = ArticleContent.builder()
                .articleId(articleId)
                .content(publishArticleEntity.getContent())
                .build();
        int insertArticleContent = articleContentDao.insert(articleContent);

        log.info("插入文章内容成功 {}", articleContent);

        ArticleCategoryRel articleCategoryRel = ArticleCategoryRel.builder()
                .articleId(articleId)
                .categoryId(publishArticleEntity.getCategoryId())
                .isDeleted(0)
                .build();
        int insertArticleCategoryRel = articleCategoryRelDao.insert(articleCategoryRel);

        log.info("文章分类插入成功 {}", articleCategoryRel);

        List<String> tags = publishArticleEntity.getTags();

        List<Long> tagId = new ArrayList<>();

        List<Tag> tagInsert = new ArrayList<>();
        for (String tag : tags) {
            if (tag.matches("\\d+")) {
                tagId.add(Long.parseLong(tag));
            } else {
                tagInsert.add(Tag.builder().name(tag).createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).isDeleted(0).build());
            }
        }
        if (!tagInsert.isEmpty()) {
            tagDao.insetTags(tagInsert);
            log.info("标签插入成功 {}", tagInsert);
        }

        tagInsert.forEach(tag -> tagId.add(tag.getId()));

        List<ArticleTagRel> articleTagRels = new ArrayList<>();
        tagId.forEach(id -> articleTagRels.add(ArticleTagRel.builder().articleId(articleId).tagId(id).build()));

        int insertArticleTagRel = articleTagRelDao.batchInsert(articleTagRels);
        log.info("文章标签插入成功 {}", articleTagRels);

        return insertArticle + insertArticleContent + insertArticleCategoryRel + insertArticleTagRel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteArticleById(long articleId) {

        int deleteByPrimaryKey = articleDao.logicalDeleteByPrimaryKey(articleId);

        int logicalDeleteCategory = articleCategoryRelDao.logicalDeleteByArticleId(articleId);

        int logicalDeleteTag = articleTagRelDao.logicalDeleteByArticleId(articleId);

        return deleteByPrimaryKey + logicalDeleteCategory + logicalDeleteTag;
    }

    @Override
    public List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDate startDate, LocalDate endDate) {

        List<ArticleEntity> articleEntities = new ArrayList<>();
        List<Article> articles = articleDao.selectArticlePageList((page - 1) * pageSize, pageSize, title, startDate, endDate);
        for (Article article : articles) {
            articleEntities.add(ArticleEntity.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .cover(article.getCover())
                    .readNum(article.getReadNum())
                    .isDeleted(article.getIsDeleted())
                    .createTime(article.getCreateTime())
                    .updateTime(article.getUpdateTime())
                    .build()
            );
        }

        return articleEntities;
    }

    @Override
    public int findArticleSize() {
        return articleDao.selectArticleSize();
    }

    @Override
    public ArticleDetailEntity findArticleDetailById(long articleId) {

        Article article = articleDao.selectByPrimaryKey(articleId);

        if (article == null) {
            throw new RuntimeException("文章不存在");
        }

        ArticleContent articleContent = articleContentDao.selectByArticleId(articleId);

        ArticleCategoryRel articleCategoryRel = articleCategoryRelDao.selectArticleCategoryRelByArticleId(articleId);

        List<ArticleTagRel> articleTagRels = articleTagRelDao.selectByArticleId(articleId);
        List<Long> tagIds = articleTagRels.stream().mapToLong(ArticleTagRel::getId).boxed().toList();

        return ArticleDetailEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .cover(article.getCover())
                .summary(article.getSummary())
                .content(articleContent.getContent())
                .categoryId(articleCategoryRel.getCategoryId())
                .tagIds(tagIds)
                .build();
    }
}
