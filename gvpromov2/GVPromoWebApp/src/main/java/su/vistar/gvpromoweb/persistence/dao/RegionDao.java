package su.vistar.gvpromoweb.persistence.dao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.RegionEntity;

/**
 *
 * @author darya
 */
@Repository(value = "regionDao")
public class RegionDao extends AbstractDao{
    
    public RegionEntity getRegionByApiId(Integer apiId) {
        return (RegionEntity)sessionFactory.getCurrentSession()
                .createCriteria(RegionEntity.class)
                .add(Restrictions.eq("apiId", apiId))
                .uniqueResult();
    }
}
