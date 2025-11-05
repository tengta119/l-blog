package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表
 * user_role_rel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleRel implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除(0：未删除 1：已删除)
     */
    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}