package su.vistar.gvpromoweb.persistence.dao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.CandidateUsers;


@Repository(value="candidateUsersDao")
public class CandidateUsersDao extends AbstractDao{
    
    /*получаем кандидата из таблицы candidates по его вк uid*/
    public CandidateUsers getCandidateForVkUser(String vkUserId) {
        
        return  (CandidateUsers)sessionFactory.getCurrentSession()
                .createCriteria(CandidateUsers.class)
                .add(Restrictions.eq("vkUserId", vkUserId))
                .uniqueResult();
    }  
}
