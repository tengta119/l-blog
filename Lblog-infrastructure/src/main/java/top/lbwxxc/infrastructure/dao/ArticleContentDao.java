package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.ArticleContent;

@Mapper
public interface ArticleContentDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    ArticleContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);
}