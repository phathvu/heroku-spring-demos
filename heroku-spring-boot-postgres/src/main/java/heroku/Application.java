package heroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by vhphat on 6/9/2017.
 */
@SpringBootApplication
@ComponentScan(value = "heroku")
@RestController
public class Application {
    // https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String hello() {
        return "Hi there...Ahihi";
    }

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
}