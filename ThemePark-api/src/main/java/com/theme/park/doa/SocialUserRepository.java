package com.theme.park.doa;

import com.theme.park.entities.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    Optional<SocialUser> findByNameAndProvider(String name, String provider);
    Optional<SocialUser> findByIdAndProvider(String id, String provider);
    Optional<SocialUser> findBySocialUserIdAndProvider(Long socialUserId, String provider);
    Optional<SocialUser> findByEmailAndProvider(String email, String provider);
}
