package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.UserDetail;

@Mapper
public interface UserDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);
}