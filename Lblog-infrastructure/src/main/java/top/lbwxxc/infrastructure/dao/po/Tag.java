package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签表
 * tag
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag implements Serializable {
    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 逻辑删除标志位：0：未删除 1：已删除
     */
    private int isDeleted;

    private static final long serialVersionUID = 1L;
}