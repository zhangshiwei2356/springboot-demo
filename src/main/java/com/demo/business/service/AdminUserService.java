package com.demo.business.service;

import com.demo.business.dto.AdminUserSaveDTO;
import com.demo.business.vo.AdminUserVO;

import java.util.List;

public interface AdminUserService {

    List<AdminUserVO> listAll();

    AdminUserVO getById(Long id);

    AdminUserVO create(AdminUserSaveDTO dto);

    AdminUserVO update(Long id, AdminUserSaveDTO dto);

    void remove(Long id);

    List<AdminUserVO> reset();
}
