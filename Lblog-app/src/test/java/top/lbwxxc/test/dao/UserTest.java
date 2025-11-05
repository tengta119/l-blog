package top.lbwxxc.test.dao;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.infrastructure.dao.RoleDao;
import top.lbwxxc.infrastructure.dao.po.Role;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private RoleDao roleDao;

    @Test
    public void testInsert() {
        List<Role> roles = roleDao.selectAll();
        System.out.println(roles);
    }
}
