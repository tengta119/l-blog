package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.user.adapter.repository.IRolePermissionRepository;
import top.lbwxxc.domain.user.model.entity.PermissionEntity;
import top.lbwxxc.domain.user.model.entity.RoleEntity;
import top.lbwxxc.domain.user.model.entity.RolePermissionRelEntity;
import top.lbwxxc.infrastructure.dao.PermissionDao;
import top.lbwxxc.infrastructure.dao.RoleDao;
import top.lbwxxc.infrastructure.dao.RolePermissionRelDao;
import top.lbwxxc.infrastructure.dao.po.Permission;
import top.lbwxxc.infrastructure.dao.po.Role;
import top.lbwxxc.infrastructure.dao.po.RolePermissionRel;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class RolePermissionRepository implements IRolePermissionRepository {

    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RolePermissionRelDao rolePermissionRelDao;

    @Override
    public List<RoleEntity> queryAllRole() {

        List<Role> roles = roleDao.selectAll();
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (Role role : roles) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(role.getId());
            roleEntity.setRoleName(role.getRoleName());
            roleEntity.setRoleKey(role.getRoleKey());
            roleEntities.add(roleEntity);
        }

        return roleEntities;
    }

    @Override
    public List<PermissionEntity> queryAllPermission() {

        List<Permission> permissions = permissionDao.selectAllPermission();
        List<PermissionEntity> permissionEntities = new ArrayList<>();
        for (Permission permission : permissions) {
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setId(permission.getId());
            permissionEntity.setName(permission.getName());
            permissionEntity.setPermissionKey(permission.getPermissionKey());

            permissionEntities.add(permissionEntity);
        }

        return permissionEntities;
    }

    @Override
    public List<RolePermissionRelEntity> queryAllRoleRelPermission() {

        List<RolePermissionRel> rolePermissionRels = rolePermissionRelDao.selectAll();
        List<RolePermissionRelEntity> rolePermissionRelEntities = new ArrayList<>();
        for (RolePermissionRel rolePermissionRel : rolePermissionRels) {
            RolePermissionRelEntity rolePermissionRelEntity = new RolePermissionRelEntity();
            rolePermissionRelEntity.setId(rolePermissionRel.getId());
            rolePermissionRelEntity.setRoleId(rolePermissionRel.getRoleId());
            rolePermissionRelEntity.setPermissionId(rolePermissionRel.getPermissionId());

            rolePermissionRelEntities.add(rolePermissionRelEntity);
        }

        return rolePermissionRelEntities;
    }


}
