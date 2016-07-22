package su.vistar.gvpromoweb.persistence.dao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.AreaEntity;

/**
 *
 * @author darya
 */
@Repository(value = "areaDao")
public class AreaDao extends AbstractDao{
    
    public AreaEntity getAreaByApiId(Integer apiId) {
        return (AreaEntity)sessionFactory.getCurrentSession()
                .createCriteria(AreaEntity.class)
                .add(Restrictions.eq("apiId", apiId))
                .uniqueResult();
    }
}
