package com.pichincha.exam.accounts.helper;

import com.pichincha.exam.accounts.domain.dto.ClientDto;
import com.pichincha.exam.accounts.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Random;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(target = "number", source = "accountNumber"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "initialValue", source = "initialBalance"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "clientId", source = "client")
    })
    Account accountDtoToEntity(com.pichincha.exam.models.Account account);

    @Mappings({
            @Mapping(source = "number", target = "accountNumber"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "initialValue", target = "initialBalance"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "clientId", target = "client")

    })
    com.pichincha.exam.models.Account accountEntityToDto(Account account);


    @Mappings({
            @Mapping(target = "accountNumber", expression = "java(newAccount())"),
            @Mapping(target = "type", constant = "SAVINGS"),
            @Mapping(target = "initialBalance", constant = "100"),
            @Mapping(target = "status", constant = "true"),
            @Mapping(target = "client", source = "id")
    })
    com.pichincha.exam.models.Account personToAccount(ClientDto client);


    default String newAccount() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000));
    }

}


