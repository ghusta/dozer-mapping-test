package fr.husta.test.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class MapperUtils {

    private static Mapper instance;

    private MapperUtils() {
    }

    public static Mapper getMapper() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    private static synchronized void init() {
        instance = DozerBeanMapperBuilder
                .create()
                .withMappingFiles("dozerBeanMapping.xml")
                .build();
    }

}
