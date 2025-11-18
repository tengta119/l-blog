package generate;

import generate.UserExternalUrls;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExternalUrlsDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserExternalUrls record);

    int insertSelective(UserExternalUrls record);

    UserExternalUrls selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserExternalUrls record);

    int updateByPrimaryKey(UserExternalUrls record);
}