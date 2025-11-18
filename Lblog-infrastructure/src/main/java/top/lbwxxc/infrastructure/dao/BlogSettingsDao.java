package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.BlogSettings;

@Mapper
public interface BlogSettingsDao {

    int deleteByPrimaryKey(Long id);

    int insert(BlogSettings record);

    int insertSelective(BlogSettings record);

    BlogSettings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogSettings record);

    int updateByPrimaryKey(BlogSettings record);

}