package com.pichincha.exam.accounts.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties
@Configuration
@Getter
@Setter
public class PropertiesConfiguration {
    @Value("${client.users}")
    private String clientUsers;
}
