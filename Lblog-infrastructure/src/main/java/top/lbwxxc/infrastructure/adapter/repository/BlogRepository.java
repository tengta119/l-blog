package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.model.entity.BlogSettingsEntity;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.model.entity.ExternalUrlEntity;
import top.lbwxxc.domain.blog.model.entity.TagEntity;
import top.lbwxxc.infrastructure.dao.BlogSettingsDao;
import top.lbwxxc.infrastructure.dao.CategoryDao;
import top.lbwxxc.infrastructure.dao.TagDao;
import top.lbwxxc.infrastructure.dao.UserExternalUrlsDao;
import top.lbwxxc.infrastructure.dao.po.BlogSettings;
import top.lbwxxc.infrastructure.dao.po.Category;
import top.lbwxxc.infrastructure.dao.po.Tag;
import top.lbwxxc.infrastructure.dao.po.UserExternalUrls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogRepository implements IBlogRepository {


    @Resource
    private CategoryDao categoryDao;
    @Resource
    private TagDao tagDao;
    @Resource
    private BlogSettingsDao blogSettingsDao;
    @Resource
    private UserExternalUrlsDao userExternalUrlsDao;

    @Override
    public int addCategory(String name) {

        Category category = new Category();
        category.setName(name);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setIsDeleted(0);
        return categoryDao.insert(category);
    }

    @Override
    public List<CategoryEntity> queryCategoryList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate) {

        List<Category> categories = categoryDao.selectCategoryByPageAndData((page - 1) * pageSize, pageSize, name, startDate, endDate);
        return getCategoryEntities(categories);
    }

    @Override
    public int deleteCategory(long categoryId) {
        return categoryDao.logicalDelete(categoryId);
    }

    @Override
    public List<CategoryEntity> findAllCategory() {

        List<Category> categories = categoryDao.selectAllCategory();
        return getCategoryEntities(categories);
    }

    @Override
    public int findCategorySize() {
        return categoryDao.selectCategorySize();
    }

    @Override
    public int addTag(String name) {

        Tag tag = Tag.builder()
                .name(name)
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .isDeleted(0)
                .build();

        return tagDao.insert(tag);
    }

    @Override
    public int addTags(List<String> tags) {

        List<Tag> tagList = tags.stream().map(name ->
                Tag.builder()
                        .name(name)
                        .updateTime(LocalDateTime.now())
                        .createTime(LocalDateTime.now())
                        .isDeleted(0)
                        .build()
        ).toList();

        return tagDao.insetTags(tagList);
    }

    @Override
    public List<TagEntity> queryTagList(int page, int pageSize, String name, LocalDateTime startDate, LocalDateTime endDate) {

        List<Tag> tags = tagDao.selectTagByPageAndData((page - 1) * pageSize, pageSize, name, startDate, endDate);
        return getTagEntities(tags);
    }

    @Override
    public int deleteTag(long tagId) {
        return tagDao.logicalDelete(tagId);
    }

    @Override
    public List<TagEntity> findAllTag() {
        List<Tag> tags = tagDao.selectTagAll();
        return getTagEntities(tags);
    }

    @Override
    public int findTagSize() {
        return tagDao.selectTagSize();
    }

    @Override
    public int updateBlogSettings(String logo, String name) {

        BlogSettings blogSettings = BlogSettings.builder().id(1L).build();

        if (logo != null && !logo.isEmpty()) {
            blogSettings.setLogo(logo);
        }
        if (name != null && !name.isEmpty()) {
            blogSettings.setName(name);
        }

        return blogSettingsDao.updateByPrimaryKeySelective(blogSettings);
    }

    @Override
    public BlogSettingsEntity getBlogSettings() {
        BlogSettings blogSettings = blogSettingsDao.selectByPrimaryKey(1L);

        return BlogSettingsEntity.builder()
                .name(blogSettings.getName())
                .logo(blogSettings.getLogo())
                .build();
    }

    @Override
    public int addExternalUrl(String name, String logo, String url) {

        UserExternalUrls userExternalUrls = UserExternalUrls.builder()
                .url(url)
                .platform(name)
                .icon(logo)
                .build();

        return userExternalUrlsDao.insertSelective(userExternalUrls);
    }

    @Override
    public int updateExternalUrl(Long id, String name, String logo, String url) {
        UserExternalUrls userExternalUrls = UserExternalUrls.builder().id(id).build();

        if (logo != null && !logo.isEmpty()) {
            userExternalUrls.setIcon(logo);
        }
        if (name != null && !name.isEmpty()) {
            userExternalUrls.setPlatform(name);
        }
        if (url != null && !url.isEmpty()) {
            userExternalUrls.setUrl(url);
        }

        return userExternalUrlsDao.updateByPrimaryKeySelective(userExternalUrls);
    }

    @Override
    public List<ExternalUrlEntity> findExternalUrlList(int page, int pageSize) {
        List<UserExternalUrls> userExternalUrls = userExternalUrlsDao.selectUserExternalUrlsPage((page - 1) * pageSize, pageSize);
        List<ExternalUrlEntity> externalUrlEntities = new ArrayList<>();
        for (UserExternalUrls userExternalUrl : userExternalUrls) {
            ExternalUrlEntity externalUrlEntity = ExternalUrlEntity.builder()
                    .id(userExternalUrl.getId())
                    .url(userExternalUrl.getUrl())
                    .name(userExternalUrl.getPlatform())
                    .logo(userExternalUrl.getIcon())
                    .build();
            externalUrlEntities.add(externalUrlEntity);
        }
        return externalUrlEntities;
    }

    @Override
    public int findExternalUrlSize() {
        return userExternalUrlsDao.selectUserExternalUrlsCount();
    }

    private List<TagEntity> getTagEntities(List<Tag> tags) {
        List<TagEntity> tagEntities = new ArrayList<>();
        for (Tag tag : tags) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setId(tag.getId());
            tagEntity.setName(tag.getName());
            tagEntity.setCreateTime(LocalDateTime.now());

            tagEntities.add(tagEntity);
        }

        return tagEntities;
    }

    private List<CategoryEntity> getCategoryEntities(List<Category> categories) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (Category category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(category.getId());
            categoryEntity.setName(category.getName());
            categoryEntity.setCreateTime(category.getCreateTime());
            categoryEntities.add(categoryEntity);
        }
        return categoryEntities;
    }
}
