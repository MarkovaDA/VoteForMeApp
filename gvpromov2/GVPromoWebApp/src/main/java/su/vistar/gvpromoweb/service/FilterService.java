package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.FilterDao;
import su.vistar.gvpromoweb.persistence.entity.FilterEntity;

@Service("filterService")
public class FilterService {
    
    @Autowired
    private FilterDao filterDao;
    
    @Transactional
    public FilterEntity get(Integer candidateId){
        return filterDao.get(candidateId);        
    }
    
    @Transactional
    public void saveOrUpdate(FilterEntity filter){
        filterDao.save(filter);
    }
}
