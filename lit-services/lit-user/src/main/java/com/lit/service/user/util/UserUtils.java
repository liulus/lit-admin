package com.lit.service.user.util;

import com.lit.service.core.model.LoginUser;
import com.lit.service.user.context.UserConst;
import com.lit.support.util.ClassUtils;
import com.lit.support.util.SpringContextUtils;
import com.lit.support.util.secret.EncryptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 17-10-3 下午4:37
 * version $Id: UserUtils.java, v 0.1 Exp $
 */
public class UserUtils {


    public static LoginUser getLoginUser() {
        return null;
    }

    public static LoginUser getLoginUser(HttpSession session) {

        LoginUser loginUser = (LoginUser) session.getAttribute(UserConst.LOGIN_USER);

        // todo 测试使用
        if (loginUser == null) {
            loginUser = new LoginUser();
            loginUser.setUserName("liulu");
            loginUser.setLevelIndex("001");
            loginUser.setOrgCode("999000");
        }

        return loginUser;
    }

    public static String nextLevelIndex(String parentLevelIndex, List<String> existLevelIndexes) {

        if (existLevelIndexes == null || existLevelIndexes.isEmpty()) {
            return parentLevelIndex + padStart("1", 3, '0');
        }
        Collections.sort(existLevelIndexes);

        int count = existLevelIndexes.size();
        int maxSerialNum = Integer.parseInt(existLevelIndexes.get(count - 1).substring(parentLevelIndex.length()));

        if (maxSerialNum <= count) {
            int next = maxSerialNum + 1;
            String nextStr = padStart(String.valueOf(next), 3, '0');
            return parentLevelIndex + nextStr;
        }

        int i = 1;
        for (String serialNum : existLevelIndexes) {
            int currentNum = Integer.parseInt(serialNum.substring(parentLevelIndex.length()));
            if (!Objects.equals(i, currentNum)) {
                String nextStr = padStart(String.valueOf(i), 3, '0');
                return parentLevelIndex + nextStr;
            }
            i++;
        }

        return "";
    }

    public static String encode(String password) {
        if (ClassUtils.isPresent("org.springframework.security.crypto.password.PasswordEncoder")) {
            PasswordEncoder passwordEncoder = SpringContextUtils.getBean(PasswordEncoder.class);
            if (passwordEncoder != null) {
                return passwordEncoder.encode(password);
            }
        }
        return EncryptUtils.encodeBCrypt(password);
    }

    public static String padStart(String string, int minLength, char padChar) {
        Objects.requireNonNull(string); // eager for GWT.
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }

}
