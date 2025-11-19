package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * 文章对应标签关联表
 * article_tag_rel
 */
@Data
public class ArticleTagRel implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;

    private static final long serialVersionUID = 1L;
}