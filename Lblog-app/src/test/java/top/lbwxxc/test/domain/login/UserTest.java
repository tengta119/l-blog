package top.lbwxxc.test.domain.login;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {


    @Resource
    private DefaultUserLoginStrategyFactory domainUserLoginStrategyFactory;

    @Test
    public void test() throws Exception {
        StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> handler = domainUserLoginStrategyFactory.strategyHandler();
        LoginUserEntity loginUserEntity = new LoginUserEntity();
        loginUserEntity.setPhone("15981160633");
        loginUserEntity.setReqPassword("123456");
        loginUserEntity.setType("0");
        UserAccountEntity apply = handler.apply(loginUserEntity, new DefaultUserLoginStrategyFactory.DynamicContext());
        System.out.println(apply);
    }
}
