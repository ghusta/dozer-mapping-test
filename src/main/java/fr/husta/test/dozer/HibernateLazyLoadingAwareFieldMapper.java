package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.fieldmap.FieldMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
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

    private final HibernateSkipLazyLoadingType skipLazyLoadingStrategy;

    protected HibernateLazyLoadingAwareFieldMapper(HibernateSkipLazyLoadingType skipLazyLoadingStrategy) {
        if (skipLazyLoadingStrategy == null) {
            throw new IllegalArgumentException("Parameter 'skipLazyLoadingStrategy' null");
        }
        this.skipLazyLoadingStrategy = skipLazyLoadingStrategy;
        log.debug("skipLazyLoading is : {}", skipLazyLoadingStrategy);
    }

    @Override
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        if (skipLazyLoadingStrategy == HibernateSkipLazyLoadingType.SKIP_ALL
                || skipLazyLoadingStrategy == HibernateSkipLazyLoadingType.SKIP_COLLECTIONS_ONLY) {
            // If field is initialized, Dozer will continue mapping
            boolean stopMapping = false;

            // in case of Hibernate mapping. Ex : @OneToMany / @ManyToMany
            // Spans PersistentBag, PersistentSet, PersistentList, etc.
            if (sourceFieldValue instanceof PersistentCollection) {
                boolean initialized = ((PersistentCollection) sourceFieldValue).wasInitialized();
                // TODO: simplifier avec org.hibernate.Hibernate#isInitialized
                log.debug("on ClassMap source [{}], dest [{}]",
                        classMap.getSrcClassName(),
                        classMap.getDestClassName());
                log.debug("on FieldMap source [{}], dest [{}] | wasInitialized = {}",
                        fieldMapping.getSrcFieldName(),
                        fieldMapping.getDestFieldName(),
                        initialized);
                stopMapping = !initialized;
                if (stopMapping) {
                    log.debug("Mapping of property [{}.{}] will be skipped / ignored by Dozer !",
                            classMap.getSrcClassName(), fieldMapping.getSrcFieldName());
                }
                return stopMapping;
            }

            if (skipLazyLoadingStrategy == HibernateSkipLazyLoadingType.SKIP_ALL) {
                // in case of Hibernate mapping. Ex : @ManyToOne
                // NOTE: source object may have been deproxied by com.github.dozermapper.core.util.MappingUtils#deProxy
                // if using HibernateProxyResolver
                if (source instanceof HibernateProxy) {
                    log.debug("Source : {} (@{}) est un HibernateProxy", source.getClass().getName(), source.hashCode());
                    HibernateProxy sourceHibernateProxy = (HibernateProxy) source;
                    LazyInitializer lazyInitializer = sourceHibernateProxy.getHibernateLazyInitializer();
                    Class<?> srcPersistentClass = lazyInitializer.getPersistentClass();
                    boolean initialized = !sourceHibernateProxy.getHibernateLazyInitializer().isUninitialized();
                    log.debug("Source HibernateProxy : initialized = {}", initialized);

                    if (!initialized) {
                        SharedSessionContractImplementor sharedSession = lazyInitializer.getSession();
                        if (sharedSession instanceof SessionImplementor) {
                            // forcer evict ?
                            // ((SessionImplementor) sharedSession).evict(source);
                        }
                    }

                    stopMapping = !initialized;
                    if (stopMapping) {
                        log.debug("Mapping of property [{}.{}] SHOULD be skipped / ignored by Dozer !",
                                srcPersistentClass.getName(), fieldMapping.getSrcFieldName());
                    }
                    return stopMapping;
                }
            }

            return stopMapping;
        } else if (skipLazyLoadingStrategy == HibernateSkipLazyLoadingType.NONE) {
            return false;
        } else {
            return false;
        }
    }

}
