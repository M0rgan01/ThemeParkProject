package com.theme.park.business;

import com.theme.park.entities.SocialUser;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.SocialUserDTO;

import java.math.BigInteger;

public interface SocialUserBusiness {

    SocialUser getSocialUserById(String id, String provider) throws NotFoundException;
    SocialUser getSocialUserByEmail(String email, String provider) throws NotFoundException;
    SocialUser createSocialUser(SocialUserDTO socialUserDTO) throws NotFoundException;
}
