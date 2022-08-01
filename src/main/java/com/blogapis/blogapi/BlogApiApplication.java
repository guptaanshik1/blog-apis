package com.blogapis.blogapi;

import com.blogapis.blogapi.entity.Role;
import com.blogapis.blogapi.repository.RoleRepository;
import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${cloud_name}")
	private String cloudName;

	@Value("${api_key}")
	private String apiKey;

	@Value("${api_secret}")
	private String apiSecret;

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper () {
		return new ModelMapper();
	}

	@Bean
	public Cloudinary cloudinaryConfig () {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		return cloudinary;
	}

	@Override
	public void run(String... args) throws Exception {
		log.warn("BCrypted Password :: {}", passwordEncoder.encode("123456"));

		try {
			Role roleAdmin = new Role();
			roleAdmin.setId(501);
			roleAdmin.setName("ROLE_ADMIN");

			Role roleUser = new Role();
			roleUser.setId(502);
			roleUser.setName("ROLE_USER");

			List<Role> roles = List.of(roleAdmin, roleUser);
			List<Role> savedRoles = roleRepository.saveAll(roles);

			savedRoles.forEach(role -> log.info("Role :: {}", role));
			} catch (Exception e) {

		}
	}
}