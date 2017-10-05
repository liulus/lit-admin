package net.skeyurt.lit.user.util;

import net.skeyurt.lit.commons.util.WebUtils;
import net.skeyurt.lit.user.context.LoginUser;
import net.skeyurt.lit.user.context.UserConst;

import javax.servlet.http.HttpSession;

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

        Object loginUser = session.getAttribute(UserConst.LOGIN_USER);

        return loginUser == null ? null : (LoginUser) loginUser;
    }


}
