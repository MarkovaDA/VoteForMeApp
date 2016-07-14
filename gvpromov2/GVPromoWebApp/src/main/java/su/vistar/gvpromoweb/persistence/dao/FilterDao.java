package su.vistar.gvpromoweb.persistence.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.FilterEntity;

@Repository(value="filterDao")
public class FilterDao extends AbstractDao{
    
    /*извлечение фильтра по id-кандидата*/   
    public FilterEntity get(Integer candidateId){
         return (FilterEntity)sessionFactory.getCurrentSession()
                .createCriteria(FilterEntity.class)
                .add(Restrictions.eq("candidateId", candidateId))
                .uniqueResult();
    }      
}
