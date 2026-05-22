package com.cloud.business.integration;

import com.cloud.business.integration.dto.ProductRemoteVO;
import com.cloud.common.domain.Result;

/**
 * 系统商品询价（单机模式下为进程内调用）。
 */
public interface SystemProductClient {

    Result<ProductRemoteVO> getPrice(String code);
}
