package su.vistar.gvpromoweb.persistence.dao;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.ReceiverEntity;

@Repository(value = "receiverDao")
public class ReceiverDao extends AbstractDao {

    //пользователь по id
    public ReceiverEntity get(Integer Id) {
        return (ReceiverEntity) sessionFactory.getCurrentSession()
                .createCriteria(ReceiverEntity.class)
                .add(Restrictions.eq("id", Id))
                .uniqueResult();
    }

    //получаем пользователя, которому еще не отпарвляли сообщение
    public List<ReceiverEntity> getWasNotSendReceiver() {
        return   sessionFactory.getCurrentSession()
                .createCriteria(ReceiverEntity.class)
                .setMaxResults(1)  //limit
                .setFirstResult(0) //offset              
                .add(Restrictions.eq("status", false)).
                list()
                ;
                
    }   
}
