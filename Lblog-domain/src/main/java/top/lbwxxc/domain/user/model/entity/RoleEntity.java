package top.lbwxxc.domain.user.model.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色唯一标识
     */
    private String roleKey;
}
