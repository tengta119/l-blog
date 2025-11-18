package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客设置表
 * blog_settings
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogSettings implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 博客Logo
     */
    private String logo;

    /**
     * 博客名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}