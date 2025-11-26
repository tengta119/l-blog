package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.Article;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    int logicalDeleteByPrimaryKey(Long id);

    List<Article> selectArticlePageList(int offset, int pageSize, String title, LocalDateTime startDate, LocalDateTime endDate);

    int selectArticleSize();

    List<Article> selectArticleListByCategoryId(long categoryId, int offset, int pageSize);

    List<Article> selectArticleListByTag(long tagId, int offset, int pageSize);

    Article selectPreArticleByArticleId(long articleId);

    Article selectNextArticleByArticleId(long articleId);

    int addArticleReadNum(long articleId);
}