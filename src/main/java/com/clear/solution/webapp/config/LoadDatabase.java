package com.clear.solution.webapp.config;

import com.clear.solution.webapp.model.User;
import com.clear.solution.webapp.repository.UserRepository;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading {}", repository.save(
                    new User("bilbo@mail.com",
                            "Bilbo",
                            "Baggins",
                            LocalDate.of(1995, 5, 12),
                            "Canada,Laval,293-B Bd Cartier O",
                            "(450) 972-0963")));

            log.info("Preloading {}", repository.save(
                    new User("geraldine@mail.com",
                            "Smitham",
                            "Geraldine",
                            LocalDate.of(1977, 10, 6),
                            "Germany,Mettmann,Laubacher Strasse 13",
                            "(403) 235-4529")));
        };
    }
}