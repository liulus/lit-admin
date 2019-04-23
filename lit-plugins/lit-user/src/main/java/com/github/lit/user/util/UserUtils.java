package com.github.lit.user.util;

import com.github.lit.plugin.core.model.LoginUser;
import com.github.lit.support.util.ClassUtils;
import com.github.lit.support.util.EncryptUtils;
import com.github.lit.support.util.SpringContextUtils;
import com.github.lit.support.util.WebUtils;
import com.github.lit.user.context.UserConst;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User : liulu
 * Date : 17-10-3 下午4:37
 * version $Id: UserUtils.java, v 0.1 Exp $
 */
public class UserUtils {


    public static LoginUser getLoginUser() {
        return getLoginUser(WebUtils.getSession());
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

//        if (existLevelIndexes == null || existLevelIndexes.isEmpty()) {
//            return parentLevelIndex + Strings.padStart("1", 3, '0');
//        }
//        Collections.sort(existLevelIndexes);
//
//        int count = existLevelIndexes.size();
//        int maxSerialNum = Integer.parseInt(existLevelIndexes.get(count - 1).substring(parentLevelIndex.length()));
//
//        if (maxSerialNum <= count) {
//            int next = maxSerialNum + 1;
//            String nextStr = Strings.padStart(String.valueOf(next), 3, '0');
//            return parentLevelIndex + nextStr;
//        }
//
//        int i = 1;
//        for (String serialNum : existLevelIndexes) {
//            int currentNum = Integer.parseInt(serialNum.substring(parentLevelIndex.length()));
//            if (!Objects.equals(i, currentNum)) {
//                String nextStr = Strings.padStart(String.valueOf(i), 3, '0');
//                return parentLevelIndex + nextStr;
//            }
//            i++;
//        }

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

}
