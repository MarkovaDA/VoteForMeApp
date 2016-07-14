package su.vistar.gvpromoweb.persistence.dao;

import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.HistoryEntity;
import org.hibernate.criterion.Restrictions;
import java.util.Calendar;

/**
 *
 * @author darya
 */
@Repository(value = "historyDao")
public class HistoryDao extends AbstractDao{

    public HistoryEntity selectByIdAndDate(String vkUserId){
        
        return (HistoryEntity)sessionFactory.getCurrentSession()
                .createCriteria(HistoryEntity.class)
                .add(Restrictions.eq("vkUserId", vkUserId))
                .add(Restrictions.eq("lastDate", sqlNowDate()))
                .uniqueResult();
    }
    public HistoryEntity selectByVkId(String vkUserId){
        return  (HistoryEntity)sessionFactory.getCurrentSession()
                .createCriteria(HistoryEntity.class)
                .add(Restrictions.eq("vkUserId", vkUserId))
                .uniqueResult();
    }
    public void saveOrUpdate(HistoryEntity entity){
        //обновляем текущую дату
        entity.setLastDate(sqlNowDate()); //обновляем дату
        entity.setCountQuery(entity.getCountQuery() + 1); //увеличиваем число запросов
        save(entity);
    }
    public void simpleSave(HistoryEntity entity){
        save(entity);
    }
    /*текущая дата в формате sql-даты*/
    private java.sql.Date sqlNowDate(){
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        return date;
    }
    
}
