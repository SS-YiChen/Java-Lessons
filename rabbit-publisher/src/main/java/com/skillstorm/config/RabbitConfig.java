package com.skillstorm.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	//pokemon
	@Value("${queues.oak}")
	private String queueName;
	
	@Value("${exchanges.fanout}")
	private String fanoutExchange;
	
	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}
	
	@Bean
	public Exchange fanoutExchange() {
		return new FanoutExchange(fanoutExchange);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
