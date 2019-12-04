package com.theme.park.business.impl;

import com.theme.park.business.CommentBusiness;
import com.theme.park.business.ParkBusiness;
import com.theme.park.business.SocialUserBusiness;
import com.theme.park.doa.CommentRepository;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.UserNotMatchException;
import com.theme.park.object.SearchCriteria;
import com.theme.park.doa.specification.SpecificationBuilder;
import com.theme.park.entities.Comment;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Page<Comment> searchComments(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException {
        if (searchCriteria == null)
            searchCriteria = new ArrayList<>();

        SpecificationBuilder builder = new SpecificationBuilder<Comment>(searchCriteria);
        Specification<Comment> spec = builder.build();

        logger.debug("searching comment with " + searchCriteria.size() + " criteria list size for page " + page + " with size " + size);
        return commentRepository.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment) throws NotFoundException {
        comment.setSocialUser(socialUserBusiness.getSocialUserByEmail(comment.getSocialUser().getEmail(), comment.getSocialUser().getProvider()));
        comment.setDate(new Date());
        comment = commentRepository.save(comment);
        logger.info("Create comment with id " + comment.getId());
        if (comment.getNotation() != 0)
            parkBusiness.updateNotation(comment.getPark().getId());
        return comment;
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, Comment comment) throws NotFoundException {
        Comment commentCompare = getCommentById(id);
        boolean updateNotation = false;

        if (comment.getSocialUser().getSocialUserId() != commentCompare.getSocialUser().getSocialUserId())
            throw new UserNotMatchException("user.not.match");

        if (!comment.getContent().equals(commentCompare.getContent()))
            commentCompare.setContent(comment.getContent());

        if (comment.getNotation() != commentCompare.getNotation()) {
            commentCompare.setNotation(comment.getNotation());
            updateNotation = true;
        }

        commentCompare.setUpdatedContent(true);

        comment = commentRepository.save(commentCompare);

        if (updateNotation)
            parkBusiness.updateNotation(comment.getPark().getId());
        logger.info("Turn updated --> comment with id " + id);
        return comment;
    }

    @Override
    public Comment deleteComment(Long id, Long socialUserId) throws NotFoundException, AlreadyExistException {
        Comment comment = getCommentById(id);

        if (comment.isDeleteComment()) {
            throw new AlreadyExistException("comment.already.delete");
        } else if (comment.getSocialUser().getSocialUserId() != socialUserId){
            throw new UserNotMatchException("user.not.match");
        }

        comment.setDeleteComment(true);
        logger.info("Turn deleted --> comment with id " + id);
       return commentRepository.save(comment);
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
