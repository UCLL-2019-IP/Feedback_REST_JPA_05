package be.ucll.feedback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/*
Tell JPA that this should be saved in the database, by adding @Entity
a table "feedback" will be made in the database.
Fields will be made in that table, corresponding to the variables in the class
in this case: feedbackId, name and feedbackMessage.
*/
@Entity
@Table(name = "feedback")
public class Feedback {
    // tell JPA that the field feedbackId will be the primary key, by adding the @Id annotation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // generate feedbackId automatically, no more AtomicInteger
    private int feedbackId;

    @NotEmpty // includes @NotNull
    @Size(min=2, max=20)
    private String name;

    @NotEmpty // includes @NotNull
    @Size(max=500)
    private String feedbackMessage;

    // we need to tie the feedback to a topic, so we create a new parameter here!
    private String feedbackTopicId;
    // add getter & setter
    public String getFeedbackTopicId() { return feedbackTopicId; }
    public void setFeedbackTopicId(String feedbackTopicId) { this.feedbackTopicId = feedbackTopicId; }



    // since a feedback can have many comments, we need a OneToMany relationship
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "feedbackId", referencedColumnName = "feedbackId")
    private List<Comment> comments;
    // add getter & setter for list
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }



    // no-args Constructor, always necessary!
    public Feedback() { }

    // new constructor used in the CommandLineRunner, to populate the database
    public Feedback(String name, String feedbackMessage, String feedbackTopicId) {
        this.name = name;
        this.feedbackMessage = feedbackMessage;
        this.feedbackTopicId = feedbackTopicId;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
}
