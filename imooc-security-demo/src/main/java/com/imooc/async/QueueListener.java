package com.imooc.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 模拟线程2对于消息队列的监听，即查看completeOrder是否有值
 * ContextRefreshedEvent:整个spring容器初始化完毕的一个事件
 */

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Autowired
    private MockQueue mockQueue;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //因为一直在循环，需要单开一个线程，不然启动的时候会阻塞
        new Thread(() -> {
            while(true) {
                if(StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderNumber = mockQueue.getCompleteOrder();
                    logger.info("返回订单处理结果："+orderNumber);
                    //整个异步处理完成，设置返回给浏览器的信息
                    deferredResultHolder.getMap().get(orderNumber).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
