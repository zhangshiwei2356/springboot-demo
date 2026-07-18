package com.demo.business.service;

import com.demo.business.vo.VehicleVO;

import java.util.List;

public interface VehicleService {

    List<VehicleVO> listOnSale(String keyword);

    VehicleVO getById(Long id);
}
