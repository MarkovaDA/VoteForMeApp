
package su.vistar.gvpromoweb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.MessageDao;
import su.vistar.gvpromoweb.persistence.entity.MessageEntity;


@Service("messageService")
public class MessageService {
    
    @Autowired
    private MessageDao messageDao;
    
    @Transactional(readOnly = true)
    public MessageEntity get(Integer message_id) {
        return messageDao.get(message_id);
    }
    
    @Transactional
    public List<MessageEntity> listMessages(Integer appUserId){
        return messageDao.listMessages(appUserId);
    }
}
