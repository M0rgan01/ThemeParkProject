package com.theme.park.business.impl;

import com.theme.park.business.RoleBusiness;
import com.theme.park.business.SocialUserBusiness;
import com.theme.park.doa.SocialUserRepository;
import com.theme.park.entities.Role;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.SocialUserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
public class SocialUserBusinessImpl implements SocialUserBusiness {

    private ModelMapper modelMapper;
    private SocialUserRepository socialUserRepository;
    private RoleBusiness roleBusiness;
    private static final Logger logger = LoggerFactory.getLogger(SocialUserBusinessImpl.class);

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

        logger.debug("Getting SocialUser ID " + id + " and provider " + provider);
        return socialUser.get();
    }

    @Override
    public SocialUser getSocialUserByEmail(String email, String provider) throws NotFoundException {
        Optional<SocialUser> socialUser = socialUserRepository.findByEmailAndProvider(email, provider);

        if (!socialUser.isPresent())
            throw new NotFoundException("user.not.found");

        logger.debug("Getting SocialUser email " + email + " and provider " + provider);
        return socialUser.get();
    }

    @Override
    public SocialUser createSocialUser(SocialUserDTO socialUserDTO) throws NotFoundException, AlreadyExistException {

        try {
            getSocialUserByEmail(socialUserDTO.getEmail(), socialUserDTO.getProvider());
            throw new AlreadyExistException("user.already.exist");
        } catch (NotFoundException e) {
            SocialUser socialUser = modelMapper.map(socialUserDTO, SocialUser.class);
            socialUser.setRoles(Arrays.asList(roleBusiness.getUserRole()));
            socialUser.setActive(true);
            socialUser.setFirstLoginDate(new Date());
            logger.debug("Create SocialUser email " + socialUserDTO.getEmail() + " and provider " + socialUserDTO.getProvider());
            return socialUserRepository.save(socialUser);
        }
    }

    @Override
    public SocialUser setAdminRole(String email, String provider) throws NotFoundException, AlreadyExistException {
        SocialUser socialUser = getSocialUserByEmail(email, provider);

        Role admin = roleBusiness.getAdminRole();

            if (socialUser.getRoles().contains(admin))
                throw new AlreadyExistException("role.admin.already.exist");

        socialUser.getRoles().add(admin);

        logger.info("Add ADMIN role for user with email " + socialUser.getEmail() + " and provider " + socialUser.getProvider());

        return socialUserRepository.save(socialUser);
    }
}
