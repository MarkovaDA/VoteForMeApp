
package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.RegionDao;
import su.vistar.gvpromoweb.persistence.entity.RegionEntity;

/**
 *
 * @author darya
 */
@Service("regionService")
public class RegionService {
    @Autowired
    private RegionDao regionDao;
    
    @Transactional
    public void saveOrUpdate(RegionEntity region) {
       regionDao.save(region);      
    }
    
    @Transactional
    public RegionEntity getRegionByApiId(Integer apiId){
        return regionDao.getRegionByApiId(apiId);
    }
    
}
