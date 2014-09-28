package com.panjiesw.blangszut;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

public abstract class PersistenceConfig {

	@Bean
	public abstract DataSource dataSource();

	protected abstract Properties additionalProperties();

	protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource) {
		dataSource.setMaxActive(20);
		dataSource.setMaxIdle(8);
		dataSource.setMinIdle(8);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.panjiesw.blangszut.persistence.domains");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		em.afterPropertiesSet();
		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}

@Configuration
@Profile(BlangszutProfiles.STANDALONE)
class StandalonePersistenceConfig extends PersistenceConfig {

	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:sagan;MODE=PostgreSQL");
		dataSource.setUsername("bl");
		dataSource.setPassword("");
		dataSource.setValidationQuery("SELECT 1");

		configureDataSource(dataSource);

		return dataSource;
	}

	protected Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		return properties;
	}
}

@Configuration
@Profile(BlangszutProfiles.OPENSHIFT)
class OpenshiftPersistenceConfig extends PersistenceConfig {

	@Override
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("${OPENSHIFT_POSTGRESQL_DB_URL}/blangszut");

		configureDataSource(dataSource);

		return dataSource;
	}

	@Override
	protected Properties additionalProperties() {
		return null;
	}
}
