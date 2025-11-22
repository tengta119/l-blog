package top.lbwxxc.domain.user.service;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.holder.LoginUserContextHolder;
import top.lbwxxc.domain.user.adapter.repository.IRolePermissionRepository;
import top.lbwxxc.domain.user.adapter.repository.IUserInfoRepository;
import top.lbwxxc.domain.user.model.entity.PermissionEntity;
import top.lbwxxc.domain.user.model.entity.RoleEntity;
import top.lbwxxc.domain.user.model.entity.RolePermissionRelEntity;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.common.RedisConstants;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserInfoInfoService implements IUserInfoService {

    @Resource
    private IRolePermissionRepository rolePermissionRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserInfoRepository userInfoRepository;

    @Override
    public void pushRolePermission2Redis() {
        List<RoleEntity> roleEntities = rolePermissionRepository.queryAllRole();
        List<PermissionEntity> permissionEntities = rolePermissionRepository.queryAllPermission();
        List<RolePermissionRelEntity> rolePermissionRelEntities = rolePermissionRepository.queryAllRoleRelPermission();
        log.info("查询系统权限信息\n {} \n {} \n {}", roleEntities, permissionEntities, rolePermissionRelEntities);

        stringRedisTemplate.opsForValue().set(Constants.USER_ROLE, JSON.toJSONString(roleEntities));
        stringRedisTemplate.opsForValue().set(Constants.USER_PERMISSION, JSON.toJSONString(permissionEntities));
        stringRedisTemplate.opsForValue().set(Constants.USER_REL_ROLE_PERMISSION, JSON.toJSONString(rolePermissionRelEntities));
    }

    @Override
    public UserInfoEntity getUserInfo() {
        String userInfoKey = RedisConstants.buildUserInfoKey(10011L);
        String userInfoStr = stringRedisTemplate.opsForValue().get(userInfoKey);
        UserInfoEntity userInfo = null;
        if (userInfoStr == null) {
            userInfo = userInfoRepository.getUserInfoById(10011L);
            stringRedisTemplate.opsForValue().set(userInfoKey, JSON.toJSONString(userInfo), 3, TimeUnit.HOURS);
        } else {
            log.info("查询用户详细信息击中缓存 {}", userInfoStr);
            userInfo = JSON.parseObject(userInfoStr, UserInfoEntity.class);
        }

        return userInfo;
    }

    @Override
    public void logout() {
        StpUtil.logout(LoginUserContextHolder.getUserId());
    }

    @Override
    public int updateUserInfo(UserInfoEntity userInfo) {
        userInfo.setId(LoginUserContextHolder.getUserId());
        return userInfoRepository.updateUserInfo(userInfo);
    }
}
