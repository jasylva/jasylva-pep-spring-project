package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//import com.azul.crs.client.Response;
import com.example.entity.*;
import com.example.service.*;
import java.util.*;

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

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account){
        Account newAccount = accountService.persistAccount(account);
        if(account.getUsername() == null || account.getPassword().length() < 4){
            return ResponseEntity.status(400).build();
        }if (newAccount != null){
            return ResponseEntity.ok(newAccount);
        }else{
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account){
        Account loggedAccount = accountService.getAccount(account.getUsername(), account.getPassword());
        if(loggedAccount != null) return ResponseEntity.ok(loggedAccount); else return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
        return ResponseEntity.status(400).build();
    }

    if (accountService.getAccount(message.getPostedBy()) == null) {
        return ResponseEntity.status(400).build();
    }

    Message newMessage = messageService.persistMessage(message);
    return ResponseEntity.ok(newMessage);

    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(){  return ResponseEntity.ok(messageService.getAllMessages());  }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessage(@PathVariable Integer message_id){
        Message message = messageService.getMessage(message_id);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer message_id){
        int rowsAffected = messageService.deleteMessage(message_id);
        if (rowsAffected == 1) return ResponseEntity.ok(1); else return ResponseEntity.ok(null);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer message_id, @RequestBody Map<String, String> body){
        String newText = body.get("messageText"); 

    if (newText == null || newText.isBlank() || newText.length() > 255) {
        return ResponseEntity.status(400).build();
    }

    if (messageService.getMessage(message_id) == null) {
        return ResponseEntity.status(400).build();
    }

    return ResponseEntity.ok(messageService.updateMessage(message_id, newText));
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<?> getAllMessagesFromId(@PathVariable Integer account_id){
        return ResponseEntity.ok(messageService.getAllMessagesFromUser(account_id));
    }
}
