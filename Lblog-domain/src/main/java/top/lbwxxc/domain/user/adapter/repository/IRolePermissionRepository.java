package top.lbwxxc.domain.user.adapter.repository;


import top.lbwxxc.domain.user.model.entity.PermissionEntity;
import top.lbwxxc.domain.user.model.entity.RoleEntity;
import top.lbwxxc.domain.user.model.entity.RolePermissionRelEntity;

import java.util.List;

public interface IRolePermissionRepository {

    List<RoleEntity> queryAllRole();
    List<PermissionEntity> queryAllPermission();

    List<RolePermissionRelEntity> queryAllRoleRelPermission();
}
