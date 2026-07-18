package com.demo.business.mapper;

import com.demo.business.config.BusinessDataProperties;
import com.demo.business.entity.ProductEntity;
import com.demo.business.mapper.support.AbstractJsonFileMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Repository
public class ProductMapper extends AbstractJsonFileMapper<ProductEntity> {

    public ProductMapper(BusinessDataProperties properties) throws IOException {
        super(
                Path.of(properties.getDataDir()).toAbsolutePath().normalize().resolve("products.json"),
                new TypeReference<List<ProductEntity>>() { },
                "demo-seed/products.json",
                null);
    }
}
