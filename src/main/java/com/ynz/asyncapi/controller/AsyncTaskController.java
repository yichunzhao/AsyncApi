package com.ynz.asyncapi.controller;


import com.ynz.asyncapi.dto.SearchResult;
import com.ynz.asyncapi.entities.Person;
import com.ynz.asyncapi.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 * There are two ways to implement async http request processing in Spring boot framework.
 * 1) return a Callable, which is handled in the designated task thread pool.
 * 2) return a DeferredResult, where developer must manage the thread pool.
 * </p>
 * <p>
 * In Spring boot, by default request Async process has been enabled.
 * </p>
 */

@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncTaskController {
    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/bigTask")
    public Callable<String> postBigTask(HttpServletRequest request) {
        log.info("....in the /bigTask thread from the servlet container: " + Thread.currentThread().getName());
        log.info(" is async enabled? " + request.isAsyncSupported());

        return new HeavyTask();
    }

    @GetMapping("/findPerson/{name}")
    public Callable<List<Person>> findPersonByName(@PathVariable("name") String name) {
        return () -> {
            Thread.sleep(1000);//simulating a delay.
            return personRepository.findByName(name);
        };
    }

    @GetMapping("/search")
    public DeferredResult<SearchResult> searchDeferredResult() {
        log.info("in the /search handler");
        log.info("http request handler thread: " + Thread.currentThread().getName());

        DeferredResult<SearchResult> searchResultDeferredResult = new DeferredResult<>();

        asyncTaskExecutor.submit(() -> {
                    log.info("start to execute a task of searching");
                    log.info("with in the task thread pool, task thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                    log.info("search end.");

                    SearchResult searchResult = new SearchResult();
                    searchResult.setName("Somebody");
                    searchResult.setBirthDate(LocalDate.of(1990, Month.AUGUST, 22).toString());

                    searchResultDeferredResult.setResult(searchResult);
                }
        );

        return searchResultDeferredResult;
    }

    //must be a callable
    private class HeavyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("in the call() method.");
            log.info("Current thread from mvc task executor: " + Thread.currentThread().getName());


            //simulating a heavy task.
            log.info("start to do task");
            Thread.sleep(5000);
            log.info("task has been done");

            String result = Thread.currentThread().getName() + " result. ";

            return result;
        }

    }
}
