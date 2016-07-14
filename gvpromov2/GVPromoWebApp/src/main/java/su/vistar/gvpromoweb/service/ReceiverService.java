
package su.vistar.gvpromoweb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.ReceiverDao;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;

@Service("receiverService")
public class ReceiverService {
    @Autowired
    private ReceiverDao receiverDao;   
    
    @Transactional
    public void saveOrUpdate(ReceiverEntity entity){
        receiverDao.save(entity);
    }
    
    @Transactional
    public ReceiverEntity select(){
        return receiverDao.get(1);
    }
    
    @Transactional
    public List<ReceiverEntity> getWasNotSendReceiver(){
        return receiverDao.getWasNotSendReceiver();
    }
}
