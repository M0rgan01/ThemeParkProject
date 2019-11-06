package com.theme.park.business;

import com.theme.park.entities.SocialUser;
import com.theme.park.exception.NotFoundException;

public interface SocialUserBusiness {

    SocialUser getSocialUser(Long id, String provider) throws NotFoundException;
    SocialUser getSocialUser(String email, String provider) throws NotFoundException;
}
