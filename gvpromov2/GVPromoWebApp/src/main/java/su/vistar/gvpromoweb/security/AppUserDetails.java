package su.vistar.gvpromoweb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = 4641303544672482845L;

    private UserEntity user;
    private List<GrantedAuthority> authorities;

    public AppUserDetails() {
    }

    public AppUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>(1);
            GrantedAuthority ga = new AppGrantedAuthority("ROLE_USER");
            authorities.add(ga);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isEnabled();
    }

    @Override
    public String toString() {
        return "AppUserDetails [getAuthorities()=" + getAuthorities()
                + ", getPassword()=" + getPassword() + ", getUsername()="
                + getUsername() + ", isAccountNonExpired()="
                + isAccountNonExpired() + ", isAccountNonLocked()="
                + isAccountNonLocked() + ", isCredentialsNonExpired()="
                + isCredentialsNonExpired() + ", isEnabled()=" + isEnabled()
                + ", getUserBySecret()=" + getUser() + "]";
    }

    public UserEntity getUser() {
        return user;
    }


}
