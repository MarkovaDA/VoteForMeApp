package su.vistar.gvpromoweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.UserDao;
import su.vistar.gvpromoweb.persistence.entity.UserEntity;



@Service("userService")
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public UserEntity get(String vkId) {
        return userDao.get(vkId);
    }

    @Transactional(readOnly = true)
    public UserEntity getById(Integer id) {
        return userDao.get(id);
    }
    @Transactional
    public void saveOrUpdate(UserEntity entity){
        userDao.save(entity);
    }

}
