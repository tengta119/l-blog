package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.ArticleTagRel;

@Mapper
public interface ArticleTagRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagRel record);

    int insertSelective(ArticleTagRel record);

    ArticleTagRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagRel record);

    int updateByPrimaryKey(ArticleTagRel record);
}