package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * 文章内容表
 * article_content
 */
@Data
public class ArticleContent implements Serializable {
    /**
     * 文章内容id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章正文
     */
    private String content;

    private static final long serialVersionUID = 1L;
}