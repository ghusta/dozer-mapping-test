package fr.husta.test.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class MapperUtils {

    private static final Mapper instance;

    static {
        // See : https://dozermapper.github.io/gitbook/documentation/gettingstarted.html
        // and : https://dozermapper.github.io/gitbook/documentation/usage.html
        instance = DozerBeanMapperBuilder
                .create()
                .withMappingFiles("dozerBeanMapping.xml")
                .build();
    }

    private MapperUtils() {
        throw new AssertionError();
    }

    public static Mapper getMapper() {
        return instance;
    }

}
