package viet.moba.regret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegretServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegretServerApplication.class, args);
	}

}
