package com.RabbitMqApplication;

/**
 * 
 */


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author kiran koli
 *
 * 
 */

@RestController
@RequestMapping
public class RabbitMqController {

	RabbitMqProducer rabbitMqProducer;

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqController.class);

	public RabbitMqController(RabbitMqProducer r) {
		rabbitMqProducer = r;
	}

	@PostMapping("/api")
	public ResponseEntity<String> pushMessage(@RequestBody(required = false) String xml) {
		LOGGER.info("Incoming Request ...");
		try {
			rabbitMqProducer.sendMessage(xml);
			return ResponseEntity.ok("Mesaage sended To Queue");
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return ResponseEntity.ok("Mesaage Not Sended");

	}

}