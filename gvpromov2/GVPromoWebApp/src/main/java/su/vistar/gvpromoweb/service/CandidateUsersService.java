package su.vistar.gvpromoweb.service;

import java.util.List;
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
    
    @Transactional
    public void saveOrUpdate(CandidateUsers entity){
        candidateUsersDao.save(entity);    
    }
    
    
    @Transactional
    public List<CandidateUsers> allSendors(Integer candidateId){
        return candidateUsersDao.allSendorsByCandidate(candidateId);
    }
    
    @Transactional
    public void delete(CandidateUsers entity){
        candidateUsersDao.delete(entity);
    }
    
}
