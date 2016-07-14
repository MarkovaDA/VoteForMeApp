package su.vistar.gvpromoweb.persistence.entity.security;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "security_role")
public class SystemRoleEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_admin_role")
    private Boolean isAdminRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAdminRole() {
        return isAdminRole;
    }

    public void setIsAdminRole(Boolean isAdminRole) {
        this.isAdminRole = isAdminRole;
    }
}
