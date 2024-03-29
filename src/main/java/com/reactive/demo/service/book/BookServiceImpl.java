package com.reactive.demo.service.book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reactive.demo.repository.AuthorRepository;
import com.reactive.demo.repository.BookRepository;
import com.reactive.demo.servicedto.request.AddBookRequest;
import com.reactive.demo.servicedto.request.UpdateBookRequest;
import com.reactive.demo.servicedto.response.BookResponse;
import com.reactive.demo.entity.Author;
import com.reactive.demo.entity.Book;

import rx.Completable;
import rx.Single;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Single<String> addBook(AddBookRequest addBookRequest) {
		System.out.println(addBookRequest);
		return saveBookToRepository(addBookRequest);
	}

	private Single<String> saveBookToRepository(AddBookRequest addBookRequest) {
		System.out.println("saveBookToRepository called ");
		return Single.create(singleSubscriber -> {
			Optional<Author> optionalAuthor = authorRepository.findById(addBookRequest.getAuthorId());
			if (!optionalAuthor.isPresent())
				singleSubscriber.onError(new EntityNotFoundException());
			else {
				String addedBookId = bookRepository.save(toBook(addBookRequest)).getId();
				singleSubscriber.onSuccess(addedBookId);
			}
		});
	}

	private Book toBook(AddBookRequest addBookRequest) {
		Book book = new Book();
		BeanUtils.copyProperties(addBookRequest, book);
		book.setId(UUID.randomUUID().toString());
		book.setAuthor(Author.builder().id(addBookRequest.getAuthorId()).build());
		System.out.println(book);
		return book;
	}

	@Override
	public Completable updateBook(UpdateBookRequest updateBookRequest) {
		return updateBookToRepository(updateBookRequest);
	}

	private Completable updateBookToRepository(UpdateBookRequest updateBookRequest) {
		return Completable.create(completableSubscriber -> {
			Optional<Book> optionalBook = bookRepository.findById(updateBookRequest.getId());
			if (!optionalBook.isPresent())
				completableSubscriber.onError(new EntityNotFoundException());
			else {
				Book book = optionalBook.get();
				book.setTitle(updateBookRequest.getTitle());
				bookRepository.save(book);
				completableSubscriber.onCompleted();
			}
		});
	}

	@Override
	public Single<List<BookResponse>> getAllBooks(int limit, int page) {
		return findAllBooksInRepository(limit, page).map(this::toBookResponseList);
	}

	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}
	
	
	private Single<List<Book>> findAllBooksInRepository(int limit, int page) {
		return Single.create(singleSubscriber -> {
			List<Book> books = bookRepository.findAll(PageRequest.of(page, limit)).getContent();
			singleSubscriber.onSuccess(books);
		});
	}

	private List<BookResponse> toBookResponseList(List<Book> bookList) {
		return bookList.stream().map(this::toBookResponse).collect(Collectors.toList());
	}

	private BookResponse toBookResponse(Book book) {
		BookResponse bookResponse = new BookResponse();
		BeanUtils.copyProperties(book, bookResponse);
		bookResponse.setAuthorName(book.getAuthor().getName());
		return bookResponse;
	}

	@Override
	public Single<BookResponse> getBookDetail(String id) {
		return findBookDetailInRepository(id);
	}

	private Single<BookResponse> findBookDetailInRepository(String id) {
		return Single.create(singleSubscriber -> {
			Optional<Book> optionalBook = bookRepository.findById(id);
			if (!optionalBook.isPresent())
				singleSubscriber.onError(new EntityNotFoundException());
			else {
				BookResponse bookResponse = toBookResponse(optionalBook.get());
				singleSubscriber.onSuccess(bookResponse);
			}
		});
	}

	@Override
	public Completable deleteBook(String id) {
		return deleteBookInRepository(id);
	}

	private Completable deleteBookInRepository(String id) {
		return Completable.create(completableSubscriber -> {
			Optional<Book> optionalBook = bookRepository.findById(id);
			if (!optionalBook.isPresent())
				completableSubscriber.onError(new EntityNotFoundException());
			else {
				bookRepository.delete(optionalBook.get());
				completableSubscriber.onCompleted();
			}
		});
	}

}
