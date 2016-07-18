package su.vistar.gvpromoweb.persistence.entity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "candidates")
public class CandidateEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    
    @Column(name = "appuser_id")
    private Integer appUserId;
    
    @Column(name = "city_id")
    private Integer cityId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name="vk_id")
    private String vkId;
    
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "candidate")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<MessageEntity> messages; //соответствующие кандидату сообщения

    
    
    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
   
    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }
    
    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    } 
    
    public CandidateEntity() {
        
    }
   
    @Column(name = "active", nullable = false, columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active = false;

    public Boolean getStatus() {
        return active;
    }

    public void setStatus(Boolean status) {
        this.active = status;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
