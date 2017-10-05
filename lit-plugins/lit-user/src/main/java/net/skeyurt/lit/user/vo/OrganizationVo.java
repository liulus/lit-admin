package net.skeyurt.lit.user.vo;

import lombok.*;
import net.skeyurt.lit.commons.page.Pager;

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
public class OrganizationVo extends Pager {

    private static final long serialVersionUID = -3178668574403099542L;


    private Long parentId;

    private String orgCode;

}
