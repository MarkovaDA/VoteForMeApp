
package su.vistar.gvpromoweb.persistence.dao;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.MessageEntity;


@Repository(value = "messageDao")
public class MessageDao extends AbstractDao{
    
    /*извлечение сообщения по id*/
    public MessageEntity get(Integer messageId) {
        return (MessageEntity)sessionFactory.getCurrentSession()
                .createCriteria(MessageEntity.class)
                .add(Restrictions.eq("messageId", messageId))
                .uniqueResult();
    }
    
    /*извлечение списка сообщений одного кандидата
    *этот метод будет выполняться призагрузке приложения
    */
    public List<MessageEntity> listMessages(Integer appUserId) {
        return  sessionFactory.getCurrentSession()
                .createCriteria(MessageEntity.class)
                .add(Restrictions.eq("appUserId", appUserId))
                .list();
    }
    
    
}
