package com.demo.system.service.impl;

import com.demo.business.entity.VehicleEntity;
import com.demo.business.mapper.VehicleMapper;
import com.demo.common.base.BaseService;
import com.demo.common.domain.PageResult;
import com.demo.common.exception.GlobalException;
import com.demo.system.dto.ProductPriceQueryDTO;
import com.demo.system.service.ProductReadService;
import com.demo.system.vo.ProductPriceVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 演示商品价格表。
 */
@Service
public class ProductReadServiceImpl extends BaseService<ProductPriceQueryDTO, ProductPriceVO> implements ProductReadService {

    // price map
    private static final Map<String, BigDecimal> DEMO_PRICE = new HashMap<>();

    static {
        DEMO_PRICE.put("SKU-PHONE", new BigDecimal("3999.00"));
        DEMO_PRICE.put("SKU-BOOK", new BigDecimal("59.90"));
        DEMO_PRICE.put("SKU-DEMO", new BigDecimal("9.99"));
    }

    private final VehicleMapper vehicleMapper;

    public ProductReadServiceImpl(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public ProductPriceVO getPrice(ProductPriceQueryDTO dto) {
        if (dto == null || dto.getProductCode() == null) {
            throw new GlobalException("商品编码不能为空");
        }
        String code = dto.getProductCode().trim();
        BigDecimal price = DEMO_PRICE.get(code);
        if (price == null && code.toUpperCase(Locale.ROOT).startsWith("CAR-")) {
            price = lookupVehiclePrice(code);
        }
        if (price == null) {
            throw new GlobalException("商品不存在或未定价");
        }
        ProductPriceVO vo = new ProductPriceVO();
        vo.setProductCode(code);
        vo.setUnitPrice(price);
        return vo;
    }

    private BigDecimal lookupVehiclePrice(String code) {
        for (VehicleEntity vehicle : vehicleMapper.selectAll()) {
            if (vehicle.getCode() != null
                    && vehicle.getCode().equalsIgnoreCase(code)
                    && StringUtils.hasText(vehicle.getPrice())) {
                return new BigDecimal(vehicle.getPrice().trim());
            }
        }
        return null;
    }

    @Override
    public List<ProductPriceVO> list(ProductPriceQueryDTO dto) {
        return Collections.emptyList();
    }

    @Override
    public PageResult<ProductPriceVO> page(ProductPriceQueryDTO dto) {
        return new PageResult<>(1, 10, 0, Collections.emptyList());
    }

    @Override
    public Boolean save(ProductPriceQueryDTO dto) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(ProductPriceQueryDTO dto) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Long id) {
        return Boolean.FALSE;
    }
}
