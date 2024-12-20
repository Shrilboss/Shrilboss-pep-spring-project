package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    public Message createMessage(Message message) {
        String messageText = message.getMessageText();
        Integer postedBy = message.getPostedBy();

        // Check if messageText is blank or over 255 characters
        if (messageText == null || messageText.length()==0 || messageText.length()>255) {
            throw new IllegalArgumentException("MessageText is invalid");
        }

        // Check if posteBy refers to a real user
        if (postedBy == null || accountRepository.findByAccountId(postedBy)==null) {
            throw new IllegalArgumentException("PosteBy doesn't refer to a real user");
        }

        // Save the account to the database
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    public int deleteMessageById(Integer messageId) {
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessage(Integer messageId, String newMessageText) {
        if(newMessageText == null || newMessageText.length()==0 || newMessageText.length()>255){
            throw new IllegalArgumentException("MessageText is invalid");
        }

        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message updatedmessage = existingMessage.get();
            updatedmessage.setMessageText(newMessageText);
            messageRepository.save(updatedmessage);
            return 1;
        }else{
            throw new IllegalArgumentException("MessageId doesn't exist");
        }
    }

    public List<Message> getMessageByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
