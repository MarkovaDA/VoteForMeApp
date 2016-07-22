
package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.AreaDao;
import su.vistar.gvpromoweb.persistence.dao.CityDao;
import su.vistar.gvpromoweb.persistence.dao.RegionDao;
import su.vistar.gvpromoweb.persistence.entity.AreaEntity;
import su.vistar.gvpromoweb.persistence.entity.CityEntity;

/**
 *
 * @author darya
 */
@Service("areaService")
public class AreaService {
    @Autowired
    private AreaDao areaDao;
    
    @Transactional
    public void saveOrUpdate(AreaEntity area) {
       areaDao.save(area);      
    }
    
    @Transactional
    public AreaEntity getAreaByApiId(Integer api_id){
        return areaDao.getAreaByApiId(api_id);
    }
}
