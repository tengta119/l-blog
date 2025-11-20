package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.ArticleCategoryRel;

@Mapper
public interface ArticleCategoryRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategoryRel record);

    int insertSelective(ArticleCategoryRel record);

    ArticleCategoryRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategoryRel record);

    int updateByPrimaryKey(ArticleCategoryRel record);

    int logicalDeleteByArticleId(Long articleId);

    ArticleCategoryRel selectArticleCategoryRelByArticleId(Long articleId);

    int updateArticleCategoryRelByArticleId(ArticleCategoryRel articleCategoryRel);
}