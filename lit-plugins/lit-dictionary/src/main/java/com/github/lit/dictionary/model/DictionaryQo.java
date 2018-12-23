package com.github.lit.dictionary.model;

import com.github.lit.support.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryQo extends PageRequest {

    private static final long serialVersionUID = -2144418218430870185L;

    private String keyword;

    private Long parentId = 0L;

    private String dictKey;

    private String dictValue;

    private Boolean order = false;
}
