package com.ecgobike.helper;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

/**
 * Created by ChenJun on 2018/4/25.
 */
public class BeanHelper {
    public static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    //DestinationObject destObject = mapper.map(sourceObject, DestinationObject.class);
}
