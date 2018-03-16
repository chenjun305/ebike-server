package net.zriot.ebike.service.sms.operator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.qcloudsms.*;

import java.util.ArrayList;

@Component
public class TencentSmsOperator implements SmsOperator {
    @Value("${sms.appid:1400051631}")
    private int appid;
    @Value("${sms.appkey:ccdb1747af0916ae49f3beebedb1efd2}")
    private String appkey;
    @Value("${sms.templId:59810}")
    private int templId;
    @Value("${sms.sign:小鸣单车}")
    private String sign;
    @Value("${sms.nationCode:86}")
    private String nationCode;

    @Override
    public boolean send(String phoneNo, String content) {

        try {
            SmsSingleSender sender = new   SmsSingleSender(appid, appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(content);
            SmsSingleSenderResult result = sender.sendWithParam(nationCode, phoneNo, templId, params, sign, "", "");
            return true;
        } catch (Exception e) {
        }
        return false;
    }

}
