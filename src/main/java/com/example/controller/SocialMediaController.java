package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;

import java.util.List;

import javax.naming.AuthenticationException;

@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
//Account

    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> createAccount(@RequestBody Account account) {
        if(accountService.usernamePresent(account.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(account);
        }
        accountService.persistAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account acc = accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(acc == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(acc);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
//Message

    @RequestMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        Message message = messageService.getMessageById(message_id);
        if(message != null){
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @GetMapping("accounts/{posted_by}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int posted_by){
        List <Message> messages = messageService.getAllMessagesByAccountId(posted_by);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if(accountService.getAccountById(message.getPosted_by()) == null || message.getMessage_text().equals("") || message.getMessage_text().length() > 255){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        messageService.persistMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PatchMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody Message message){
        if(messageService.getMessageById(message_id) == null || message.getMessage_text().equals("") || message.getMessage_text().length() > 255){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        messageService.updateMessage(message_id, message);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id){
        Message deletedMessage = messageService.getMessageById(message_id);
        if (deletedMessage != null) {
            messageService.deleteMessage(message_id);
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }


}
