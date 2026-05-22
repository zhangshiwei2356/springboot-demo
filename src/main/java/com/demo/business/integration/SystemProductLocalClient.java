package com.demo.business.integration;

import com.demo.business.integration.dto.ProductRemoteVO;
import com.demo.common.domain.Result;
import com.demo.system.dto.ProductPriceQueryDTO;
import com.demo.system.service.ProductReadService;
import com.demo.system.vo.ProductPriceVO;
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
