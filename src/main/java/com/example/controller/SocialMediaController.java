package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerUser(@RequestBody Account account){

        if(account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(account);
        }

        Account existingAccount = accountService.findByUserName(account.getUsername());
       
        if(existingAccount != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingAccount);
        }

        Account newAccount = accountService.addAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }


    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> userlogin(@RequestBody Account account){

        Account existingAccount = accountService.findByUserName(account.getUsername());

       if(existingAccount == null || !(existingAccount.getPassword().equals(account.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account);
       }
       return ResponseEntity.status(HttpStatus.OK).body(existingAccount);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> newMessage(@RequestBody Message message){
        Message existingAccount = messageService.findPostedBy(message.getPosted_by());

        if(!(message.getMessage_text().isBlank()) && (message.getMessage_text().length() <= 255) && (existingAccount != null))
        {
            Message newMessage = messageService.addMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(newMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages()
    {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer id){

        Message messageId = messageService.getMessageById(id);
        
        if (messageId == null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageId);
    }

    @DeleteMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") Integer id){
      Message messageExists = messageService.getMessageById(id);
      if (messageExists != null) {
        int rowsUpdated = messageService.deleteMessageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
      }
      else{
        return ResponseEntity.status(HttpStatus.OK).build();
      }
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessageById(@RequestBody Message updatedMessage, @PathVariable("message_id") Integer id){

        Message messageExists = messageService.getMessageById(id);

        if(messageExists != null) {
            if(!(updatedMessage.getMessage_text().isBlank()) && (updatedMessage.getMessage_text().length() <= 255)){
               int rowsupdated = messageService.updateMessage(messageExists, updatedMessage.getMessage_text());
               return ResponseEntity.status(HttpStatus.OK).body(rowsupdated);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable("account_id") Integer accountId){
        
        List <Message> messages = messageService.getMessagesByAccount(accountId);
        if(messages != null) {
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
