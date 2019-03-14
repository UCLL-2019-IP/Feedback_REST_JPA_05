package be.ucll.feedback.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/*
Tell JPA that this should be saved in the database, by adding @Entity
a table "feedback" will be made in the database.
Fields will be made in that table, corresponding to the variables in the class
in this case: topicId, title and description.
*/
@Entity
@Table(name = "topic")
public class Topic {
    // tell JPA that the field topicId will be the primary key, by adding the @Id annotation
    @Id
    private String topicId;

    @NotEmpty // includes @NotNull
    @Size(min = 2, max = 30)
    private String title;

    @NotEmpty // includes @NotNull
    @Size(min = 5, max = 70)
    private String description;

    // since a topic can have many feedbacks, we need a OneToMany relationship
    // also add CascadeType.ALL so it automatically updates the feedbacks as well
    @OneToMany (cascade = CascadeType.ALL)
    /*
    we also need to tell the system how to connect these things
    use @JoinColumn() for one relation, @JoinColumns() for multiple relations
    since the @Id's are both called "topicId"...
    first parameter = topicId of feedback class -> feedbackTopicId (foreign key)
    second parameter = topicId of this class
    */
    @JoinColumn(name = "feedbackTopicId", referencedColumnName = "topicId")
    private List<Feedback> feedbacks;
    // add getter & setter for list
    public List<Feedback> getFeedbacks() { return feedbacks; }
    public void setFeedbacks(List<Feedback> feedbacks) { this.feedbacks = feedbacks; }

    // since a topic can have only one topic info, we need a OneToOne relationship
    // also add CascadeType.ALL so it automatically updates the topic info as well
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "topicId", referencedColumnName = "topicInfoId")
    private TopicInfo topicInfo;
    // add getter & setter
    public TopicInfo getTopicInfo() { return topicInfo; }
    public void setTopicInfo(TopicInfo topicInfo) { this.topicInfo = topicInfo; }

    // no-args Constructor, always necessary!
    public Topic() { }

    // constructor used in the CommandLineRunner, to populate the database
    public Topic(String topicId, String title, String description) {
        this.topicId = topicId;
        this.title = title;
        this.description = description;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
