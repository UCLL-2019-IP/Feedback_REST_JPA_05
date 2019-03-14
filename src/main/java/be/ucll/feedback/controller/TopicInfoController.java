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
public class TopicInfoController {
    @Autowired
    MyService topicService;

    @GetMapping("topicinfo")
    public List<TopicInfo> getAllTopicInfos(){
        return topicService.getAllTopicInfos();
    }

    @GetMapping("topic/{topicId}/info")
    public TopicInfo getTopicInfoPerTopic(@PathVariable String topicId) {
        return topicService.getTopicInfoPerTopic(topicId);
    }

    @PostMapping("topic/{topicId}/info")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewTopicInfo(@RequestBody @Valid TopicInfo topicInfo, @PathVariable String topicId) {
        // create topic info and set topic id, this is just to have the binding with topic
        topicInfo.setTopicInfoId(topicId);
        topicService.addTopicInfo(topicInfo);
    }

    // When a client needs to replace an existing Resource entirely, they can use PUT.
    // When they’re doing a partial update, they can use HTTP PATCH. Complicated -> for later.
    @PutMapping("topic/{topicId}/info")
    @ResponseStatus(HttpStatus.OK)
    public void editSpecificWholeTopicInfo(@PathVariable String topicId, @RequestBody @Valid TopicInfo changedInfo) {
        // create feedback and set topic, this is just to have the binding with topic
        changedInfo.setTopicInfoId(topicId);
        topicService.changeTopicInfo(topicId, changedInfo);
    }

    @DeleteMapping("topic/{topicId}/info/{id}")
    // HTTP 204 No Content: The server successfully processed the request, but is not returning any content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopicInfo(@PathVariable String id) {
        topicService.deleteTopicInfo(id);
    }

    // Still the same exception handler as in the previous version
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requested topic info(s) not found!")
    @ExceptionHandler(value = IllegalArgumentException.class)
    public void badIdExceptionHandler() {
    }
}
