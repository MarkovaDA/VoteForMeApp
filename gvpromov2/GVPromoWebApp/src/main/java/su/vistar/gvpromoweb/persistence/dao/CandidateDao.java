package su.vistar.gvpromoweb.persistence.dao;


import java.util.List;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.CandidateEntity;

@Repository(value = "candidateDao")
public class CandidateDao extends AbstractDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*получаем текущего (активного) кандидата*/
    public CandidateEntity getActive() {
        return  (CandidateEntity)sessionFactory.getCurrentSession()
                .createCriteria(CandidateEntity.class)
                .add(Restrictions.eq("active", true))
                .uniqueResult();
    }
    
    
    public CandidateEntity getCandidateById(Integer id){
         return  (CandidateEntity)sessionFactory.getCurrentSession()
                .createCriteria(CandidateEntity.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
    
    /*список всех кандидатов*/
    public List<CandidateEntity> getCandidates() {
        return  sessionFactory.getCurrentSession()
                .createCriteria(CandidateEntity.class)
                .list();
    }
}
