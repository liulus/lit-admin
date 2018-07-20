package com.github.lit.menu.model;

import com.github.lit.page.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2018/1/15 10:41
 * version $Id: MenuQo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuQo extends Page{

    private static final long serialVersionUID = 1204535134806916841L;

    private Long id;

    private Long parentId = 0L;

    private String menuCode;




}
