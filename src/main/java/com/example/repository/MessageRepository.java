package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query("FROM Message p WHERE p.posted_by = :posted_by")
    Message findPostedBy(@Param("posted_by") Integer posted_by);

    @Query("FROM Message p WHERE p.message_id = :message_id")
    Message getMessageById(@Param("message_id") Integer message_id);

    @Query("FROM Message p WHERE p.posted_by = :posted_by")
    List<Message> getMessagesByAccount(@Param("posted_by") Integer posted_by);
}
