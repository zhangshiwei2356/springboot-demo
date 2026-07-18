package com.demo.business.integration;

import com.demo.business.integration.dto.ProductRemoteVO;
import com.demo.common.domain.Result;

/**
 * 系统商品询价（单机模式下为进程内调用）。
 */
public interface SystemProductClient {

    Result<ProductRemoteVO> getPrice(String code);
}
