package com.example.todoapp.User.Models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_username")
    @NotNull
    private String username;

    @Column(name = "user_password")
    @NotNull
    private String password;

    @Column(name = "remote")
    @NotNull
    private boolean remoteDB;

    @Column(name = "role_admin")
    private boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.isAdmin)
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
            return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
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
