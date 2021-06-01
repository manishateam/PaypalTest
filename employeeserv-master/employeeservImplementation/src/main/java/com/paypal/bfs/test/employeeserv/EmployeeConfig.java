package com.paypal.bfs.test.employeeserv;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/v1/schema/employee.json")
public class EmployeeConfig {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

}
