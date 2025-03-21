package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("SELECT a FROM Message a WHERE a.postedBy = :postedBy")
    Optional<List<Message>> getAllById(@Param("postedBy") Integer postedBy);
}
