package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lbwxxc.infrastructure.dao.po.ArticleTagRel;

import java.util.List;

@Mapper
public interface ArticleTagRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagRel record);

    int insertSelective(ArticleTagRel record);

    ArticleTagRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagRel record);

    int updateByPrimaryKey(ArticleTagRel record);

    int batchInsert(@Param("list") List<ArticleTagRel> list);

    int logicalDeleteByArticleId(@Param("articleId") Long articleId);

    List<ArticleTagRel> selectByArticleId(@Param("articleId") Long articleId);
}