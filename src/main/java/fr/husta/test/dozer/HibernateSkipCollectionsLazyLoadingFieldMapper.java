package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;

/**
 * CustomFieldMapper to avoid triggering Lazy Loading for Collections with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
public class HibernateSkipCollectionsLazyLoadingFieldMapper
        extends HibernateLazyLoadingAwareFieldMapper
        implements CustomFieldMapper {

    public HibernateSkipCollectionsLazyLoadingFieldMapper() {
        super(HibernateSkipLazyLoadingType.SKIP_COLLECTIONS_ONLY);
    }

}
