package pers.wdcy.biology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "pers.wdcy.biology.repository")
@SpringBootApplication(scanBasePackages = "pers.wdcy")
public class BiologyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiologyApplication.class, args);
	}

}
