package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.fieldmap.FieldMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;

/**
 * CustomFieldMapper to avoid triggering Lazy Loading with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
@Slf4j
public class HibernateLazyLoadingAwareMapper
        implements CustomFieldMapper {

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        // If field is initialized, Dozer will continue mapping
        boolean stopMapping = false;

        // Spans PersistentBag, PersistentSet, PersistentList, etc.
        if (sourceFieldValue instanceof PersistentCollection) {
            stopMapping = !((PersistentCollection) sourceFieldValue).wasInitialized();
        }

        return stopMapping;
    }

}
