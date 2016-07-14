package su.vistar.gvpromoweb.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AbstractDao {

    @Qualifier("sessionFactory")
    @Autowired
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> void save(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }
}