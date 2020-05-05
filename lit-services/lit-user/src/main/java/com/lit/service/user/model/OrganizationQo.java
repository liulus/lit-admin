package com.lit.service.user.model;

import com.lit.support.data.domain.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class OrganizationQo extends PageRequest {

    private static final long serialVersionUID = -3178668574403099542L;

    private String keyword;

    private Long parentId = 0L;

    private String orgCode;

}
