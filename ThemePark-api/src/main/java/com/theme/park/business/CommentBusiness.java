package com.theme.park.business;

import com.theme.park.entities.Comment;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.CommentDTO;

public interface CommentBusiness {
    Comment createComment(CommentDTO commentDTO) throws NotFoundException;
    Comment updateComment(Long id, CommentDTO commentDTO) throws NotFoundException;
    void deleteComment(Long id) throws NotFoundException;
    Comment getCommentById(Long id) throws NotFoundException;
}
