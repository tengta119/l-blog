package top.lbwxxc.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.UserRoleRel;

@Mapper
public interface UserRoleRelDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRel record);

    int insertSelective(UserRoleRel record);

    UserRoleRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRel record);

    int updateByPrimaryKey(UserRoleRel record);
}