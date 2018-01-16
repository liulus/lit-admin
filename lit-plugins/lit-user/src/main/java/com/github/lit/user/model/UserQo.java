package com.github.lit.user.model;

import com.github.lit.commons.page.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2018/1/16 15:17
 * version $Id: UserQo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQo extends Page{

    private static final long serialVersionUID = 3920536469407790559L;

    private Long userId;

    private String userName;

    private String orgId;

    private String orgCode;

    private String orgName;

    private String serialNum;

}
