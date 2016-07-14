package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.HistoryDao;
import su.vistar.gvpromoweb.persistence.entity.HistoryEntity;


/**
 *
 * @author darya
 */
@Service("historyService")
public class HistoryService {
    @Autowired
    private HistoryDao historyDao; 
        
    @Transactional
    public HistoryEntity selectByIdAndDate(String vkUserId){
       return historyDao.selectByIdAndDate(vkUserId);
    }
    
    @Transactional
    public HistoryEntity selectByVkId(String vkUserId){
        return historyDao.selectByVkId(vkUserId);
    }
    
    @Transactional
    public void saveOrUpdate(HistoryEntity entity){
        historyDao.saveOrUpdate(entity);
    }
    
    @Transactional
    public void simpleSave(HistoryEntity entity){
        historyDao.simpleSave(entity);
    }
   
}
