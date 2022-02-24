package com.example.api;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.User;
import com.example.api.repositories.PostLikeRepository;
import com.example.api.repositories.PostRepository;
import com.example.api.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiApplication.class, args);
		System.out.println("server listening...");
	}

	@Bean
	CommandLineRunner commandLineRunner(
			PostRepository postRepository,
			UserRepository userRepository,
			PostLikeRepository postLikeRepository
	){
		return args -> {

			//			users
			User placeholderUser1 = new User(
					null,
					"kavooce",
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>()
			);
			User placeholderUser2 = new User(
					null,
					"jack",
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>()
			);
			User placeholderUser3 = new User(
					null,
					"jill",
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>()
			);
			userRepository.save(placeholderUser1);
			userRepository.save(placeholderUser2);
			userRepository.save(placeholderUser3);

			Comment placeholderComment1 = new Comment(
					UUID.randomUUID(),
					placeholderUser2,
					"We are seeing the problem with the FED printing at a whim.  Sector rotation as money has to go somewhere, a phony narrative (Russia-Ukraine) to take the market down & kick up energy prices, setting up a major kick up next week",
					0,
					0,
					LocalDateTime.now(),
					new ArrayList<>()
			);
			Comment placeholderComment2 = new Comment(
					UUID.randomUUID(),
					placeholderUser3,
					"This will continue until there is an external event the FED or Central Banks can't control.  Just 1 major domino needs to fall out of control of the financial cab-l",
					0,
					0,
					LocalDateTime.now(),
					new ArrayList<>()
			);
			List<Comment> commentsList = new ArrayList<>();
			commentsList.add(placeholderComment1);
			commentsList.add(placeholderComment2);


			Post placeholderPost1 = new Post(
					UUID.randomUUID(),
					"SPY rising",
					"Stocks sell off into the market close, crude oil rises, cryptocurrencies under pressure",
					placeholderUser1,
					0,
					0,
					commentsList,
					LocalDateTime.now().plusHours(6),
					new ArrayList<>()
			);
			Post placeholderPost2 = new Post(
					UUID.randomUUID(),
					"SPY falling",
					"Stocks in a tailspin",
					placeholderUser1,
					0,
					0,
					new ArrayList<>(),
					LocalDateTime.now().plusHours(6),
					new ArrayList<>()
			);

			postRepository.save(placeholderPost1);
//			postRepository.save(placeholderPost2);
		};
	}

	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList(
				"http://localhost:3000",
				"http://localhost:4200"
		));
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin",
				"Access-Control-Allow-Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Origin, Accept",
				"X-Requested-With",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers"
		));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials",
				"Filename"
		));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET",
				"POST",
				"PUT",
				"PATCH",
				"DELETE",
				"OPTIONS"
		));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
