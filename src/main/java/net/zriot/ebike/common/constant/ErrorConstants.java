package net.zriot.ebike.common.constant;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.log.LogHelp;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ErrorConstants {
    public static final int FAIL = 0;
    public static final int SUCC = 1;
    public static final int ERR_DB = 2;//数据库异常，请联系管理员！
    public static final int ERR_REQUEST = 3;//请求数据异常！
    public static final int ERR_REPEATED_LOGIN = 4;//你的帐号已经在其他地方登录了！
    public static final int ERR_LOCK_ACCOUNT = 5;//你当前已经被封号了！
    public static final int ERR_BANNED_RIDING = 6;// You are banned riding！
    public static final int ERR_REQUEST_VERSION = 7;//错误的版本号！
    public static final int ERR_LOGIN_OVERDUE = 8;//登录验证失败，请重新登录！
    public static final int ERR_ILLEGAL_REQUEST = 9;//非法请求！
    public static final int ERR_FILE_NOT_FOUND = 10;//找不到指定文件！
    public static final int ERR_INIT_DB = 11;//数据库初始化异常！
    public static final int LOGIN_OVERDUE = 12;//登录已经过期，请重新登录！
    public static final int ERR_SIGN = 13;//签名验证错误，请重新登录！
    public static final int LACK_PARAMS_CHECK_SIGN = 14;//缺少校验签名参数，请重新登录！
    public static final int ILLEGAL_PARAMS_CHECK_SIGN = 15;//非法的校验参数，请重新登录！
    public static final int ERR_PARAMS = 16;//请求参数错误！
    public static final int ERR_EMPTY_UPLOAD_PIC = 17;//上传的图片为空！
    public static final int ERR_REQUEST_TOO_FAST = 18;//您的请求速度过快，请休息一会儿！
    public static final int ERR_WRONG_VERSION = 19;//请更新到最新版本！
    public static final int ILLEGAL_IMAGE_FORMAT = 20;//非法图片信息！
    public static final int LACK_PARAMS_TOKEN = 21;//缺少token信息！
    public static final int ILLEGAL_TOKEN = 22;//非法的token信息！
    public static final int ILLEGAL_UID = 23;//非法的uid信息！
    public static final int USER_NOT_FOUND = 200;//找不到指定用户！
    public static final int USER_CREATE_FAIL = 201;//用户创建失败！

    public static final int SMS_PIN_INVALID = 101;
    public static final int NOT_EXIST_EBIKE = 301;
    public static final int ALREADY_MEMBERSHIP = 302;
    public static final int NO_MEMBERSHIP = 303;
    public static final int ALREADY_RENEW = 304;
    public static final int LACK_MONEY = 900;

    static Map<Integer, String> map = new HashMap<Integer, String>();

    public static String getDesc(int code) {
        if (map.size() == 0) {
//            Document document = null;
//            Element root = null;
//            String ERR_CODE_URL = System.getenv("err_code");
//            SAXReader reader = new SAXReader();
//            try {
//                document = reader.read(new File(ERR_CODE_URL));
//            } catch (DocumentException e) {
//                GException ge = new GException(ERR_FILE_NOT_FOUND, ERR_CODE_URL + " not found!", e);
//                LogHelp.doLogAppErr(ge);
//            }
//            root = document.getRootElement();
//            @SuppressWarnings("unchecked")
//            List<Element> errs = root.elements("err");
//            for (Element e : errs) {
//                map.put(Integer.parseInt(e.attributeValue("id")), e.attributeValue("description"));
//            }
        }

        if (!map.containsKey(code)) {
            return map.get(ErrorConstants.FAIL);
        }
        return map.get(code);
    }
}
