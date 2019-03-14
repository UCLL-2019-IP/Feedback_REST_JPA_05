package be.ucll.feedback.repository;

import be.ucll.feedback.model.TopicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // need to tell Spring this is a repository
public interface TopicInfoRepository extends JpaRepository<TopicInfo, String> {
    TopicInfo findByTopicInfoIdAndCreationDate(String topicId, String date);

    TopicInfo findByTopicInfoId(String topicId);
}
