package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.fieldmap.FieldMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 * CustomFieldMapper to manage Lazy Loading with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
@Slf4j
public abstract class HibernateLazyLoadingAwareFieldMapper
        implements CustomFieldMapper {

    private final boolean skipLazyLoading;

    protected HibernateLazyLoadingAwareFieldMapper(boolean skipLazyLoading) {
        this.skipLazyLoading = skipLazyLoading;
        log.debug("skipLazyLoading is : {}", skipLazyLoading);
    }

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        if (skipLazyLoading) {
            // If field is initialized, Dozer will continue mapping
            boolean stopMapping = false;

            // in case of Hibernate mapping. Ex : @ManyToOne
            if (source instanceof HibernateProxy) {
                // NOTE: the HibernateProxy will be initialized anyway...
                // see also org.hibernate.Hibernate#initialize
                log.debug("Source : {} (@{}) is a HibernateProxy", source.getClass().getName(), source.hashCode());
                HibernateProxy sourceHibernateProxy = (HibernateProxy) source;
                LazyInitializer lazyInitializer = sourceHibernateProxy.getHibernateLazyInitializer();
                boolean initialized = !lazyInitializer.isUninitialized();
                log.debug("Source HibernateProxy : initialized = {}", initialized);
            }

            // in case of Hibernate mapping. Ex : @OneToMany / @ManyToMany
            // Spans PersistentBag, PersistentSet, PersistentList, etc.
            if (sourceFieldValue instanceof PersistentCollection) {
                boolean initialized = ((PersistentCollection) sourceFieldValue).wasInitialized();
                log.debug("on ClassMap source [{}], dest [{}]",
                        classMap.getSrcClassName(),
                        classMap.getDestClassName());
                log.debug("on FieldMap source [{}], dest [{}] | wasInitialized = {}",
                        fieldMapping.getSrcFieldName(),
                        fieldMapping.getDestFieldName(),
                        initialized);
                stopMapping = !initialized;
            }

            return stopMapping;
        } else {
            return false;
        }
    }

}
