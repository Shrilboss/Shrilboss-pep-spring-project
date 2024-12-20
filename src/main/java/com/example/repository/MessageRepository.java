package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

//Similar to MessageDAO
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findAll();
    Message findByMessageId(Integer messageId);
    List<Message> findByPostedBy(Integer accountId);
}
