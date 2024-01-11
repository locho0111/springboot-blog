package com.springboot.blog.springbootblog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.blog.springbootblog.entity.Role;
import com.springboot.blog.springbootblog.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot Blog App REST APIs", description = "Spring Boot Blog App REST APIs Documentation", version = "v1.0", contact = @Contact(name = "Rick", email = "RickHo0104@gmail.com"), license = @License(name = "Apache 2.0")), externalDocs = @ExternalDocumentation(description = "Spring Boot Blog App Documentation", url = "https://github.com/locho0111/springboot-blog"))
public class SpringbootBlogApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		try {
			roleRepository.save(adminRole);
			roleRepository.save(userRole);
		} catch (Exception exception) {
			System.out.println("Duplicated Record");
		}

	}

}
