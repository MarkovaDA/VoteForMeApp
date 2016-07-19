package su.vistar.gvpromoweb.persistence.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class MessageEntity implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;
    
    @Column(name = "message_text", columnDefinition="TEXT")
    private String messageText; 
    
    //@Column(name = "appuser_id", insertable=false, updatable=false)
    //private Integer appUserId; //на самом деле привязка к id в таблице candidates
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate; //кандидат, автор данного сообщения

   
    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    /*public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }*/

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "MessageEntity{" + "messageId=" + messageId + ", messageText=" + messageText + '}';
    }
    
    
}
