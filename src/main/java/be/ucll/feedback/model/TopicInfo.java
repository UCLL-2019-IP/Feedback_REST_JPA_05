package be.ucll.feedback.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/*
Tell JPA that this should be saved in the database, by adding @Entity
a table "topic_info" will be made in the database.
Fields will be made in that table, corresponding to the variables in the class
in this case: topicInfoId, creationDate and info.
*/
@Entity
@Table(name = "topic_info")
public class TopicInfo {
    // tell JPA that the field topicInfoId will be the primary key, by adding the @Id annotation
    @Id
    private String topicInfoId;

    @NotEmpty // includes @NotNull
    private String creationDate;

    private String info; // can be empty

    // no-args Constructor, always necessary!
    public TopicInfo() { }

    // new constructor used in the CommandLineRunner, to populate the database
    public TopicInfo(String topicInfoId, String creationDate, String info) {
        this.topicInfoId = topicInfoId;
        this.creationDate = creationDate;
        this.info = info;
    }

    public String getTopicInfoId() {
        return topicInfoId;
    }

    public void setTopicInfoId(String topicInfoId) {
        this.topicInfoId = topicInfoId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
