
package su.vistar.gvpromoweb.persistence.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "candidate_sendors")
public class CandidateUsers implements Serializable{    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    
    @Column(name = "candidate_id")
    private Integer candidateId; 
    
    @Column(name = "vkuser_id")
    private String vkUserId;  
    
           
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public void setVkUserId(String vkUserId) {
        this.vkUserId = vkUserId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public String getVkUserId() {
        return vkUserId;
    }
}
