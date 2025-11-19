package generate;

import generate.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}