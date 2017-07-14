package net.skeyurt.lit.dictionary.qo;

import lombok.*;
import net.skeyurt.lit.commons.page.Pager;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryQo.java, v 0.1 Exp $
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryQo extends Pager {

    private static final long serialVersionUID = -2144418218430870185L;

    private String dictKey;

    private String dictValue;

    private String memo;

    private Integer dictLevel;

    private Long parentId;

    private Boolean system;

    private Boolean queryRoot;
}
