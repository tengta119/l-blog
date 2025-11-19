package generate;

import generate.ArticleContent;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleContentDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    ArticleContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);
}