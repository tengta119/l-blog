package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.lbwxxc.domain.blog.adapter.repository.IArticleRepository;
import top.lbwxxc.domain.blog.model.entity.*;
import top.lbwxxc.infrastructure.dao.*;
import top.lbwxxc.infrastructure.dao.po.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Resource
    private CategoryDao categoryDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int publishArticle(PublishUpdateArticleEntity publishUpdateArticleEntity) {

        Article article = Article.builder()
                .title(publishUpdateArticleEntity.getTitle())
                .cover(publishUpdateArticleEntity.getCover())
                .summary(publishUpdateArticleEntity.getSummary())
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
                .content(publishUpdateArticleEntity.getContent())
                .build();
        int insertArticleContent = articleContentDao.insert(articleContent);

        log.info("插入文章内容成功 {}", articleContent);

        ArticleCategoryRel articleCategoryRel = ArticleCategoryRel.builder()
                .articleId(articleId)
                .categoryId(publishUpdateArticleEntity.getCategoryId())
                .isDeleted(0)
                .build();
        int insertArticleCategoryRel = articleCategoryRelDao.insert(articleCategoryRel);

        log.info("文章分类插入成功 {}", articleCategoryRel);

        List<String> tags = publishUpdateArticleEntity.getTags();

        List<Long> tagIds = new ArrayList<>();

        addTags(tags, tagIds);

        List<ArticleTagRel> articleTagRels = new ArrayList<>();
        tagIds.forEach(id -> articleTagRels.add(ArticleTagRel.builder().articleId(articleId).tagId(id).build()));

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
    public List<ArticleEntity> findAllArticlePageList(int page, int pageSize, String title, LocalDateTime startDate, LocalDateTime endDate) {

        List<ArticleEntity> articleEntities = new ArrayList<>();
        List<Article> articles = articleDao.selectArticlePageList((page - 1) * pageSize, pageSize, title, startDate, endDate);
        for (Article article : articles) {
            articleEntities.add(ArticleEntity.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .cover(article.getCover())
                    .summary(article.getSummary())
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

        List<ArticleTagRel> articleTagRels = articleTagRelDao.selectEffectiveByArticleId(articleId);
        List<Long> tagIds = articleTagRels.stream().mapToLong(ArticleTagRel::getTagId).boxed().toList();

        return ArticleDetailEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .cover(article.getCover())
                .createTime(article.getCreateTime())
                .summary(article.getSummary())
                .content(articleContent.getContent())
                .categoryId(articleCategoryRel.getCategoryId())
                .tagIds(tagIds)
                .readNum(article.getReadNum())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateArticle(PublishUpdateArticleEntity publishUpdateArticleEntity) {

        Long articleId = publishUpdateArticleEntity.getArticleId();
        Article article = Article.builder()
                .id(articleId)
                .title(publishUpdateArticleEntity.getTitle())
                .cover(publishUpdateArticleEntity.getCover())
                .summary(publishUpdateArticleEntity.getSummary())
                .updateTime(LocalDateTime.now())
                .build();
        int updateArticle = articleDao.updateByPrimaryKey(article);
        log.info("更新文章源数据成功 {}",  updateArticle);


        ArticleContent articleContent = ArticleContent.builder()
                .articleId(articleId)
                .content(publishUpdateArticleEntity.getContent())
                .build();
        int updateContent = articleContentDao.updateContentByArticleId(articleContent);
        log.info("更新文章内容成功, {}",  updateContent);

        ArticleCategoryRel articleCategoryRel = ArticleCategoryRel.builder()
                .articleId(articleId)
                .categoryId(publishUpdateArticleEntity.getCategoryId())
                .build();
        int updateArticleCategoryRel = articleCategoryRelDao.updateArticleCategoryRelByArticleId(articleCategoryRel);
        log.info("更新文章分类成功 {}",  updateArticleCategoryRel);


        List<String> tags = publishUpdateArticleEntity.getTags();
        List<Long> tagIds = new ArrayList<>();
        addTags(tags, tagIds);

        List<Long> tagUpdateDelete = new ArrayList<>();
        List<Long> tagUpdateUpdateEffective = new ArrayList<>();
        List<ArticleTagRel> tagInsert = new ArrayList<>();
        List<ArticleTagRel> articleTagRels = articleTagRelDao.selectAllByArticleId(articleId);

        for (ArticleTagRel articleTagRel : articleTagRels) {
            if (tagIds.contains(articleTagRel.getTagId())) {
                if (articleTagRel.getIsDeleted() == 1) {
                    tagUpdateUpdateEffective.add(articleTagRel.getId());
                }
            } else {
                tagUpdateDelete.add(articleTagRel.getId());
            }
            tagIds.remove(articleTagRel.getTagId());
        }

        tagIds.forEach(tagId -> tagInsert.add(ArticleTagRel.builder().articleId(articleId).tagId(tagId).build()));

        int tagUpdate = 0;
        if (!tagInsert.isEmpty()) {
            tagUpdate += articleTagRelDao.batchInsert(tagInsert);
            log.info("新增文章标记 {}", tagUpdate);
        }
        if (!tagUpdateUpdateEffective.isEmpty()) {
            tagUpdate += articleTagRelDao.batchUpdateEffective(tagUpdateUpdateEffective);
            log.info("使文章标签生效 {}", tagUpdate);
        }
        if (!tagUpdateDelete.isEmpty()) {
            tagUpdate += articleTagRelDao.batchLogicDelete(tagUpdateDelete);
            log.info("删除一些文章标记 {}", tagUpdate);
        }

        return updateArticle + updateContent + updateArticleCategoryRel + tagUpdate;
    }

    @Override
    public List<TagEntity> findTagByArticleId(long articleId) {

        List<ArticleTagRel> articleTagRels = articleTagRelDao.selectEffectiveByArticleId(articleId);
        List<Long> tagIds = articleTagRels.stream().map(ArticleTagRel::getTagId).toList();
        return getTagEntities(tagIds);
    }

    @Override
    public CategoryEntity findCategoryByArticleId(long articleId) {

        ArticleCategoryRel articleCategoryRel = articleCategoryRelDao.selectArticleCategoryRelByArticleId(articleId);
        Category category = categoryDao.selectByPrimaryKey(articleCategoryRel.getCategoryId());

        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .createTime(category.getCreateTime())
                .build();
    }

    @Override
    public List<ArticleEntity> findArticleByCategoryId(long categoryId, int page, int pageSize) {
        List<Article> articles = articleDao.selectArticleListByCategoryId(categoryId, (page - 1) * pageSize, pageSize);
        return articles.stream().map(article -> ArticleEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .createTime(article.getCreateTime())
                .isDeleted(article.getIsDeleted())
                .updateTime(article.getUpdateTime())
                .readNum(article.getReadNum())
                .cover(article.getCover())
                .build()
        ).toList();
    }

    @Override
    public int findArticleSizeByCategoryId(long categoryId) {
        return articleCategoryRelDao.selectArticleSizeByCategoryId(categoryId);
    }

    @Override
    public int findArticleSizeByTagId(long tagId) {
        return articleTagRelDao.selectArticleSizeByTagId(tagId);
    }

    @Override
    public List<ArticleEntity> findArticleByTagId(long tagId, int page, int pageSize) {
        List<Article> articles = articleDao.selectArticleListByTag(tagId, (page - 1) * pageSize, pageSize);
        return articles.stream().map(article -> ArticleEntity.builder()
                .id(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .createTime(article.getCreateTime())
                .isDeleted(article.getIsDeleted())
                .updateTime(article.getUpdateTime())
                .readNum(article.getReadNum())
                .cover(article.getCover())
                .build()
        ).toList();
    }

    @Override
    public ArticleEntity findPreArticleByArticleId(long articleId) {

        Article article = articleDao.selectPreArticleByArticleId(articleId);

        if (article != null) {
            return ArticleEntity.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public ArticleEntity findNextArticleByArticleId(long articleId) {

        Article article = articleDao.selectNextArticleByArticleId(articleId);

        if (article != null) {
            return ArticleEntity.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public CategoryEntity findCategoryByCategoryId(long categoryId) {

        Category category = categoryDao.selectEffectiveCategoryCategoryId(categoryId);

        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public List<TagEntity> findTagsByTagIds(List<Long> tagIds) {
        return getTagEntities(tagIds);
    }

    private List<TagEntity> getTagEntities(List<Long> tagIds) {
        List<Tag> tags = tagDao.selectTagsById(tagIds);

        return Optional.ofNullable(tags)
                .orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .map(tag -> {
                    TagEntity entity = new TagEntity();
                    entity.setId(tag.getId());
                    entity.setName(tag.getName());
                    entity.setCreateTime(tag.getCreateTime());
                    return entity;
                })
                .toList();
    }

    // 新增标签，并将标签 id 写入到 tagId
    private void addTags(List<String> tags, List<Long> tagId) {
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
    }
}
