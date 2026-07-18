package com.demo.business.service.impl;

import com.demo.business.converter.VehicleConverter;
import com.demo.business.entity.VehicleEntity;
import com.demo.business.mapper.VehicleMapper;
import com.demo.business.service.VehicleService;
import com.demo.business.vo.VehicleVO;
import com.demo.common.exception.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleMapper vehicleMapper;
    private final VehicleConverter vehicleConverter;

    public VehicleServiceImpl(VehicleMapper vehicleMapper, VehicleConverter vehicleConverter) {
        this.vehicleMapper = vehicleMapper;
        this.vehicleConverter = vehicleConverter;
    }

    @Override
    public List<VehicleVO> listOnSale(String keyword) {
        String kw = StringUtils.hasText(keyword) ? keyword.trim().toLowerCase(Locale.ROOT) : null;
        return vehicleMapper.selectAll().stream()
                .filter(v -> "1".equals(v.getStatus()))
                .filter(v -> kw == null || matchesKeyword(v, kw))
                .map(vehicleConverter::convert)
                .toList();
    }

    @Override
    public VehicleVO getById(Long id) {
        VehicleEntity entity = vehicleMapper.selectById(id);
        if (entity == null) {
            throw new GlobalException("车辆不存在");
        }
        return vehicleConverter.convert(entity);
    }

    private static boolean matchesKeyword(VehicleEntity v, String kw) {
        return contains(v.getName(), kw)
                || contains(v.getBrand(), kw)
                || contains(v.getModel(), kw)
                || contains(v.getCode(), kw);
    }

    private static boolean contains(String text, String kw) {
        return text != null && text.toLowerCase(Locale.ROOT).contains(kw);
    }
}
