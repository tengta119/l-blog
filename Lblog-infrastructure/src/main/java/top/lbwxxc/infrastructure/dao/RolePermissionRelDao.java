package top.lbwxxc.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.RolePermissionRel;

import java.util.List;

@Mapper
public interface RolePermissionRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionRel record);

    int insertSelective(RolePermissionRel record);

    RolePermissionRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermissionRel record);

    int updateByPrimaryKey(RolePermissionRel record);

    List<RolePermissionRel> selectAll();
}