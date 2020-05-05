package com.lit.service.security.model;

import com.lit.support.data.Condition;
import com.lit.support.data.Logic;
import com.lit.support.data.domain.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/1/15 17:34
 * version $Id: AuthorityQo.java, v 0.1 Exp $
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityQo extends PageRequest {

    private static final long serialVersionUID = 8174220538849445482L;

    private String authorityType;

    @Condition(logic = Logic.IN, property = "userId")
    private List<Long> userIds;
}
