package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章所属分类关联表
 * article_category_rel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    /**
     * 删除标志位：0：未删除 1：已删除
     */
    private int isDeleted;

    private static final long serialVersionUID = 1L;
}