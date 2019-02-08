/**
 * Starts the whole application
 * and deploys it to the server
 */

package project;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableJpaAuditing
public class Starter {


    public static void main(String[] args) {

        SpringApplication.run(Starter.class, args);
    }


    /**
     * տալիս է մեզ SessionFactory
     * @return
     */

}
