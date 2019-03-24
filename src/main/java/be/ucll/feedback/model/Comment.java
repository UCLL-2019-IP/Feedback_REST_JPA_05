package be.ucll.feedback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/*
Tell JPA that this should be saved in the database, by adding @Entity
a table "comment" will be made in the database.
Fields will be made in that table, corresponding to the variables in the class
in this case: commentId, name and commentMessage.
*/
@Entity
@Table(name = "comment")
public class Comment {
    // tell JPA that the field commentId will be the primary key, by adding the @Id annotation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // generate commentId automatically, no more AtomicInteger
    private int commentId;

    @NotEmpty // includes @NotNull
    @Size(min=2, max=20)
    private String name;

    @NotEmpty // includes @NotNull
    @Size(max=500)
    private String commentMessage;

    // we need to tie the comment to a feedback, so we create a new parameter here!
    private int feedbackId;
    // add getter & setter
    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }



    /*
    This is recursive! Each comment can have comments of its own, ad infinitum.
    */
    // Changed CascadeType.ALL to the set of {CascadeType.PERSIST, CascadeType.MERGE},
    // this prevents too much deleting, now it works as it should.
    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (name = "top_comment_id")
    // need this to prevent endless loop!
    @JsonBackReference // the back part of the reference – will be omitted from serialization
    // @JsonIgnore // -> this also works, instead of @JsonBackReference !!!!
    private Comment topComment;
    // add getter & setter
    public Comment getTopComment() { return topComment; }
    public void setTopComment(Comment topComment) { this.topComment = topComment; }


    @OneToMany (mappedBy = "topComment", cascade = CascadeType.ALL, orphanRemoval = true)
    // need this to prevent endless loop!
    @JsonManagedReference // the forward part of the reference – gets serialized normally
    // @JsonIgnore // -> this also works, instead of @JsonManagedReference !!!!
    private List<Comment> commentList = new ArrayList<Comment>();
    // add getter & setter for list
    public List<Comment> getCommentList() { return commentList; }
    public void setCommentList(List<Comment> commentList) { this.commentList = commentList;  }
    /*
    End of recursive stuff
     */



    // no-args Constructor, always necessary!
    public Comment() { }

    // new constructor used in the CommandLineRunner, to populate the database
    public Comment(String name, String commentMessage, int feedbackId, Comment topComment) {
        this.name = name;
        this.commentMessage = commentMessage;
        this.feedbackId = feedbackId;
        this.topComment = topComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }
}
