package top.lbwxxc.domain.user.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permissionKey;
}
