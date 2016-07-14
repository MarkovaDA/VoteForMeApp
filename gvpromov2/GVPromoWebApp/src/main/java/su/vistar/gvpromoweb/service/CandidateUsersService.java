package su.vistar.gvpromoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.CandidateUsersDao;
import su.vistar.gvpromoweb.persistence.entity.CandidateUsers;

@Service("candidateUsersService")
public class CandidateUsersService {
    
    @Autowired
    private CandidateUsersDao candidateUsersDao;
    
    @Transactional
    public CandidateUsers getCandidateForVkUser(String vkUserId) {
        return candidateUsersDao.getCandidateForVkUser(vkUserId);
    }
    
}
