package com.reactive.demo.web;

import java.net.URI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.demo.service.author.AuthorService;
import com.reactive.demo.servicedto.request.AddAuthorRequest;
import com.reactive.demo.webdto.request.AddAuthorWebRequest;
import com.reactive.demo.webdto.response.BaseWebResponse;

import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/api/authors")
public class AuthorRestController {
	@Autowired
    private AuthorService authorService;
	 @PostMapping(
	            consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )
	
	 public Single<ResponseEntity<BaseWebResponse>> addAuthor(@RequestBody AddAuthorWebRequest addAuthorWebRequest) {
	       System.out.println(addAuthorWebRequest);
		 return authorService.addAuthor(toAddAuthorRequest(addAuthorWebRequest))
	                .subscribeOn(Schedulers.io())
	                .map(s -> ResponseEntity
	                        .created(URI.create("/api/authors/" + s))
	                        .body(BaseWebResponse.successNoData()));
	    }
	 private AddAuthorRequest toAddAuthorRequest(AddAuthorWebRequest addAuthorWebRequest) {
	        AddAuthorRequest addAuthorRequest = new AddAuthorRequest();
	        BeanUtils.copyProperties(addAuthorWebRequest, addAuthorRequest);
	        System.out.println(addAuthorRequest);
	        return addAuthorRequest;
	    }
	

}
