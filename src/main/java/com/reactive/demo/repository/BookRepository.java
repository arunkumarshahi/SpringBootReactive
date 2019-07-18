package com.reactive.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reactive.demo.entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	 List<Book> findAllByAuthorId(String authorId);
}
