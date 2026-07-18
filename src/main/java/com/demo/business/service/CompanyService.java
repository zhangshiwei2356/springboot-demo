package com.demo.business.service;

import com.demo.business.dto.CompanySaveDTO;
import com.demo.business.vo.CompanyVO;

import java.util.List;

public interface CompanyService {

    List<CompanyVO> listAll();

    CompanyVO getById(Long id);

    CompanyVO create(CompanySaveDTO dto);

    CompanyVO update(Long id, CompanySaveDTO dto);

    void remove(Long id);

    List<CompanyVO> reset();
}
