package top.lbwxxc.test.trigger;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.api.dto.login.LoginRequestDTO;
import top.lbwxxc.api.dto.login.VerificationCodeRequestDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.trigger.http.LoginOrRegisterController;
import top.lbwxxc.trigger.http.VerificationCodeController;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private VerificationCodeController verificationCodeController;
    @Resource
    private LoginOrRegisterController loginOrRegisterController;


    @Test
    public void userTest(){
        Response<String> phone = verificationCodeController.sendVerificationCode(new VerificationCodeRequestDTO("phone", "15981160633", "www."));
        System.out.println(phone.getInfo());
    }

    @Test
    public void userTest2(){
        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .phone("15981160633")
                .code("438324")
                .build();
        loginOrRegisterController.loginOrRegister(loginRequestDTO);
    }
}
