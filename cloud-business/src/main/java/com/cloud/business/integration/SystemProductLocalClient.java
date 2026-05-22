package com.cloud.business.integration;

import com.cloud.business.integration.dto.ProductRemoteVO;
import com.cloud.common.domain.Result;
import com.cloud.system.dto.ProductPriceQueryDTO;
import com.cloud.system.service.ProductReadService;
import com.cloud.system.vo.ProductPriceVO;
import org.springframework.stereotype.Component;

@Component
public class SystemProductLocalClient implements SystemProductClient {

    private final ProductReadService productReadService;

    public SystemProductLocalClient(ProductReadService productReadService) {
        this.productReadService = productReadService;
    }

    @Override
    public Result<ProductRemoteVO> getPrice(String code) {
        ProductPriceVO price = productReadService.getPrice(new ProductPriceQueryDTO(code));
        ProductRemoteVO remote = new ProductRemoteVO();
        remote.setProductCode(price.getProductCode());
        remote.setUnitPrice(price.getUnitPrice());
        return Result.ok(remote);
    }
}
