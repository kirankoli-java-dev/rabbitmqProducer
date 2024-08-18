package com.RabbitMqApplication;

/**
 * 
 */


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
//import org.springframework.amqp.rabbit.retry.RetryTemplate;
//import org.springframework.amqp.rabbit.support.PublisherCallbackChannelImpl;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
//import org.springframework.amqp.rabbit.transaction.RabbitTransactionManagerFactoryBean;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author kiran koli
 *
 * 
 */
@Configuration
public class RabbitMqConfig {

	@Value("${rabbitmq.username}")
	private String RabbitmqUsername;

	@Value("${rabbitmq.password}")
	private String RabbitmqPassword;

	@Value("${rabbitMq.queue.name}")
	private String queue;

	@Value("${rabbitMq.exchange.name}")
	private String exchange;

	@Value("${rabbitMq.routingKey}")
	private String routingKey;

	@Value("${rabbitmq.virtualHost}")
	private String RabbitmqVirtualHost;

	@Value("${rabbitmq.host}")
	private String Rabbitmqhost;

	@Value("${rabbitmq.port}")
	private int RabbitmqPort;

	@Value("${rabbitmq.InitialInterval}")
	private long InitialInterval;

	@Value("${rabbitmq.Multiplier}")
	private double Multiplier;

	@Value("${rabbitmq.MaxInterval}")
	private long MaxInterval;

	@Bean
	public Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	// Binding Between queue and exchange using routingKey

	@Bean
	public Binding binding() {

		return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(Rabbitmqhost, RabbitmqPort);
		connectionFactory.setUsername(RabbitmqUsername);
		connectionFactory.setPassword(RabbitmqPassword);
		connectionFactory.setVirtualHost(RabbitmqVirtualHost);
		connectionFactory.setPublisherConfirms(true); // for confirming that messages are received
		connectionFactory.setPublisherReturns(true); // for returns if the messages are undeliverable
		return connectionFactory;
	}

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(InitialInterval);
		backOffPolicy.setMultiplier(Multiplier);
		backOffPolicy.setMaxInterval(MaxInterval);

		retryTemplate.setBackOffPolicy(backOffPolicy);

		return retryTemplate;
	}

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, RetryTemplate retryTemplate) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		rabbitTemplate.setRetryTemplate(retryTemplate);
		rabbitTemplate.setMandatory(true); // Ensures messages are returned if not deliverable
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter converter() {
		return new SimpleMessageConverter();
	}

	@Bean
	public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
		return new RabbitTransactionManager(connectionFactory);
	}

	// conctionFactory
	// RabbitTemplate
	// RabbitAdmin
	// All above config will do spring container for us

}
