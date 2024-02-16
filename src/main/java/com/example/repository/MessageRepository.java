package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query(value = "SELECT * FROM Message WHERE posted_by = :postedBy", nativeQuery = true)
    List<Message> findMessagesByPostedBy(@Param("postedBy") int postedBy);
}
