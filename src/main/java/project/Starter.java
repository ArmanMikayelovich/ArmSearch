/**
 * Starts the whole application
 * and deploys it to the server
 */

package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Starter {

    public static void main(String[] args) {

        SpringApplication.run(Starter.class, args);
    }
}
