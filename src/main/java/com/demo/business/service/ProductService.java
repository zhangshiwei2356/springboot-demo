package com.demo.business.service;

import com.demo.business.dto.ProductSaveDTO;
import com.demo.business.vo.ProductVO;

import java.util.List;

public interface ProductService {

    List<ProductVO> listAll();

    ProductVO getById(Long id);

    ProductVO create(ProductSaveDTO dto);

    ProductVO update(Long id, ProductSaveDTO dto);

    void remove(Long id);

    List<ProductVO> reset();
}
