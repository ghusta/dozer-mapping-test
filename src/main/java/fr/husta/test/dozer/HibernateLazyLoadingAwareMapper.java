package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.fieldmap.FieldMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;

/**
 * CustomFieldMapper to manage Lazy Loading with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
@Slf4j
public abstract class HibernateLazyLoadingAwareMapper
        implements CustomFieldMapper {

    private final boolean skipLazyLoading;

    protected HibernateLazyLoadingAwareMapper(boolean skipLazyLoading) {
        this.skipLazyLoading = skipLazyLoading;
        log.debug("skipLazyLoading is : {}", skipLazyLoading);
    }

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        if (skipLazyLoading) {
            // If field is initialized, Dozer will continue mapping
            boolean stopMapping = false;

            // Spans PersistentBag, PersistentSet, PersistentList, etc.
            if (sourceFieldValue instanceof PersistentCollection) {
                boolean wasInitialized = ((PersistentCollection) sourceFieldValue).wasInitialized();
                stopMapping = !wasInitialized;
            }

            return stopMapping;
        } else {
            return false;
        }
    }

}
