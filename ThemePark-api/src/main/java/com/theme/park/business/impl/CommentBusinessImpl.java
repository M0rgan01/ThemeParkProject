package com.theme.park.business.impl;

import com.theme.park.business.CommentBusiness;
import com.theme.park.business.ParkBusiness;
import com.theme.park.business.SocialUserBusiness;
import com.theme.park.doa.CommentRepository;
import com.theme.park.entities.Comment;
import com.theme.park.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Component
public class CommentBusinessImpl implements CommentBusiness {

    private CommentRepository commentRepository;
    private ParkBusiness parkBusiness;
    private SocialUserBusiness socialUserBusiness;
    private static final Logger logger = LoggerFactory.getLogger(CommentBusinessImpl.class);

    public CommentBusinessImpl(CommentRepository commentRepository,
                               ParkBusiness parkBusiness,
                               SocialUserBusiness socialUserBusiness) {
        this.commentRepository = commentRepository;
        this.parkBusiness = parkBusiness;
        this.socialUserBusiness = socialUserBusiness;
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment) throws NotFoundException {
        comment.setSocialUser(socialUserBusiness.getSocialUserByEmail(comment.getSocialUser().getEmail(), comment.getSocialUser().getProvider()));
        comment.setDate(new Date());
        comment = commentRepository.save(comment);
        logger.info("Create comment with id " + comment.getId());
        parkBusiness.updateNotation(comment.getPark().getId());
        return comment;
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, Comment comment) throws NotFoundException {

        comment = commentRepository.save(comment);
        logger.info("Update comment with id " + id);
        parkBusiness.updateNotation(comment.getPark().getId());
        return comment;
    }

    @Override
    public void deleteComment(Long id) throws NotFoundException {
        logger.info("Delete comment with id " + id);
        commentRepository.delete(getCommentById(id));
    }

    @Override
    public Comment getCommentById(Long id) throws NotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);

        if (!comment.isPresent())
            throw new NotFoundException("comment.not.found");

        logger.debug("Getting comment with id " + id);
        return comment.get();
    }
}
