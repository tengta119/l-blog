package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章内容表
 * article_content
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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