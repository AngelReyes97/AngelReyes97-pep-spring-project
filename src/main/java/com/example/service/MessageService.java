package com.example.service;
import java.util.List;

import javax.transaction.Transactional;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message findPostedBy(Integer posted_by){
        return messageRepository.findPostedBy(posted_by);
    }

    public Message addMessage(Message newMessage){
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        return messageRepository.getMessageById(id);
    }

    public int deleteMessageById(Integer id){
       messageRepository.deleteById(id);
        return 1;
    }

    public int updateMessage(Message message, String newMessage){
        message.setMessage_text(newMessage);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccount(Integer id){
        return messageRepository.getMessagesByAccount(id);
    }

}
