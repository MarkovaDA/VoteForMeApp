package su.vistar.gvpromoweb.persistence.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * эта сущность отражает историю пользования приложением
 */
@Entity
@Table(name="history")
public class HistoryEntity {
      
    @Column(name = "vkuser_id")
    private String vkUserId;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "query_limit")
    private Integer countQuery;
    
    @Column(name = "last_date")
    private Date lastDate;
    

    public String getVkUserId() {
        return vkUserId;
    }

    public void setVkUserId(String vkUserId) {
        this.vkUserId = vkUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountQuery() {
        return countQuery;
    }

    public void setCountQuery(Integer countQuery) {
        this.countQuery = countQuery;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }   
}
