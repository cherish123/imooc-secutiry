package com.imooc.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Autowired
    private MockQueue mockQueue;

    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {

        logger.info("主线程开始");

        //随机生成8位的订单号
        String oderNumber = RandomStringUtils.randomNumeric(8);
        //模拟放进消息队列
        mockQueue.setPlaceOrder(oderNumber);
        //订单处理结果
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(oderNumber,result);

//        Callable<String> result = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                logger.info("副线程开始");
//                Thread.sleep(1000);
//                logger.info("副线程返回");
//                return "success";
//            }
//        };
        logger.info("主线程返回");
        return result;
    }
}
