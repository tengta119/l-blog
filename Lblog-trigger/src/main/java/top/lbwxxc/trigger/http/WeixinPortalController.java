package top.lbwxxc.trigger.http;



import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.lbwxxc.domain.user.service.ILoginService;
import top.lbwxxc.types.weixin.MessageTextEntity;
import top.lbwxxc.types.weixin.SignatureUtil;
import top.lbwxxc.types.weixin.WxScanEvent;
import top.lbwxxc.types.weixin.XmlUtil;


/**
 * 微信服务对接，对接地址：<a href="http://http://lbwxxc.natapp1.cc/api/v1/weixin/portal/receive">/api/v1/weixin/portal/receive</a>
 * <p>
 * <a href="http://http://lbwxxc.natapp1.cc/api/v1/weixin/portal/receive/">...</a>
 */
@Slf4j
@RestController()
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    @Value("${wx.originalid}")
    private String originalid;
    @Value("${wx.token}")
    private String token;
    @Resource
    private ILoginService loginService;

    @GetMapping(value = "receive/", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check(token, signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    //当有用户向公众号发送文本消息时，微信平台会将信息转发到这个方法，该方法处理完请求后，会把信息返回给微信平台，再有微信平台把处理后的消息发送给用户
    @PostMapping(value = "receive/", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        log.info("接收微信公众号信息请求 {} 开始 {}", openid, requestBody);

        try {
            // 消息转换
            WxScanEvent wxScanEvent = XmlUtil.fromXml(requestBody, WxScanEvent.class);
            if (wxScanEvent != null && "event".equals(wxScanEvent.getMsgType()) && "SCAN".equals(wxScanEvent.getEvent())) {
                loginService.saveWxLoginState(wxScanEvent.getTicket(), openid);
                log.info("登录成功 {}", openid);
                return buildMessageTextEntity(openid, "登录成功");
            }

            return buildMessageTextEntity(openid, "你好");
        } catch (Exception e) {
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }

}
