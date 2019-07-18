package com.reactive.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

import static junit.framework.Assert.assertTrue;

public class RxJavaUnitTest {
	Logger log = LoggerFactory.getLogger(RxJavaUnitTest.class);
	 String result="";

	    // Simple subscription to a fix value
	    @Test
	    public void returnAValue(){
	        result = "";
	        Observable<String> observer = Observable.just("Hello"); // provides datea
	        observer.subscribe(s -> result=s); // Callable as subscriber
	        log.info("...."+result.equals("Hello"));
	    }
	}