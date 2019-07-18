package com.reactive.demo.service.book;

import java.util.List;

import com.reactive.demo.entity.Book;
import com.reactive.demo.servicedto.request.AddBookRequest;
import com.reactive.demo.servicedto.request.UpdateBookRequest;
import com.reactive.demo.servicedto.response.*;

import rx.Completable;
import rx.Single;

public interface BookService {
	Single<String> addBook(AddBookRequest addBookRequest);

	Completable updateBook(UpdateBookRequest updateBookRequest);

	Single<List<BookResponse>> getAllBooks(int limit, int page);

	Single<BookResponse> getBookDetail(String id);

	Completable deleteBook(String id);

	List<Book> getBooks();
}