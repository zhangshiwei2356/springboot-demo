package com.demo.business.controller;

import com.demo.business.dto.SubmitOrderDTO;
import com.demo.business.service.OrderService;
import com.demo.business.vo.OrderVO;
import com.demo.common.base.BaseController;
import com.demo.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单")
public class OrderController extends BaseController<SubmitOrderDTO, OrderVO> {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submit")
    @Operation(summary = "提交订单")
    public Result<OrderVO> submit(@Valid @RequestBody SubmitOrderDTO dto) {
        return ok(orderService.submitOrder(dto));
    }
}
