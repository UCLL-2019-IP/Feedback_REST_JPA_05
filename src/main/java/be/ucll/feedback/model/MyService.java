package be.ucll.feedback.model;

import be.ucll.feedback.repository.CommentRepository;
import be.ucll.feedback.repository.FeedbackRepository;
import be.ucll.feedback.repository.TopicInfoRepository;
import be.ucll.feedback.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service// need to tell Spring this is a service
public class MyService {
    @Autowired
    TopicRepository topicRepository; // dependency injection of the repository

    @Autowired
    TopicInfoRepository topicInfoRepository; // dependency injection of the repository

    @Autowired
    FeedbackRepository feedbackRepository; // dependency injection of the repository

    @Autowired
    CommentRepository commentRepository; // dependency injection of the repository

    // no more hardcoded values here!
    public MyService() { }


    // *************************
    // TOPIC INFO
    // *************************

    public List<TopicInfo> getAllTopicInfos() {
        return topicInfoRepository.findAll();
    }

    public TopicInfo getTopicInfoPerTopic(String topicId) {
        return topicInfoRepository.findByTopicInfoId(topicId);
    }

    public void addTopicInfo(TopicInfo topicInfo) {
        topicInfoRepository.save(topicInfo);
    }

    public void changeTopicInfo(String topicId, TopicInfo changedInfo) {
        changedInfo.setTopicInfoId(topicId); // use id from url, so people can't mess up
        // if row exists with this id, row is updated, otherwise it's created
        topicInfoRepository.save(changedInfo); // all we need to do to change a topic info
    }

    public void deleteTopicInfo(String id) {
        topicInfoRepository.deleteById(id);
    }

    // *************************
    // TOPICS
    // *************************

    // just return the whole list, Spring takes care of conversion to JSON
    public List<Topic> getAllTopics() {
        // look at what the autocomplete comes up with if you use the repository!
        return topicRepository.findAll();
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic); // all we need to do to save a topic
    }

    // look for a topic by id (see controller)
    public Topic findTopicById(String id) {
        /*
        This returns an "Optional". That is kind of a promise that can be broken.
        If there is nothing with that id in the database table, then the promise
        is broken. If that happens, you can act on it in different ways. Here, I
        just throw the IllegalArgumentException, like I always did.
        */
        return topicRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void changeTopic(String id, Topic changedTopic) {
        changedTopic.setTopicId(id); // use id from url, so people can't mess up
        // make sure we don't lose the feedbacks!
        // get them out first, and pass them on to the new topic.
        Topic topicToChange = this.findTopicById(id);
        changedTopic.setFeedbacks(topicToChange.getFeedbacks());
        // same for the topic info!
        changedTopic.setTopicInfo(topicToChange.getTopicInfo());
        // if row exists with this id, row is updated, otherwise it's created
        topicRepository.save(changedTopic); // all we need to do to change a topic
    }

    public void deleteTopic(String id) {
        topicRepository.deleteById(id);
    }

    // look for a topic by title (see controller)
    public List<Topic> findTopicByTitle(String title) {
        List<Topic> topics = new ArrayList<>();
        // needed to define the method in the repository!
        topics = topicRepository.findByTitle(title);
        // if nothing returned, throw an IllegalArgumentException
        if(topics.isEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            return topics;
        }
    }

    // *************************
    // FEEDBACKS
    // *************************

    // just return the whole list, Spring takes care of conversion to JSON
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getAllFeedbacksPerTopic(String topicId) {
        // look at what the autocomplete comes up with if you use the repository!
        return feedbackRepository.findByFeedbackTopicId(topicId);
    }

    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback); // all we need to do to save a feedback
    }

    // look for a feedback by id (see controller)
    public Feedback findFeedbackById(int id) {
        /*
        This returns an "Optional". That is kind of a promise that can be broken.
        If there is nothing with that id in the database table, then the promise
        is broken. If that happens, you can act on it in different ways. Here, I
        just throw the IllegalArgumentException, like I always did.
        */
        return feedbackRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void changeFeedback(int id, Feedback changedFeedback) {
        changedFeedback.setFeedbackId(id); // use id from url, so people can't mess up
        // if row exists with this id, row is updated, otherwise it's created
        feedbackRepository.save(changedFeedback); // all we need to do to change a feedback
    }

    public void deleteFeedback(int id) {
        feedbackRepository.deleteById(id);
    }

    // look for a feedback by name (see controller)
    public List<Feedback> findFeedbackByName(String topicId, String name) {
        List<Feedback> feedbacks = new ArrayList<>();
        // needed to define the method in the repository!
        feedbacks = feedbackRepository.findByFeedbackTopicIdAndName(topicId, name);
        // if nothing returned, throw an IllegalArgumentException
        if(feedbacks.isEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            return feedbacks;
        }
    }

    // *************************
    // COMMENTS
    // *************************

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsPerFeedback(int feedbackId) {
        return commentRepository.findAllByFeedbackId(feedbackId);
    }

    public Comment getCommentById(int parentCommentId) {
        /*
        This returns an "Optional". That is kind of a promise that can be broken.
        If there is nothing with that id in the database table, then the promise
        is broken. If that happens, you can act on it in different ways. Here, I
        just throw the IllegalArgumentException, like I always did.
        */
        return commentRepository.findById(parentCommentId).orElse(null);
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
