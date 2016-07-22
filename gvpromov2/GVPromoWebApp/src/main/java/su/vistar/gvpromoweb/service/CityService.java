
package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.CityDao;
import su.vistar.gvpromoweb.persistence.dao.RegionDao;
import su.vistar.gvpromoweb.persistence.entity.CityEntity;

/**
 *
 * @author darya
 */
@Service("cityService")
public class CityService {
    @Autowired
    private CityDao cityDao;
    
    @Transactional
    public void saveOrUpdate(CityEntity city) {
       cityDao.save(city);      
    }
    
    @Transactional
    public CityEntity getCityByName(String name){
        return cityDao.getCityByName(name);
    }
}
