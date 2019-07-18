package com.reactive.demo.service.author;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.demo.entity.Author;
import com.reactive.demo.repository.AuthorRepository;
import com.reactive.demo.servicedto.request.AddAuthorRequest;

import rx.Single;

import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Single<String> addAuthor(AddAuthorRequest addAuthorRequest) {
        return addAuthorToRepository(addAuthorRequest);
    }

    private Single<String> addAuthorToRepository(AddAuthorRequest addAuthorRequest) {
        return Single.create(singleSubscriber -> {
            String addedAuthorId = authorRepository.save(toAuthor(addAuthorRequest)).getId();
            singleSubscriber.onSuccess(addedAuthorId);
        });
    }

    private Author toAuthor(AddAuthorRequest addAuthorRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(addAuthorRequest, author);
        author.setId(UUID.randomUUID().toString());
        System.out.println(author);
        return author;
    }
}