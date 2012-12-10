package Tools;

public class Message {
    private int msgId,msgType;
    
    public Message(int sender, int type){
        msgId = sender;
        msgType = type;
    }
    
    public Message(int sender, int type, String info){
        msgId = sender;
        msgType = type;
    } 

    public int getMsgId(){
        return msgId;
    }
    
    public int getMsgType(){
        return msgType;
    }   
    
}
