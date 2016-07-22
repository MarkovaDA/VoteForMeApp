package su.vistar.gvpromoweb.persistence.dao;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.CityEntity;


/**
 *
 * @author darya
 */
@Repository(value = "cityDao")
public class CityDao extends AbstractDao{
    
    public CityEntity getCityByName(String name) {
        return (CityEntity)sessionFactory.getCurrentSession()
                .createCriteria(CityEntity.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }
}
