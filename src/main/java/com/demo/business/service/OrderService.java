package com.demo.business.service;

import com.demo.business.dto.OrderQueryDTO;
import com.demo.business.dto.SubmitOrderDTO;
import com.demo.business.vo.OrderVO;

/**
 * 订单服务。
 */
public interface OrderService {

    OrderVO submitOrder(SubmitOrderDTO dto);
}
