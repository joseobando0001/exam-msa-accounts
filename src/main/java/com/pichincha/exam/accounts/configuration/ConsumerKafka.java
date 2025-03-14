package com.pichincha.exam.accounts.configuration;

import com.google.gson.Gson;
import com.pichincha.exam.accounts.domain.dto.ClientDto;
import com.pichincha.exam.accounts.helper.AccountMapper;
import com.pichincha.exam.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerKafka {

    private final AccountService accountService;

    @Bean("consume")
    public Consumer<Message<String>> consumer() {
        return this::processMessage;
    }

    private void processMessage(Message<String> message) {
        log.info("Receiving message {}", message.getPayload());
        accountService.postAccount(AccountMapper.INSTANCE.personToAccount(new Gson().fromJson(message.getPayload(), ClientDto.class)))
                .subscribe();
    }
}
