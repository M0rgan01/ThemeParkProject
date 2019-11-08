package com.theme.park.business.impl;

import com.theme.park.business.RoleBusiness;
import com.theme.park.business.SocialUserBusiness;
import com.theme.park.doa.SocialUserRepository;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.SocialUserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
public class SocialUserBusinessImpl implements SocialUserBusiness {

    private ModelMapper modelMapper;
    private SocialUserRepository socialUserRepository;
    private RoleBusiness roleBusiness;

    public SocialUserBusinessImpl(SocialUserRepository socialUserRepository, ModelMapper modelMapper, RoleBusiness roleBusiness) {
        this.socialUserRepository = socialUserRepository;
        this.modelMapper = modelMapper;
        this.roleBusiness = roleBusiness;
    }

    @Override
    public SocialUser getSocialUserById(String id, String provider) throws NotFoundException {

        Optional<SocialUser> socialUser = socialUserRepository.findByIdAndProvider(id, provider);

        if (!socialUser.isPresent())
            throw new NotFoundException("user.not.found");

        return socialUser.get();
    }

    @Override
    public SocialUser getSocialUserByEmail(String email, String provider) throws NotFoundException {
        Optional<SocialUser> socialUser = socialUserRepository.findByEmailAndProvider(email, provider);

        if (!socialUser.isPresent())
            throw new NotFoundException("user.not.found");

        return socialUser.get();
    }

    @Override
    public SocialUser createSocialUser(SocialUserDTO socialUserDTO) throws NotFoundException {
        SocialUser socialUser = modelMapper.map(socialUserDTO, SocialUser.class);
        socialUser.setRoles(Arrays.asList(roleBusiness.getUserRole()));
        socialUser.setActive(true);
        socialUser.setFirstLoginDate(new Date());
        return socialUserRepository.save(socialUser);
    }
}
