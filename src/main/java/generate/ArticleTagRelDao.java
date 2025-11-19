package generate;

import generate.ArticleTagRel;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagRel record);

    int insertSelective(ArticleTagRel record);

    ArticleTagRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagRel record);

    int updateByPrimaryKey(ArticleTagRel record);
}