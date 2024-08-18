package com.RabbitMqApplication;

/**
 * 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author kiran koli
 *
 * 
 */

@Service
public class RabbitMqProducer {

	@Value("${rabbitMq.exchange.name}")
	private String exchange;

	@Value("${rabbitMq.routingKey}")
	private String routingKey;

	public RabbitTemplate rabbitTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducer.class);

	public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 
	 */

	public void sendMessage(String json) {

		LOGGER.info("SENDING MESSAGE TO QUEUE " + json);
		rabbitTemplate.convertAndSend(exchange, routingKey, json);

	}

}
