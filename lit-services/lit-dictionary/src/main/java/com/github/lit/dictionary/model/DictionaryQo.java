package com.github.lit.dictionary.model;

import com.github.lit.support.data.domain.PageRequest;
import lombok.Data;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
@Data
public class DictionaryQo extends PageRequest {

    private static final long serialVersionUID = -2144418218430870185L;

    private String keyword;

    private Long parentId;

}
