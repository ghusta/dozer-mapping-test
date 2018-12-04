package fr.husta.test.dozer;

import com.github.dozermapper.core.CustomFieldMapper;

/**
 * CustomFieldMapper to avoid triggering Lazy Loading with Hibernate.
 *
 * @see com.github.dozermapper.core.DozerBeanMapperBuilder
 */
public class HibernateSkipLazyLoadingFieldMapper
        extends HibernateLazyLoadingAwareFieldMapper
        implements CustomFieldMapper {

    public HibernateSkipLazyLoadingFieldMapper() {
        super(true);
    }

}
