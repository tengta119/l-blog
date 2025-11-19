package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * 文章所属分类关联表
 * article_category_rel
 */
@Data
public class ArticleCategoryRel implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 分类id
     */
    private Long categoryId;

    private static final long serialVersionUID = 1L;
}