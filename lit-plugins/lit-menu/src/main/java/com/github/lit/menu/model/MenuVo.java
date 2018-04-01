package com.github.lit.menu.model;

import lombok.Data;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/13 19:49
 * version $Id: MenuVo.java, v 0.1 Exp $
 */
@Data
public abstract class MenuVo {

    private String code;

    private String name;

    private String icon;

    private String url;

    private Integer orderNum;

    private String type;

    private String memo;

    @Data
    public static class Detail extends MenuVo{

        private Long menuId;

        private Long parentId;

        private Boolean enable;

        private Boolean isParent;

        private List<Detail> children;

    }

    @Data
    public static class Add extends MenuVo{
        private Long parentId;
    }

    @Data
    public static class Update extends MenuVo{
        private Long menuId;
    }



}
