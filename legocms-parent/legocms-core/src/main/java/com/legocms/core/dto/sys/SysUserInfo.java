package com.legocms.core.dto.sys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.legocms.core.dto.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserInfo extends Dto {
    private static final long serialVersionUID = -6450105877713244685L;
    private String code;
    private String name;
    private String password;
    private List<Role> roles = new ArrayList<Role>();

    public Set<String> getRoleCodes() {
      Set<String> codes = new HashSet<String>();
      for (Role role : this.roles) {
        codes.add(role.getCode());
      }
      return codes;
    }

    public Set<String> getRoleNames() {
      Set<String> names = new HashSet<String>();
      for (Role role : this.roles) {
        names.add(role.getCode());
      }
      return names;
    }

}