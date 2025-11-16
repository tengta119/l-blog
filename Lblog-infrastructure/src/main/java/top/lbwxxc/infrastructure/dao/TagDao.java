package top.lbwxxc.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lbwxxc.infrastructure.dao.po.Tag;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TagDao {
    int deleteByPrimaryKey(Long id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> selectTagByPageAndData(int offset, int pageSize, String name, LocalDate startDate, LocalDate endDate);

    int logicalDelete(Long id);

    List<Tag> selectTagAll();

    int selectTagSize();

    int insetTags(@Param("tags") List<Tag> tags);
}