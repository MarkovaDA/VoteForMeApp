package su.vistar.gvpromoweb.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;
import su.vistar.gvpromoweb.persistence.entity.security.AppuserRoleEntity;
import java.util.List;

@Repository(value = "userDao")
public class UserDao extends AbstractDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public UserEntity get(String vkId) {
        return (UserEntity)sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .add(Restrictions.eq("vkId", vkId))
                .uniqueResult();
    }
    
    

    public UserEntity get(Integer id) {
        return (UserEntity)sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }


    public List<UserEntity> getWithoutRoles() {
        DetachedCriteria criteria1 = DetachedCriteria.forClass(AppuserRoleEntity.class)
                .setProjection(Property.forName("user.id"));

        return (List<UserEntity>)sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .add(Restrictions.not(Property.forName("id").in(criteria1))).list();
    }
        
    public List<UserEntity> getUsersWithoutRole(Long roleID) {

        DetachedCriteria criteria1 = DetachedCriteria.forClass(AppuserRoleEntity.class)
                .add((Restrictions.eq("role.id", roleID)))
                .setProjection(Projections.distinct(Projections.property("user.id")));

        return (List<UserEntity>) sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Property.forName("id").notIn(criteria1))
                .add(Property.forName("deleted").eq(false))
                .add(Property.forName("enabled").eq(true))
                .list();
    }
    


    public List<UserEntity> getUsersWithRole(Long roleID) {

        DetachedCriteria criteria1 = DetachedCriteria.forClass(AppuserRoleEntity.class)
                .add((Restrictions.eq("role.id", roleID)))
                // .setProjection(Property.forName("appuser_id"))
                .setProjection(Projections.distinct(Projections.property("user.id")));

        return (List<UserEntity>) sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Property.forName("id").in(criteria1))
                .list();

    }    
    public List<UserEntity> listUsers() {
        return sessionFactory.getCurrentSession()
                .createCriteria(UserEntity.class)
                .addOrder(Order.desc("createDate"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
    
    public void updateRemove(Long userId,boolean isRemoved) {
        final String hql = "update UserEntity set deleted=:isRemoved where id=:userId";
        sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setBoolean("isRemoved", isRemoved)
                .setLong("userId", userId)
                .executeUpdate();
    }

    public void updateBlock(Long userId, boolean block) {
        final String hql = "update UserEntity set enabled=:block where id=:userId";
        sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("block", block)
                .setLong("userId", userId)
                .executeUpdate();
    }
   

}
