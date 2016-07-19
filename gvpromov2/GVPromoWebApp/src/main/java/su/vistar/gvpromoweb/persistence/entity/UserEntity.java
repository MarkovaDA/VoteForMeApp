package su.vistar.gvpromoweb.persistence.entity;

import su.vistar.gvpromoweb.persistence.entity.security.SystemRoleEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "appuser")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "vk_id")
    private String vkId;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "appuser_security_role",
            joinColumns = {
                    @JoinColumn(name = "appuser_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", nullable = false, updatable = false)}
    )
    private SystemRoleEntity role;
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SystemRoleEntity getRole() {
        return role;
    }

    public void setRole(SystemRoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{"
                + "id=" + id
                + ", createDate=" + createDate
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", enabled=" + enabled
                + '}';
    }


}
