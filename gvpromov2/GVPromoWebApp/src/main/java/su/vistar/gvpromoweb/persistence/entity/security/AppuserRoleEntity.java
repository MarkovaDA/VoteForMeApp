package su.vistar.gvpromoweb.persistence.entity.security;

import su.vistar.gvpromoweb.persistence.entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "appuser_security_role")
public class AppuserRoleEntity implements Serializable {

    @ManyToOne(targetEntity = SystemRoleEntity.class, optional = true)
    @JoinColumn(name = "role_id", insertable = true, updatable = true)
    private SystemRoleEntity role;

    @Id
    @ManyToOne(targetEntity = UserEntity.class, optional = true)
    @JoinColumn(name = "appuser_id", insertable = true, updatable = true)
    private UserEntity user;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SystemRoleEntity getRole() {
        return role;
    }

    public void setRole(SystemRoleEntity role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.role);
        hash = 23 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppuserRoleEntity other = (AppuserRoleEntity) obj;
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

}
