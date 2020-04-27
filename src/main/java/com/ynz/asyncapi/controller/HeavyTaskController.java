package com.ynz.asyncapi.controller;


import com.ynz.asyncapi.dto.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.Callable;

@RestController
@Slf4j
public class HeavyTaskController {
    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    @PostMapping("/bigTask/{id}")
    public Callable<String> postBigTask(@PathVariable("id") int id, HttpServletRequest request) {
        log.info("....in the /bigTask thread from the servlet container: " + Thread.currentThread().getName());
        log.info(" is async enabled? " + request.isAsyncSupported());

        return new HeavyTask(id);
    }

    @GetMapping("/search")
    public DeferredResult<SearchResult> searchDeferredResult() {
        log.info("in the /search handler");
        log.info("http request handler thread: " + Thread.currentThread().getName());

        DeferredResult<SearchResult> searchResultDeferredResult = new DeferredResult<>();

        asyncTaskExecutor.submit(() -> {
                    log.info("start to search");
                    log.info("thread pool task thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("search end.");
                    SearchResult searchResult = new SearchResult();
                    searchResult.setName("YYY");
                    searchResult.setBirthDate(LocalDateTime.of(1990, Month.AUGUST, 22, 10, 10));

                    searchResultDeferredResult.setResult(searchResult);
                }
        );
        return searchResultDeferredResult;
    }


    //must be a callable
    private class HeavyTask implements Callable<String> {
        private int taskSeq;

        public HeavyTask(int taskSeq) {
            this.taskSeq = taskSeq;
        }

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
