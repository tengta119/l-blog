package top.lbwxxc.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.Category;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CategoryDao {

    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectCategoryByPageAndData(int offset, int pageSize, String name, LocalDate startDate, LocalDate endDate);
}