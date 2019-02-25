/**
 * Starts the whole application
 * and deploys it to the server
 */

package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import project.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println(userRepository.findAll());
            System.out.println("asdf");
            System.out.println("asdf");
            System.out.println("asdf");
        };
    }
}