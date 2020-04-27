package com.ynz.asyncapi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

@RestController
@Slf4j
public class HeavyTaskController {

    @PostMapping("/bigTask/{id}")
    public Callable<String> postBigTask(@PathVariable("id") int id, HttpServletRequest request) {
        log.info("....in the /bigTask thread from the servlet container: "+ Thread.currentThread().getName());
        log.info(" is async enabled? "+ request.isAsyncSupported());

        return new HeavyTask(id);
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
