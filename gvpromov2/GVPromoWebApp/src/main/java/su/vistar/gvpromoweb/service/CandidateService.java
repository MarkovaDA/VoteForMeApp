package su.vistar.gvpromoweb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.gvpromoweb.persistence.dao.CandidateDao;
import su.vistar.gvpromoweb.persistence.entity.CandidateEntity;


@Service("candidateService")
public class CandidateService {
    @Autowired
    private CandidateDao candidateDao;
    
    
    @Transactional
    public CandidateEntity getActive() {
        return candidateDao.getActive();
    }
    
    @Transactional
    public CandidateEntity getCandidateById(Integer id){
        return candidateDao.getCandidateById(id);
    }
    
    @Transactional
    public CandidateEntity getCandidateByVkId(String vkId){
        return candidateDao.getCandidateByVkId(vkId);
    }
   
    
    @Transactional
    public List<CandidateEntity> getAllCandidates(){
        return candidateDao.getCandidates();
    }
    
    @Transactional
    public void saveOrUpdate(CandidateEntity entity){
        candidateDao.save(entity);    
    }
    @Transactional
    public void delete(CandidateEntity entity){
        candidateDao.delete(entity);
    }
    
}