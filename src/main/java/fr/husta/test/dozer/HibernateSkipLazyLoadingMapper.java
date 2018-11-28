package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;

/**
 * CustomFieldMapper to avoid triggering Lazy Loading with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
public class HibernateSkipLazyLoadingMapper
        extends HibernateLazyLoadingAwareMapper
        implements CustomFieldMapper {

    public HibernateSkipLazyLoadingMapper() {
        super(true);
    }

}
