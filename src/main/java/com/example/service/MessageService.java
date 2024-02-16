package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message persistMessage(Message message){
        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByAccountId(int posted_by){
        List<Message> messages = messageRepository.findMessagesByPostedBy(posted_by);
        return messages;
    }

    public Message getMessageById(int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }

    public Message updateMessage(int id, Message replacement){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setMessage_text(replacement.getMessage_text());
            messageRepository.save(message);
            return message;
        }
        else{
            return null;
        }
    }
    
    public void deleteMessage(Integer message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            messageRepository.deleteById(message_id);
        }
    }

}
