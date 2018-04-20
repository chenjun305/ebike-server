package com.ecgobike.helper;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.entity.User;

/**
 * Created by ChenJun on 2018/4/20.
 */
public class FileUrlHelper {
    public static User dealUser(User user) {
        if (user.getAvatar() != null && user.getAvatar().length() > 0) {
            user.setAvatar(Constants.USER_AVATAR_URL_PREFIX + user.getAvatar());
        }
        String idCardPics = user.getIdCardPics();
        String idCardUrls = null;
        if (idCardPics != null && idCardPics.length() > 0) {
            String[] strs = idCardPics.split(",");
            for (String str : strs) {
                String url = Constants.USER_IDCARD_URL_PREFIX + str;
                if (idCardUrls == null) {
                    idCardUrls = url;
                } else {
                    idCardUrls = idCardUrls + "," + url;
                }

            }
            user.setIdCardPics(idCardUrls);
        }

        return user;
    }
}
