package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.UserToken;
import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Organization;
import com.netcracker.crm.domain.model.Region;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.dto.mapper.AddressMap;
import com.netcracker.crm.dto.mapper.OrganizationMap;
import com.netcracker.crm.dto.mapper.RegionMap;
import com.netcracker.crm.dto.mapper.UserMap;
import com.netcracker.crm.service.UserTokenService;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import com.netcracker.crm.service.email.senders.RegSuccessEmailSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by Pasha on 02.05.2017.
 */
@Service
public class RegisterService {
    private static final String REFERENCE = "http://localhost:8888/user/confirm/registration?userId=%userId%&token=%token%";

    private final UserDao userDao;
    private final UserTokenService userTokenService;
    private final RegSuccessEmailSender regSuccessEmailSender;

    @Autowired
    public RegisterService(UserDao userDao, UserTokenService userTokenService,RegSuccessEmailSender regSuccessEmailSender) {
        this.userDao = userDao;
        this.userTokenService = userTokenService;
        this.regSuccessEmailSender = regSuccessEmailSender;
    }


    public Long registerUser(UserDto userDto){
        User user = mapFromDto(userDto);
        userDao.create(user);
        UserToken userToken = userTokenService.createUserToken(user);
        sendEmail(user, userToken, userDto.getPassword());
        return user.getId();
    }


    private void sendEmail(User user, UserToken token, String password){
        String concreteRef = REFERENCE
                .replaceAll("%userId%", String.valueOf(user.getId()))
                .replaceAll("%token%", token.getToken());
        EmailParam emailParam = new EmailParam(EmailType.REGISTRATION);
        emailParam.put(EmailParamKeys.USER_REFERENCE, concreteRef);
        emailParam.put(EmailParamKeys.USER, user);
        emailParam.put(EmailParamKeys.USER_PASSWORD, password);
        try {
            regSuccessEmailSender.send(emailParam);
        } catch (MessagingException e) {
//            TODO something
        }
    }



    private User mapFromDto(UserDto userDto) {
        ModelMapper mapper = configureMapper();

//        Region region = mapper.map(userDto.getAddress().getRegion(), Region.class);
//        Address address = mapper.map(userDto.getAddress(), Address.class);
//        Organization organization = mapper.map(userDto.getOrganization(), Organization.class);
        User user = mapper.map(userDto, User.class);

//        address.setRegion(region);
//        user.setOrganization(organization);
//        user.setAddress(address);

        return user;
    }

    private ModelMapper configureMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new UserMap());
        mapper.addMappings(new AddressMap());
        mapper.addMappings(new OrganizationMap());
        mapper.addMappings(new RegionMap());

        return mapper;
    }

}
