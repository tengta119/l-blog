package top.lbwxxc.config;


import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.lbwxxc.domain.user.model.entity.PermissionEntity;
import top.lbwxxc.domain.user.model.entity.RoleEntity;
import top.lbwxxc.domain.user.model.entity.RolePermissionRelEntity;
import top.lbwxxc.types.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private Cache<String, Object> cache;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<PermissionEntity> permissionEntities = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.USER_PERMISSION), PermissionEntity.class);
        if (permissionEntities != null) {
            HashMap<Long, PermissionEntity> permissionId2permission = permissionEntities.stream().collect(Collectors.toMap(PermissionEntity::getId, Function.identity(), (e1, e2) -> e2, HashMap::new));
            List<RolePermissionRelEntity> rolePermissionRelEntities = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.USER_REL_ROLE_PERMISSION), RolePermissionRelEntity.class);

            HashMap<Long, List<Long>> role2permission = new HashMap<>();
            if (rolePermissionRelEntities != null) {
                for (RolePermissionRelEntity rolePermissionRelEntity : rolePermissionRelEntities) {
                    role2permission.computeIfAbsent(rolePermissionRelEntity.getRoleId(), k -> new ArrayList<>());
                    role2permission.get(rolePermissionRelEntity.getRoleId()).add(rolePermissionRelEntity.getPermissionId());
                }
            }

            List<RoleEntity> roleEntities = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.USER_ROLE), RoleEntity.class);
            if (roleEntities != null) {

                List<Long> userRoleIds = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.buildUserRoleKey((Long) loginId)), Long.class);

                List<String> userPermissionKey = new ArrayList<>();
                if (userRoleIds != null) {
                    for (Long roleId : userRoleIds) {
                        for (Long permissionId : role2permission.get(roleId)) {
                            userPermissionKey.add(permissionId2permission.get(permissionId).getPermissionKey());
                        }
                    }
                }
                log.info("用户 {} 对应的权限 {}", loginType, userPermissionKey);
                return userPermissionKey;
            }
        }
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userToRole((Long) loginId) ;
    }

    private List<String> userToRole(Long userId) {

        List<RoleEntity> roleEntities = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.USER_ROLE), RoleEntity.class);
        if (roleEntities != null) {
            Map<Long, RoleEntity> roleId2RoleEntity = roleEntities.stream().collect(Collectors.toMap(RoleEntity::getId, Function.identity(), (e1, e2) -> e2, HashMap::new));

            List<Long> userRoleIds = JSON.parseArray(stringRedisTemplate.opsForValue().get(Constants.buildUserRoleKey(userId)), Long.class);
            if (userRoleIds != null) {
                List<String> userRoleKeys = new ArrayList<>();
                for (Long roleId : userRoleIds) {
                    RoleEntity roleEntity = roleId2RoleEntity.getOrDefault(roleId, null);
                    userRoleKeys.add(roleEntity.getRoleKey());
                }
                log.info("用户 {}，对应的权限 {}", userId, userRoleKeys);
                return userRoleKeys;
            }
        }

        return List.of();
    }
}
