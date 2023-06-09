package com.example.csws.config.auth;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

	private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }

    public User getUser() {
		return user;
	}

    public Long getId() {
        return user.getId();
    }
    public String getRole() { return user.getRoles(); }
    public int getDepartmentId() {
        Department department = user.getDepartment();
        return department.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("----------------------------------------------------");
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        user.getRoleList().forEach(r -> {
            authorities.add(()->{ return r;});
        });
        return authorities;
    }
}
