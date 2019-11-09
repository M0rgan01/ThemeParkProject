package com.theme.park.doa;

import com.theme.park.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndSocialUser(Long id, Long socialUserId);
    Page<Comment> findByPark(Long parkId, Pageable pageable);

}
