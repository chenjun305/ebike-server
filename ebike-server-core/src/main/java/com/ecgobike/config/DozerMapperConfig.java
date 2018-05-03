package com.ecgobike.config;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJun on 2018/4/25.
 */
@Configuration
public class DozerMapperConfig {

    @Bean(name = "org.dozer.Mapper")
    public Mapper dozerMapper() {
        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozerJdk8Converters.xml");
        return DozerBeanMapperBuilder.create()
                .withMappingFiles(mappingFiles)
                .build();
    }
}
