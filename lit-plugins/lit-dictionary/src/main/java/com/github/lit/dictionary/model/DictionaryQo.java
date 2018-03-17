package com.github.lit.dictionary.model;

import com.github.lit.commons.page.Page;
import lombok.*;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryQo extends Page {

    private static final long serialVersionUID = -2144418218430870185L;

    private String keyword;

    private String dictKey;

    private String dictValue;

    private String memo;

    private Integer dictLevel;

    private Long parentId;

    private Boolean system;

    private Boolean queryRoot;
}
