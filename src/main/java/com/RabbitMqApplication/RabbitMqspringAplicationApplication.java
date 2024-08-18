package com.RabbitMqApplication;


import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RabbitMqspringAplicationApplication  implements CommandLineRunner{

	@Value("${LogConfig}")
	private String LogConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqspringAplicationApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(RabbitMqspringAplicationApplication.class, args);
		
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Configurator.initialize(null, LogConfig);
		LOGGER.debug("Apllication Started .");
		
	}

}
