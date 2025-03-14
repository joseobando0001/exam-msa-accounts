package com.pichincha.exam.accounts.configuration;

import com.pichincha.exam.ApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeanWebClientTest {

    @InjectMocks
    BeanWebClient beanWebClient;

    @Mock
    PropertiesConfiguration propertiesConfiguration;
    private static final String LOCAL = "http://localhost/accounts";

    @Test
    void customerApi() {
        when(propertiesConfiguration.getClientUsers()).thenReturn(LOCAL);
        ApiClient apiClient = beanWebClient.customerApi().getApiClient();
        Assertions.assertNotNull(apiClient);
    }
}