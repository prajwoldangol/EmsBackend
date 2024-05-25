package com.prajwol.config;

import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class CustomEmployerDetails implements UserDetails {
    private EmsEmployer emsEmployer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        EmsRole role = emsEmployer.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return emsEmployer.getPassword();
    }

    @Override
    public String getUsername() {
        return emsEmployer.getUsername();
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
}
