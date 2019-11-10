package com.theme.park.business.impl;

import com.theme.park.business.CommentBusiness;
import com.theme.park.doa.CommentRepository;
import com.theme.park.entities.Comment;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CommentDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CommentBusinessImpl implements CommentBusiness {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CommentBusinessImpl.class);

    public CommentBusinessImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setDate(new Date());
        comment = commentRepository.save(comment);
        logger.info("Create comment with id " + comment.getId());
        return comment;
    }

    @Override
    public Comment updateComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment = commentRepository.save(comment);
        logger.info("Update comment with id " + comment.getId());
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
