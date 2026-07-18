package com.demo.business.service;

import com.demo.business.dto.VehiclePurchaseDTO;
import com.demo.business.vo.VehiclePurchaseResultVO;

public interface VehiclePurchaseService {

    VehiclePurchaseResultVO purchase(Long vehicleId, VehiclePurchaseDTO dto);
}
