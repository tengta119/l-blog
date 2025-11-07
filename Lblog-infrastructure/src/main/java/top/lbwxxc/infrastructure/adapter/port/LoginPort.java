package top.lbwxxc.infrastructure.adapter.port;


import cn.hutool.core.util.IdUtil;
import com.google.common.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import top.lbwxxc.domain.user.adapter.port.ILoginPort;
import top.lbwxxc.infrastructure.gateway.AliyunSmsHelper;
import top.lbwxxc.infrastructure.gateway.IWeixinApiService;
import top.lbwxxc.infrastructure.gateway.dto.WeixinQrCodeRequestDTO;
import top.lbwxxc.infrastructure.gateway.dto.WeixinQrCodeResponseDTO;
import top.lbwxxc.infrastructure.gateway.dto.WeixinTokenResponseDTO;

import java.io.IOException;

@Service
@Slf4j
public class LoginPort implements ILoginPort {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appSecret;

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private IWeixinApiService weixinApiService;
    @Resource
    private Cache<String, String> cache;

    @Override
    public void sendVerificationPhoneCode(String phone, String code) {
        String signName = "阿里云短信测试";
        String templateCode = "SMS_154950909";
        String templateParam = String.format("{\"code\":\"%s\"}", code);
        aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
    }

    @Override
    public void sendVerificationEmailCode(String email, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("15981160633@163.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Lblog 验证码");
        simpleMailMessage.setText(code);
        mailSender.send(simpleMailMessage);
    }

    @Override
    public String getWxQrCodeTicket() throws IOException {
        String sceneStr = IdUtil.getSnowflake().nextIdStr();
        return getWxQrCodeTicket(sceneStr);
    }

    @Override
    public String getWxQrCodeTicket(String sceneStr) throws IOException {

        String accessToken = getWxAccessToken(appid, appSecret);

        // 2. 生成 ticket
        WeixinQrCodeRequestDTO weixinQrCodeReq = WeixinQrCodeRequestDTO.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrCodeRequestDTO.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeRequestDTO.ActionInfo.builder()
                        .scene(WeixinQrCodeRequestDTO.ActionInfo.Scene.builder()
                                .scene_str(sceneStr)
                                .scene_id(100601)
                                .build())
                        .build())
                .build();

        Call<WeixinQrCodeResponseDTO> call = weixinApiService.createQrCode(accessToken, weixinQrCodeReq);
        WeixinQrCodeResponseDTO weixinQrCodeResponseDTO = call.execute().body();
        assert null != weixinQrCodeResponseDTO;
        return weixinQrCodeResponseDTO.getTicket();
    }

    public String getWxAccessToken(String appId, String appSecret) throws IOException {

        String token = cache.getIfPresent(appId);
        if (token != null) {
            return token;
        }

        Call<WeixinTokenResponseDTO> clientCredential = weixinApiService.getToken("client_credential", appId, appSecret);
        WeixinTokenResponseDTO weixinTokenResponseDTO = clientCredential.execute().body();
        assert weixinTokenResponseDTO != null;

        log.info("获取 AccessToken {}", weixinTokenResponseDTO);
        cache.put(appId, weixinTokenResponseDTO.getAccess_token());

        return weixinTokenResponseDTO.getAccess_token();
    }

}
