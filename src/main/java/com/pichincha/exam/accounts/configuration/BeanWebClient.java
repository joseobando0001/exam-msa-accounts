package com.pichincha.exam.accounts.configuration;

import com.pichincha.exam.users.CustomerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanWebClient {

    private final PropertiesConfiguration propertiesConfiguration;

    @Bean
    public CustomerApi customerApi() {
        final var customerApi = new CustomerApi();
        customerApi.getApiClient().setBasePath(propertiesConfiguration.getClientUsers());
        return customerApi;
    }

}
