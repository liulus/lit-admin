package net.skeyurt.lit.user.util;

import com.google.common.base.Strings;
import net.skeyurt.lit.commons.spring.web.WebUtils;
import net.skeyurt.lit.user.context.LoginUser;
import net.skeyurt.lit.user.context.UserConst;

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
        return getLoginUser(WebUtils.getSession());
    }

    public static LoginUser getLoginUser(HttpSession session) {

        LoginUser loginUser = (LoginUser) session.getAttribute(UserConst.LOGIN_USER);

        // todo 测试使用
        if (loginUser == null) {
            loginUser = new LoginUser();
            loginUser.setOrgCode("999000");
            loginUser.setSerialNum("001");
        }

        return loginUser;
    }

    public static String nextSerialNum(String parentSerialNum, List<String> existSerialNums) {

        if (existSerialNums == null || existSerialNums.isEmpty()) {
            return parentSerialNum + Strings.padStart("1", 3, '0');
        }
        Collections.sort(existSerialNums);

        int count = existSerialNums.size();
        int maxSerialNum = Integer.parseInt(existSerialNums.get(count - 1).substring(parentSerialNum.length()));

        if (maxSerialNum <= count) {
            int next = maxSerialNum + 1;
            String nextStr = Strings.padStart(String.valueOf(next), 3, '0');
            return parentSerialNum + nextStr;
        }

        int i = 1;
        for (String serialNum : existSerialNums) {
            int currentNum = Integer.parseInt(serialNum.substring(parentSerialNum.length()));
            if (!Objects.equals(i, currentNum)) {
                String nextStr = Strings.padStart(String.valueOf(i), 3, '0');
                return parentSerialNum + nextStr;
            }
            i++;
        }

        return "";
    }

}
