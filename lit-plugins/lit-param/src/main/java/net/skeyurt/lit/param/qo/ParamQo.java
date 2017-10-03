package net.skeyurt.lit.param.qo;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.commons.page.Pager;

/**
 * User : liulu
 * Date : 17-9-17 下午3:03
 * version $Id: ParamQo.java, v 0.1 Exp $
 */
@Getter
@Setter
public class ParamQo extends Pager {

    private static final long serialVersionUID = -117622963182937153L;


    private Boolean system;

    private String paramCode;



}
