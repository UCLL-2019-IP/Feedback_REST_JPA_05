package be.ucll.feedback.repository;

import be.ucll.feedback.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository has all the common stuff, like getting all the entities,
// create, update and delete.
// generic types Feedback and Integer as id
// need to use Integer (not int!), since primitive types are not supported
@Repository  // need to tell Spring this is a repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByFeedbackId(int feedbackId);
}
