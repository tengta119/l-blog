package top.lbwxxc.domain.user.service;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.repository.IRolePermissionRepository;
import top.lbwxxc.domain.user.model.entity.PermissionEntity;
import top.lbwxxc.domain.user.model.entity.RoleEntity;
import top.lbwxxc.domain.user.model.entity.RolePermissionRelEntity;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.util.JsonUtils;

import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    @Resource
    private IRolePermissionRepository rolePermissionRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void pushRolePermission2Redis() {
        List<RoleEntity> roleEntities = rolePermissionRepository.queryAllRole();
        List<PermissionEntity> permissionEntities = rolePermissionRepository.queryAllPermission();
        List<RolePermissionRelEntity> rolePermissionRelEntities = rolePermissionRepository.queryAllRoleRelPermission();
        log.info("查询系统权限信息\n {} \n {} \n {}", roleEntities, permissionEntities, rolePermissionRelEntities);

        stringRedisTemplate.opsForValue().set(Constants.USER_ROLE, JsonUtils.toJsonString(roleEntities));
        stringRedisTemplate.opsForValue().set(Constants.USER_PERMISSION, JsonUtils.toJsonString(permissionEntities));
        stringRedisTemplate.opsForValue().set(Constants.USER_REL_ROLE_PERMISSION, JsonUtils.toJsonString(rolePermissionRelEntities));
    }
}
