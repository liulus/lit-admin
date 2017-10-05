package net.skeyurt.lit.user.vo;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.commons.page.Pager;

/**
 * User : liulu
 * Date : 17-10-3 下午3:51
 * version $Id: UserVo.java, v 0.1 Exp $
 */
@Getter
@Setter
public class UserVo extends Pager{

    private static final long serialVersionUID = -7054655578166527259L;

    private Long userId;

    private String userCode;

    private Long orgId;

    private String orgCode;

    private String serialNum;



}
