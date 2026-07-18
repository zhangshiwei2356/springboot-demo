package com.demo.business.mapper;

import com.demo.business.config.BusinessDataProperties;
import com.demo.business.entity.AdminUserEntity;
import com.demo.business.mapper.support.AbstractJsonFileMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Repository
public class AdminUserMapper extends AbstractJsonFileMapper<AdminUserEntity> {

    public AdminUserMapper(BusinessDataProperties properties) throws IOException {
        super(
                Path.of(properties.getDataDir()).toAbsolutePath().normalize().resolve("users.json"),
                new TypeReference<List<AdminUserEntity>>() { },
                "demo-seed/users.json",
                null);
    }
}
