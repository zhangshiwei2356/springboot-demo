package com.demo.business.converter;

import com.demo.business.dto.CompanySaveDTO;
import com.demo.business.entity.CompanyEntity;
import com.demo.business.vo.CompanyVO;
import com.demo.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter extends BaseConverter<CompanyEntity, CompanyVO> {

    @Override
    public CompanyVO convert(CompanyEntity source) {
        if (source == null) {
            return null;
        }
        CompanyVO vo = new CompanyVO();
        vo.setId(source.getId());
        vo.setCode(source.getCode());
        vo.setName(source.getName());
        vo.setContact(source.getContact());
        vo.setPhone(source.getPhone());
        vo.setStatus(source.getStatus());
        vo.setCreateTime(source.getCreateTime());
        return vo;
    }

    public CompanyEntity toNewEntity(CompanySaveDTO dto) {
        CompanyEntity entity = new CompanyEntity();
        applyDto(entity, dto);
        return entity;
    }

    public void applyDto(CompanyEntity entity, CompanySaveDTO dto) {
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setContact(dto.getContact());
        entity.setPhone(dto.getPhone());
        entity.setStatus(dto.getStatus());
    }
}
