package su.vistar.gvpromoweb.persistence.dao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.CandidateUsers;


@Repository(value="candidateUsersDao")
public class CandidateUsersDao extends AbstractDao{
    
    
    public CandidateUsers getCandidateForVkUser(String vkUserId) {
        
        return  (CandidateUsers)sessionFactory.getCurrentSession()
                .createCriteria(CandidateUsers.class)
                .add(Restrictions.eq("vkUserId", vkUserId))
                .uniqueResult();
    }
    public List<CandidateUsers> allSendorsByCandidate(Integer candidateId) {
        return  sessionFactory.getCurrentSession()
                .createCriteria(CandidateUsers.class)
                .add(Restrictions.eq("candidateId", candidateId))
                .list();
    }

}
