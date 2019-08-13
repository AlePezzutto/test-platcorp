package testplatcorp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);

		factoryBean.setPackagesToScan("testplatcorp.data.domains", "testplatcorp.data.domains.dto");
		
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaProperties(additionalProperties());
		
		return factoryBean;
	}

	private Properties additionalProperties() {
		
		Properties properties = new Properties();
		
		properties.setProperty("spring.jpa.open-in-view", "false");
		properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");

		return properties;
	}
	
	@Bean
	public DriverManagerDataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setUsername("usr_platcorp");
		dataSource.setPassword("platcorp2019");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/platcorp");
		dataSource.setDriverClassName("org.postgresql.Driver");
			
		return dataSource;
	}
}
