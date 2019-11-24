package com.theme.park.business;

import com.theme.park.entities.Comment;
import com.theme.park.exception.NotFoundException;

public interface CommentBusiness {
    Comment createComment(Comment comment) throws NotFoundException;
    Comment updateComment(Long id, Comment comment) throws NotFoundException;
    void deleteComment(Long id) throws NotFoundException;
    Comment getCommentById(Long id) throws NotFoundException;
}
