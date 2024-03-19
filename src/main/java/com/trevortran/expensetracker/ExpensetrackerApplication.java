package com.trevortran.expensetracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@Slf4j
public class ExpensetrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensetrackerApplication.class, args);
	}

//	@Bean
//	public CommonsRequestLoggingFilter logFilter() {
//		CommonsRequestLoggingFilter filter
//				= new CommonsRequestLoggingFilter();
//		filter.setIncludeQueryString(true);
//		filter.setIncludePayload(true);
//		filter.setMaxPayloadLength(10000);
//		filter.setIncludeHeaders(true);
//		filter.setIncludePayload(true);
//		log.info(filter.toString());
//		return filter;
//	}
}
