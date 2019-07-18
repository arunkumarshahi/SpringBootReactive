package com.reactive.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reactive.demo.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, String> {

}
