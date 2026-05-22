package com.demo.business.service;

import com.demo.common.constant.MdcKeys;
import com.demo.common.context.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 订单异步后置任务演示：线程池内需手动绑定并清理上下文。
 */
@Service
public class OrderAsyncNotifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderAsyncNotifyService.class);

    @Async("asyncExecutor")
    public void notifyOrderCreated(String orderNo, Long buyerUserId, String traceId) {
        try {
            if (traceId != null) {
                MDC.put(MdcKeys.TRACE_ID, traceId);
            }
            if (buyerUserId != null) {
                UserContext.setUid(buyerUserId);
                MDC.put(MdcKeys.USER_ID, String.valueOf(buyerUserId));
            }
            LOGGER.info("async notify: order persisted orderNo={}", orderNo);
        } finally {
            UserContext.clear();
            MDC.remove(MdcKeys.USER_ID);
            MDC.remove(MdcKeys.TRACE_ID);
        }
    }
}
