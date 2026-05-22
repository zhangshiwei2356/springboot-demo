package com.demo.business.service.impl;

import com.demo.business.common.enums.OrderStatusEnum;
import com.demo.business.converter.OrderConverter;
import com.demo.business.dto.OrderQueryDTO;
import com.demo.business.dto.SubmitOrderDTO;
import com.demo.business.entity.OrderEntity;
import com.demo.business.integration.dto.UserRemoteVO;
import com.demo.business.handler.OrderHandler;
import com.demo.business.persistence.OrderPersistence;
import com.demo.business.service.OrderAsyncNotifyService;
import com.demo.business.service.OrderService;
import com.demo.business.vo.OrderIntegrationResult;
import com.demo.business.vo.OrderVO;
import com.demo.common.base.BaseService;
import com.demo.common.constant.MdcKeys;
import com.demo.common.context.UserContext;
import com.demo.common.domain.PageResult;
import com.demo.common.exception.GlobalException;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 下单主流程：Service → Converter + Handler → 持久化（演示为内存）。
 */
@Service
public class OrderServiceImpl extends BaseService<OrderQueryDTO, OrderVO> implements OrderService {

    private final OrderHandler orderHandler;
    private final OrderConverter orderConverter;
    private final OrderPersistence orderPersistence;
    private final OrderAsyncNotifyService orderAsyncNotifyService;

    public OrderServiceImpl(OrderHandler orderHandler,
                            OrderConverter orderConverter,
                            OrderPersistence orderPersistence,
                            OrderAsyncNotifyService orderAsyncNotifyService) {
        this.orderHandler = orderHandler;
        this.orderConverter = orderConverter;
        this.orderPersistence = orderPersistence;
        this.orderAsyncNotifyService = orderAsyncNotifyService;
    }

    @Override
    public OrderVO submitOrder(SubmitOrderDTO dto) {
        Long ctxUid = UserContext.getUid();
        if (ctxUid == null) {
            throw new GlobalException("请先登录后再下单");
        }
        if (!ctxUid.equals(dto.getBuyerUserId())) {
            throw new GlobalException("登录用户与下单用户不一致");
        }

        OrderIntegrationResult remoteBundle = orderHandler.execute(dto);
        UserRemoteVO user = remoteBundle.getUser();
        if (user == null || !user.getUserId().equals(dto.getBuyerUserId())) {
            throw new GlobalException("用户信息校验失败");
        }

        java.math.BigDecimal total =
                orderConverter.calculateTotal(remoteBundle.getProduct(), dto.getQuantity());

        String orderNo = genOrderNo();
        OrderEntity entity = orderConverter.toNewEntity(orderNo, dto, remoteBundle.getProduct(),
                OrderStatusEnum.CREATED.getCode(), total);
        orderPersistence.insert(entity);

        String traceId = MDC.get(MdcKeys.TRACE_ID);
        orderAsyncNotifyService.notifyOrderCreated(orderNo, dto.getBuyerUserId(), traceId);
        return orderConverter.convert(entity);
    }

    private static String genOrderNo() {
        return "ORD" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }

    @Override
    public List<OrderVO> list(OrderQueryDTO dto) {
        return Collections.emptyList();
    }

    @Override
    public PageResult<OrderVO> page(OrderQueryDTO dto) {
        // 演示环境占位：生产应走 MyBatis-Plus 分页并补充 jsqlparser 依赖
        return new PageResult<>(dto.getPageNo(), dto.getPageSize(), 0, Collections.emptyList());
    }

    @Override
    public Boolean save(OrderQueryDTO dto) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(OrderQueryDTO dto) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Long id) {
        return id != null && orderPersistence.deleteById(id) > 0;
    }
}
