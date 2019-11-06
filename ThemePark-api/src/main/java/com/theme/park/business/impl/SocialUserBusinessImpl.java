package com.theme.park.business.impl;

import com.theme.park.business.SocialUserBusiness;
import com.theme.park.doa.SocialUserRepository;
import com.theme.park.entities.SocialUser;
import com.theme.park.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialUserBusinessImpl implements SocialUserBusiness {

    private SocialUserRepository socialUserRepository;

    public SocialUserBusinessImpl(SocialUserRepository socialUserRepository) {
        this.socialUserRepository = socialUserRepository;
    }

    @Override
    public SocialUser getSocialUser(Long id, String provider) throws NotFoundException {

        Optional<SocialUser> socialUser = socialUserRepository.findByIdAndProvider(id, provider);

        if (!socialUser.isPresent())
            throw new NotFoundException("user.not.found");

        return socialUser.get();
    }

    @Override
    public SocialUser getSocialUser(String email, String provider) throws NotFoundException {
        Optional<SocialUser> socialUser = socialUserRepository.findByEmailAndProvider(email, provider);

        if (!socialUser.isPresent())
            throw new NotFoundException("user.not.found");

        return socialUser.get();
    }
}
