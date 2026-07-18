package com.demo.business.service;

import com.demo.business.dto.AdminOrderSaveDTO;
import com.demo.business.vo.AdminOrderVO;

import java.util.List;

public interface AdminOrderService {

    List<AdminOrderVO> listAll();

    AdminOrderVO getById(Long id);

    AdminOrderVO create(AdminOrderSaveDTO dto);

    AdminOrderVO update(Long id, AdminOrderSaveDTO dto);

    void remove(Long id);

    List<AdminOrderVO> reset();
}
