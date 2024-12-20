package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Account account){
        try{
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(createdAccount);
        }
        catch(IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Account login_account){
        try {
            Account checkedAccount = accountService.login(login_account);
            return ResponseEntity.ok(checkedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("messages")
    public ResponseEntity<?> messages(@RequestBody Message message){
        try{
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessage(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId){
        Message retrievedMessage = messageService.getMessageById(messageId);
        return ResponseEntity.ok(retrievedMessage);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId){
        int rowsaffected = messageService.deleteMessageById(messageId);
        if(rowsaffected>0){
            return ResponseEntity.ok(rowsaffected);
        }
        else{
            return ResponseEntity.ok().build();
        }
    }

}
