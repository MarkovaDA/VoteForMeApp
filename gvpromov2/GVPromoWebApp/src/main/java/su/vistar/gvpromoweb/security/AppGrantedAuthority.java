package su.vistar.gvpromoweb.security;

import org.springframework.security.core.GrantedAuthority;

public class AppGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -3774892224050478245L;

    private String role;

    public AppGrantedAuthority() {
    }

    public AppGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

}
