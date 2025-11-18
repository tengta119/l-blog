package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.UserExternalUrls;

@Mapper
public interface UserExternalUrlsDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserExternalUrls record);

    int insertSelective(UserExternalUrls record);

    UserExternalUrls selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserExternalUrls record);

    int updateByPrimaryKey(UserExternalUrls record);
}