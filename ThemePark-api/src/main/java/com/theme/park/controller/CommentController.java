package com.theme.park.controller;

import com.theme.park.business.CommentBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themeParkAPI/auth")
public class CommentController {

    private CommentBusiness commentBusiness;

    public CommentController(CommentBusiness commentBusiness) {
        this.commentBusiness = commentBusiness;
    }
}
