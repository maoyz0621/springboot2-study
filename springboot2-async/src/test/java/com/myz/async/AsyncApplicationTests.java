package com.myz.async;

import com.myz.async.task.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsyncApplication.class)
public class AsyncApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(AsyncApplicationTests.class);

    @Autowired
    @Lazy
    private AsyncTask asyncTask;

    @Test
    public void contextLoads() throws Exception {
        log.debug("开始....");
        asyncTask.asyncInvokeSimple();
        log.debug("进行中....");
        asyncTask.asyncInvokeWithParameter("myz");
        Future<String> future = asyncTask.asyncInvokeReturnFuture(2);
        while (true) {
            if (future.isDone()) {
                log.info(future.get());
                break;
            }
        }
        log.debug("结束....");
    }

}

