package be.ucll.feedback.repository;

import be.ucll.feedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository has all the common stuff, like getting all the entities,
// create, update and delete.
// generic types Feedback and Integer as id
// need to use Integer (not int!), since primitive types are not supported
@Repository  // need to tell Spring this is a repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    /*
    Convention over configuration!
    You can define searches on the database on any field (parameter) you have in
    your entity (Feedback in our case - id, name and feedbackMessage).
    The rule is that you define the method starting with "findBy" and then tack
    the name of the parameter on to it, in this case "name". Since this uses camelcase,
    we end up with "findByName". This needs to return a List of Feedback.
     */

    // new way to find all feedbacks by name, related to a specific topic
    // these are actually filters on your data
    public List<Feedback> findByFeedbackTopicIdAndName(String topicId, String name);

    // new way to find all feedbacks related to a specific topic
    // these are actually filters on your data
    public List<Feedback> findByFeedbackTopicId(String topicId);
}
