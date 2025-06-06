package com.hiring.notificationms.config;

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
    public Queue notificationQueue() {
        return new Queue("hiringNotificationQueue", true);
    }

    @Bean
    public TopicExchange notificationTopicExchange() {
        return new TopicExchange("hiringNotificationTopicExchange");
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(notificationTopicExchange())
                .with("notification.*");
    }


    @Bean
    public Queue otpQueue() {
        return new Queue("hiringAuthOtpQueue", true);
    }

    @Bean
    public TopicExchange otpExchange() {
        return new TopicExchange("hiringAuthOtpTopicExchange");
    }

    @Bean
    public Binding otpBinding() {
        return BindingBuilder
                .bind(otpQueue())
                .to(otpExchange())
                .with("auth.*");
    }
}
