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

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if(message.getMessageText().isBlank()) {
            return null;
        }

        if (message.getMessageText().length() > 255) {
            return null;
        }

        if(!accountRepository.findById(message.getPostedBy()).isPresent()) {
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> findMessageById(int id) {
        return messageRepository.findById(id);
    }

    public Integer deleteMessageById(int id) {

        Optional<Message> message = messageRepository.findById(id);

        if(message.isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }

        return null;
    }

    public Integer updateMessageById(int id, Message message) {

        Optional<Message> existingMessage = messageRepository.findById(id);

        if(message.getMessageText().isBlank()) {
            return null;
        }

        if(message.getMessageText().length() > 255) {
            return null;
        }

        if(existingMessage.isPresent()) {
            Message updated = existingMessage.get();
            updated.setMessageText(message.getMessageText());
            return 1;
        }

        return null;
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
