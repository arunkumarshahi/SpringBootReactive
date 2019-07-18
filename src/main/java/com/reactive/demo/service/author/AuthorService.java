package com.reactive.demo.service.author;


import com.reactive.demo.servicedto.request.AddAuthorRequest;

import rx.Single;

public interface AuthorService {
	Single<String> addAuthor(AddAuthorRequest addAuthorRequest);

}
