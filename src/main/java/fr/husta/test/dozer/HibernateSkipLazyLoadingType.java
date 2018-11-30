package fr.husta.test.dozer;

public enum HibernateSkipLazyLoadingType {

    /**
     * Hibernate Lazy Loading is enabled.
     */
    NONE,

    /**
     * Hibernate Lazy Loading is disabled for {@link org.hibernate.collection.spi.PersistentCollection PersistentCollection} (for ex : @OneToMany)
     * which have not been fetched.
     */
    SKIP_COLLECTIONS_ONLY,

    /**
     * Hibernate Lazy Loading is disabled for {@link org.hibernate.collection.spi.PersistentCollection PersistentCollection} (for ex : @OneToMany)
     * and joined Entities (for ex : @ManyToOne)
     * which have not been fetched.
     */
    SKIP_ALL;

}
