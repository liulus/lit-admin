package com.github.lit.model;

import com.lit.support.data.domain.PageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * User : liulu
 * Date : 2018/1/15 17:35
 * version $Id: RoleQo.java, v 0.1 Exp $
 */
@Getter
@Setter
public class RoleQo extends PageRequest {

    private static final long serialVersionUID = -1895536277221625300L;

    private Long userId;

}
