package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){ this.messageRepository = messageRepository; }

    public Message persistMessage(Message message){ return messageRepository.save(message); }

    public List<Message> getAllMessages(){  return messageRepository.findAll(); }

    public List<Message> getAllMessagesFromUser(int posted_by){
        Optional<List<Message>> messages = messageRepository.getAllById(posted_by);
        if(messages.isPresent()) return messages.get(); else return null;
    }

    public Message getMessage(int id){
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent()) return message.get(); else return null;
    }

    public int deleteMessage(int id){
        if (messageRepository.existsById(id)) {  
            messageRepository.deleteById(id);
            return 1;  
        }else return 0;
    }

    public int updateMessage(int id, String newText){
        Message message = messageRepository.getById(id);
        if(messageRepository.existsById(id)){
            message.setMessageText(newText);
            messageRepository.save(message);
            return 1;
        }else return 0;
    }
}
