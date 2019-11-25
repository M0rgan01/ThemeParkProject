package com.theme.park.business;

import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Comment;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentBusiness {

    Page<Comment> searchComments(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException;
    Comment createComment(Comment comment) throws NotFoundException;
    Comment updateComment(Long id, Comment comment) throws NotFoundException;
    void deleteComment(Long id) throws NotFoundException;
    Comment getCommentById(Long id) throws NotFoundException;
}
