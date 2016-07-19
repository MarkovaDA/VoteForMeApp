/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author darya
 */
@Entity
@Table(name="regions")
public class RegionEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "api_id")
    private Integer apiId; 
    
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "region")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<AreaEntity> areas; //районы в регионе 

    public List<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaEntity> areas) {
        this.areas = areas;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
           
}

