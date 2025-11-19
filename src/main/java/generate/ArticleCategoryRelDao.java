package generate;

import generate.ArticleCategoryRel;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCategoryRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategoryRel record);

    int insertSelective(ArticleCategoryRel record);

    ArticleCategoryRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategoryRel record);

    int updateByPrimaryKey(ArticleCategoryRel record);
}