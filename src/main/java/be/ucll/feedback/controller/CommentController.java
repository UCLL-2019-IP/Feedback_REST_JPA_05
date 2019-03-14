package be.ucll.feedback.controller;

import be.ucll.feedback.model.Comment;
import be.ucll.feedback.model.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController // need to tell Spring this is a REST controller
public class CommentController {
    @Autowired
    MyService commentService;

    @GetMapping("comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("feedback/{feedbackId}/comment")
    public List<Comment> getAllCommentsPerFeedback(@PathVariable int feedbackId) {
        return commentService.getAllCommentsPerFeedback(feedbackId);
    }

    // using a set of mappings here!!!
    // also, using Optional for the parentCommentId
    // need to use Integer with the Optional, they don't like primitives
    @PostMapping({"feedback/{feedbackId}/comment", "feedback/{feedbackId}/comment/{parentCommentId}"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewComment(@RequestBody @Valid Comment comment,
                                 @PathVariable int feedbackId,
                                 @PathVariable Optional<Integer> parentCommentId) {
        // create comment and set feedback id, this is just to have the binding with feedback
        comment.setFeedbackId(feedbackId);

        // check if we got a parentCommentId
        if (parentCommentId.isPresent()) {
            // get topComment by id and set it, if it exists...
            Comment topComment = commentService.getCommentById(parentCommentId.get());
            if(topComment != null){
                comment.setTopComment(topComment);
            }
        }

        commentService.addComment(comment);
    }

    @DeleteMapping("feedback/{feedbackId}/comment/{commentId}")
    // HTTP 204 No Content: The server successfully processed the request, but is not returning any content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
    }

    // Still the same exception handler as in the previous version
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requested comment(s) not found!")
    @ExceptionHandler(value = IllegalArgumentException.class)
    public void badIdExceptionHandler() {
    }
}
