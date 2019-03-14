package be.ucll.feedback.repository;

import be.ucll.feedback.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository has all the common stuff, like getting all the entities,
// create, update and delete.
// generic types Topic and String as id
@Repository  // need to tell Spring this is a repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    /*
    Convention over configuration!
    You can define searches on the database on any field (parameter) you have in
    your entity (Topic in our case - id, title and description).
    The rule is that you define the method starting with "findBy" and then tack
    the name of the parameter on to it, in this case "title". Since this uses camelcase,
    we end up with "findByTitle". This needs to return a List of Topic.
     */

    List<Topic> findByTitle(String title);
}
