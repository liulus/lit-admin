package com.github.lit.security.model;

import com.github.lit.jdbc.annotation.GeneratedValue;
import com.github.lit.jdbc.annotation.Id;
import com.github.lit.jdbc.annotation.Table;
import com.github.lit.jdbc.enums.GenerationType;
import com.github.lit.plugin.context.PluginConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/11/19 16:20
 * version $Id: Authority.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1650537028788209650L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    /**
     * 权限码
     */
    private String authorityCode;

    /**
     * 权限码名称
     */
    private String authorityName;

    /**
     * 权限类型
     */
    private String authorityType;

    /**
     * 备注
     */
    private String memo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority = (Authority) o;
        return Objects.equals(authorityId, authority.authorityId) &&
                Objects.equals(authorityCode, authority.authorityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityId, authorityCode);
    }
}
