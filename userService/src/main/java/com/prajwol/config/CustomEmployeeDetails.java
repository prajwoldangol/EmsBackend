package com.prajwol.config;

import com.prajwol.entity.EmsEmployee;
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
public class CustomEmployeeDetails  implements UserDetails {
    private EmsEmployee emsEmployee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        EmsRole role = emsEmployee.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return emsEmployee.getPassword();
    }

    @Override
    public String getUsername() {
        return emsEmployee.getUsername();
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
