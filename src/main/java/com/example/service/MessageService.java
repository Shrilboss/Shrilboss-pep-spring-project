package com.example.service;

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
}
