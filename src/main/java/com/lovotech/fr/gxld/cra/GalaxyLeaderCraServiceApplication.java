package com.lovotech.fr.gxld.cra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = { "com.lovotech.fr.gxld.core.bean.cra.domain" })
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableJpaAuditing
public class GalaxyLeaderCraServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalaxyLeaderCraServiceApplication.class, args);
	}

}
