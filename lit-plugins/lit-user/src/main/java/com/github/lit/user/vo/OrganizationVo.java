package com.github.lit.user.vo;

import com.github.lit.commons.page.Page;
import lombok.*;

/**
 * User : liulu
 * Date : 17-10-5 上午11:27
 * version $Id: OrganizationVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationVo extends Page {

    private static final long serialVersionUID = -3178668574403099542L;


    private Long parentId;

    private String orgCode;

}
