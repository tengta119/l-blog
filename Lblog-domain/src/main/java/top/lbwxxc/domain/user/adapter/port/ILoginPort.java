package top.lbwxxc.domain.user.adapter.port;


import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ILoginPort {

    void sendVerificationPhoneCode(String phone, String code);

    void sendVerificationEmailCode(String email, String code);

    public String getWxQrCodeTicket(String sceneStr) throws IOException;

    String getWxQrCodeTicket() throws IOException;
}
