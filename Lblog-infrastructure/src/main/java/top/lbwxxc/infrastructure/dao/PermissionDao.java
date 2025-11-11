package top.lbwxxc.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.Permission;

import java.util.List;

@Mapper
public interface PermissionDao {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAllPermission();
}