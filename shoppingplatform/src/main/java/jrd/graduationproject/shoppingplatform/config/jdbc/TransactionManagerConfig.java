package jrd.graduationproject.shoppingplatform.config.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class TransactionManagerConfig {

	@Bean("transactionManager")
	@Primary
	public DataSourceTransactionManager sqlServerDataSourceTransactionManager(
			@Qualifier("dataSource")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
