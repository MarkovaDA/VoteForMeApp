package su.vistar.gvpromo.commons.dto;


public class MessageDTO {
    private Integer messageId;    
    private String messageText; 
    

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
        return "MessageDTO{" + "messageId=" + messageId + ", messageText=" + messageText +  '}';
    }
}
