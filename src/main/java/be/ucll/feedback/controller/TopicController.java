package be.ucll.feedback.controller;

import be.ucll.feedback.model.MyService;
import be.ucll.feedback.model.Topic;
import be.ucll.feedback.model.TopicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController // need to tell Spring this is a REST controller
public class TopicController {
    @Autowired
    MyService topicService;

    @GetMapping("topic")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("topic")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewTopic(@RequestBody @Valid Topic topic) {
        topicService.addTopic(topic);
    }

    @GetMapping("topic/id/{foo}")
    public Topic getSpecificTopicById(@PathVariable("foo") String id) {
        return topicService.findTopicById(id);
    }

    @GetMapping("topic/title/{title}")
    public List<Topic> getSpecificTopicByTitle(@PathVariable() String title) {
        return topicService.findTopicByTitle(title);
    }

    // When a client needs to replace an existing Resource entirely, they can use PUT.
    // When they’re doing a partial update, they can use HTTP PATCH. Complicated -> for later.
    @PutMapping("topic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editSpecificWholeTopic(@PathVariable("id") String id, @RequestBody @Valid Topic changedTopic) {
        topicService.changeTopic(id, changedTopic);
    }

    @DeleteMapping("topic/{id}")
    // HTTP 204 No Content: The server successfully processed the request, but is not returning any content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("id") String id) {
        topicService.deleteTopic(id);
    }

    // Still the same exception handler as in the previous version
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requested topic(s) not found!")
    @ExceptionHandler(value = IllegalArgumentException.class)
    public void badIdExceptionHandler() {
    }
}
