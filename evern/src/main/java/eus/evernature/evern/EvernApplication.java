package eus.evernature.evern;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@EnableEncryptableProperties
@Slf4j
public class EvernApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EvernApplication.class, args);
		log.info(org.hibernate.Version.getVersionString());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("file:uploads/")
				.setCachePeriod(0);
	}
}
