package com.demo.business.mapper;

import com.demo.business.config.BusinessDataProperties;
import com.demo.business.entity.CompanyEntity;
import com.demo.business.mapper.support.AbstractJsonFileMapper;
import com.demo.common.util.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Repository
public class CompanyMapper extends AbstractJsonFileMapper<CompanyEntity> {

    public CompanyMapper(BusinessDataProperties properties) throws IOException {
        super(
                Path.of(properties.getDataDir()).toAbsolutePath().normalize().resolve("companies.json"),
                new TypeReference<List<CompanyEntity>>() { },
                "demo-seed/companies.json",
                entity -> {
                    if (isBlank(entity.getCreateTime())) {
                        entity.setCreateTime(DateUtils.formatNow());
                    }
                });
    }
}
