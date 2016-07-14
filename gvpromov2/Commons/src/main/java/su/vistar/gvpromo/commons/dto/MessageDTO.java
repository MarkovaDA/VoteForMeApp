package su.vistar.gvpromo.commons.dto;


public class MessageDTO {
    private Integer messageId;    
    private String messageText; 
    private Integer appUserId;

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

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public String toString() {
        return "MessageDTO{" + "messageId=" + messageId + ", messageText=" + messageText + ", appUserId=" + appUserId + '}';
    }
}
