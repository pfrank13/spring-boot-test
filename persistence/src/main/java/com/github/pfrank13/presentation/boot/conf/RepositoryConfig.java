package com.github.pfrank13.presentation.boot.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author pfrank
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.github.pfrank13.presentation.boot.repository"},
                       entityManagerFactoryRef = "persistenceDbEntityManagerFactory",
                       transactionManagerRef = "persistenceDbTransactionManager")
@EnableTransactionManagement
public class RepositoryConfig {
  private DataSource persistenceDbDataSource;
  private String hibernateDialect;

  @Autowired
  public RepositoryConfig(@Qualifier("persistenceDbDataSource") final DataSource persistenceDbDataSource,
                          @Value("${persistence.hibernate.dialect}") final String hibernateDialect){
    this.persistenceDbDataSource = persistenceDbDataSource;
    this.hibernateDialect = hibernateDialect;
  }

  @Bean
  public EntityManagerFactory persistenceDbEntityManagerFactory(){
    final LocalContainerEntityManagerFactoryBean factoryBean =
        new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(persistenceDbDataSource);
    factoryBean.setPersistenceUnitName("persistenceDb");
    factoryBean.setPackagesToScan("com.mobileforming.clover.leads.model");
    final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setGenerateDdl(false);
    jpaVendorAdapter.setShowSql(true);

    jpaVendorAdapter.setDatabasePlatform(hibernateDialect);
    factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

    factoryBean.afterPropertiesSet();

    return factoryBean.getObject();
  }

  @Bean
  public PlatformTransactionManager persistenceDbTransactionManager() {
    final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(this.persistenceDbEntityManagerFactory());

    return jpaTransactionManager;
  }
}
