package com.theme.park.business;

import com.theme.park.entities.SocialUser;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.NotFoundException;
import org.springframework.security.authentication.DisabledException;

public interface SocialUserBusiness {

    SocialUser getSocialUserById(String id, String provider) throws NotFoundException;
    SocialUser getSocialUserByEmail(String email, String provider) throws NotFoundException;
    SocialUser getSocialUserByName(String name, String provider) throws NotFoundException;
    SocialUser socialUserLogin(SocialUser socialUser) throws DisabledException, NotFoundException;
    SocialUser setAdminRole(String email, String provider) throws NotFoundException, AlreadyExistException;
}
