package com.imooc.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 在线程1和线程2之间传递DeferredResult对象
 */
@Component
public class DeferredResultHolder {

    //map的key就是订单号，value就是每个订单的处理结果
    private Map<String, DeferredResult<String>> map = new HashMap<String, DeferredResult<String>>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
