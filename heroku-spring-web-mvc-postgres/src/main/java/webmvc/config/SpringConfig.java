package webmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "webmvc.repository")
@EnableTransactionManagement
public class SpringConfig {
    // https://devcenter.heroku.com/articles/java-webapp-runner

    @Bean
    URI uri() throws URISyntaxException {
        // DATABASE_URL : System environment variable defined by default by Heroku
        // Syntax: postgres://username:password@hostname:port/schema-name
        String url = System.getenv("DATABASE_URL");
        return new URI(url);
    }

    @Bean
    DataSource dataSource() throws URISyntaxException {
        URI uri = uri();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?sslmode=require");
        dataSource.setUsername(uri.getUserInfo().split(":")[0]);
        dataSource.setPassword(uri.getUserInfo().split(":")[1]);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        // refer to datasource()
        entityManager.setDataSource(dataSource);
        // initialize our configuration with the default settings that are compatible with Hibernate
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // base package to scan for entity classess
        entityManager.setPackagesToScan("webmvc.entity");

        // JPA properties
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        entityManager.setJpaProperties(jpaProperties);

        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        // refer to entityManagerFactory()
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}