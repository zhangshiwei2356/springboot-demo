package com.demo.system.service;

import com.demo.system.dto.ProductPriceQueryDTO;
import com.demo.system.vo.ProductPriceVO;

public interface ProductReadService {

    ProductPriceVO getPrice(ProductPriceQueryDTO dto);
}
