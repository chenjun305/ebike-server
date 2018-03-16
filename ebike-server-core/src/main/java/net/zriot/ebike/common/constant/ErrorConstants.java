package net.zriot.ebike.common.constant;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.log.LogHelp;

// generate by common/tools/ErrorCodeCreater

public class ErrorConstants {
	public static final int FAIL = 0;//System Error！
	public static final int SUCC = 1;//Success！
	public static final int ERR_DB = 2;//Database Exception！
	public static final int ERR_REQUEST = 3;//Request Error！
	public static final int ERR_REPEATED_LOGIN = 4;//Login in other device Error！
	public static final int ERR_LOCK_ACCOUNT = 5;//Account Banned Error！
	public static final int ERR_BANNED_RIDING = 6;//Riding Banned Error！
	public static final int ERR_REQUEST_VERSION = 7;//Version Error！
	public static final int ERR_LOGIN_OVERDUE = 8;//Login Verification Error！
	public static final int ERR_ILLEGAL_REQUEST = 9;//Illegal Request！
	public static final int ERR_FILE_NOT_FOUND = 10;//Not found file Error！
	public static final int ERR_INIT_DB = 11;//Database initialization Error！
	public static final int LOGIN_OVERDUE = 12;//Login Expired！
	public static final int ERR_SIGN = 13;//Sign Verification Error！
	public static final int LACK_PARAMS_CHECK_SIGN = 14;//Lack Sign params Error！
	public static final int ILLEGAL_PARAMS_CHECK_SIGN = 15;//Illegal Sign Params Error！
	public static final int ERR_PARAMS = 16;//Request params Error！
	public static final int ERR_EMPTY_UPLOAD_PIC = 17;//Upload Pic empty Error！
	public static final int ERR_REQUEST_TOO_FAST = 18;//Request too fast！
	public static final int ERR_WRONG_VERSION = 19;//Please update to the latest version！
	public static final int ILLEGAL_IMAGE_FORMAT = 20;//Illegal image format！
	public static final int LACK_PARAMS_TOKEN = 21;//Lack Token Params！
	public static final int ILLEGAL_TOKEN = 22;//Illegal Token Params！
	public static final int ILLEGAL_UID = 23;//Illegal UID Info！
	public static final int GET_PIN_TOO_FAST = 24;//Get PIN Code Too Fast！
	public static final int SMS_PIN_INVALID = 25;//SMS Pin Invalid！
	public static final int USER_NOT_FOUND = 100;//User Not Found！
	public static final int USER_CREATE_FAIL = 101;//User Creation Failed！
	public static final int NOT_SUPPORT_MONEY_DET_TYPE = 102;//Not Support Money Det Type！
	public static final int LACK_MONEY = 103;//Lack Money！
	public static final int UPDATE_USER_INFO_FAIL = 104;//Update User Info Fail！
	public static final int ILLEGAL_AVATAR_FORMAT = 105;//Illegal Avatar Image Format！
	public static final int NICK_NAME_TOO_LONG = 106;//Nickname Too Long！
	public static final int AUTHENTICATION_FAIL = 107;//Authentication Fail！
	public static final int AUTHENTICATION_REPEATE = 108;//Authentication Repeat！
	public static final int AUTHENTICATION_SUCCED_ALREADY = 109;//Authentication Succed Already！
	public static final int NOT_EXIST_EBIKE = 200;//Not Exist EBike！
	public static final int ALREADY_MEMBERSHIP = 201;//Already Membership！
	public static final int NO_MEMBERSHIP = 202;//No Membership！
	public static final int ALREADY_RENEW = 203;//Already renew！
	public static final int NOT_RETURN_OLD_BATTERY = 204;//Please return old battery first！
	public static final int NEED_RENEW_MONTH_FEE = 205;//Need renew month fee
	public static final int NOT_YOUR_EBIKE = 206;//Not Your EBike
	public static final int NOT_EXIST_BATTERY = 300;//Not Exist Battery！
	public static final int NOT_RETURNED_BATTERY = 301;//Not Returned Battery！
	public static final int ORDER_PAID_REPEATED = 600;//UserOrder Paid Repeated！
	public static final int EXIST_UNPAID_ORDER = 601;//Exist Unpaid UserOrder！
	public static final int NO_UNPAID_ORDER = 602;//No Unpaid UserOrder！
	public static final int ERR_PAY_ORDER = 603;//UserOrder Payment Error！
	public static final int ERR_NO_ORDER = 604;//UserOrder Not Found！

	
	static Map<Integer, String> map = new HashMap<Integer, String>();
	
	public static String getDesc(int code) {
		if (map.size() == 0) {
			Document document = null;
			Element root = null;
			String ERR_CODE_URL = System.getenv("err_code");
			if (ERR_CODE_URL == null) {
            	ERR_CODE_URL = "/Users/yueqi/zriot/ebike-server/err_code.xml";
            }
			SAXReader reader = new SAXReader();
			try {
				document = reader.read(new File(ERR_CODE_URL));
			} catch (DocumentException e) {
				GException ge = new GException(ERR_FILE_NOT_FOUND, ERR_CODE_URL + " not found!", e);
				LogHelp.doLogAppErr(ge);
			}
			root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> errs = root.elements("err");
			for (Element e : errs) {
				map.put(Integer.parseInt(e.attributeValue("id")), e.attributeValue("description"));
			}
		}
		
		if (!map.containsKey(code)) {
			return map.get(ErrorConstants.FAIL);
		}
		return map.get(code);
	}
}
