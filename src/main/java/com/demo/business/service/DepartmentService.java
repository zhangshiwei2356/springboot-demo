package com.demo.business.service;

import com.demo.business.dto.DepartmentSaveDTO;
import com.demo.business.vo.DepartmentVO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentVO> listAll();

    DepartmentVO getById(Long id);

    DepartmentVO create(DepartmentSaveDTO dto);

    DepartmentVO update(Long id, DepartmentSaveDTO dto);

    void remove(Long id);

    List<DepartmentVO> reset();
}
