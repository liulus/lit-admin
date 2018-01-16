package com.github.lit.security.model;

import com.github.lit.commons.page.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2018/1/15 17:34
 * version $Id: AuthorityQo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityQo extends Page {

    private static final long serialVersionUID = 8174220538849445482L;

    private String authorityType;
}
