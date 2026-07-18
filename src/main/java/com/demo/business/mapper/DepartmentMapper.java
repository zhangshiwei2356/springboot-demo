package com.demo.business.mapper;

import com.demo.business.config.BusinessDataProperties;
import com.demo.business.entity.DepartmentEntity;
import com.demo.business.mapper.support.AbstractJsonFileMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Repository
public class DepartmentMapper extends AbstractJsonFileMapper<DepartmentEntity> {

    public DepartmentMapper(BusinessDataProperties properties) throws IOException {
        super(
                Path.of(properties.getDataDir()).toAbsolutePath().normalize().resolve("departments.json"),
                new TypeReference<List<DepartmentEntity>>() { },
                "demo-seed/departments.json",
                null);
    }
}
