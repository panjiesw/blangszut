package com.panjiesw.blangszut;

import org.h2.server.web.WebServlet;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import javax.sql.DataSource;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableJpaRepositories("com.panjiesw.blangszut.persistence.repositories")
@EnableTransactionManagement
public class BlangszutConfig {

	@Bean
	public HealthIndicator healthIndicator(DataSource dataSource) {
		if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
			org.apache.tomcat.jdbc.pool.DataSource tcDataSource =
					(org.apache.tomcat.jdbc.pool.DataSource) dataSource;
			return new AbstractHealthIndicator() {
				@Override
				protected void doHealthCheck(Health.Builder healthBuilder) throws Exception {
					healthBuilder.up().withDetail("active", tcDataSource.getActive())
							.withDetail("max_active", tcDataSource.getMaxActive())
							.withDetail("idle", tcDataSource.getIdle())
							.withDetail("max_idle", tcDataSource.getMaxIdle())
							.withDetail("min_idle", tcDataSource.getMinIdle())
							.withDetail("wait_count", tcDataSource.getWaitCount())
							.withDetail("max_wait", tcDataSource.getMaxWait());
				}
			};
		}
		return null;
	}

	@Bean
	@Profile(BlangszutProfiles.STANDALONE)
	public ServletRegistrationBean h2Console() {
		ServletRegistrationBean reg = new ServletRegistrationBean(new WebServlet(), "/console/*");
		reg.setLoadOnStartup(1);
		return reg;
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
}
