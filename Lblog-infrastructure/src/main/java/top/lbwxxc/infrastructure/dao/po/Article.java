package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

/**
 * 文章表
 * article
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article implements Serializable {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    private Date updateTime;

    /**
     * 删除标志位：0：未删除 1：已删除
     */
    private Byte isDeleted;

    /**
     * 被阅读次数
     */
    private Integer readNum;

    private static final long serialVersionUID = 1L;
}