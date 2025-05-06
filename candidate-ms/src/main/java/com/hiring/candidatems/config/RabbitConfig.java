package com.hiring.candidatems.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("com.hiring", "java.util");

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public Queue statusQueue() {
        return new Queue("hiringStatusQueue", true);
    }

    @Bean
    public TopicExchange statusTopicExchange() {
        return new TopicExchange("hiringStatusTopicExchange");
    }

    @Bean
    public Binding statusBinding() {
        return BindingBuilder
                .bind(statusQueue())
                .to(statusTopicExchange())
                .with("status.*");
    }
}
