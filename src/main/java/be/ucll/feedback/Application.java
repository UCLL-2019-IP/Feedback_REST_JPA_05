package be.ucll.feedback;

import be.ucll.feedback.model.Comment;
import be.ucll.feedback.model.Feedback;
import be.ucll.feedback.model.Topic;
import be.ucll.feedback.model.TopicInfo;
import be.ucll.feedback.repository.CommentRepository;
import be.ucll.feedback.repository.FeedbackRepository;
import be.ucll.feedback.repository.TopicInfoRepository;
import be.ucll.feedback.repository.TopicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication  // need to tell Spring this is a Spring Boot Application
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

    // use this to fill up the database from the start
    @Bean
    @Order(1) // do this first
    CommandLineRunner runnerTopics(TopicRepository repo){
        return TopicArgs -> {
            repo.save(new Topic("reis", "Reizen", "Reizen, vroeger en nu."));
            repo.save(new Topic("ip", "Cursus IP", "Feedback op de cursus IP."));
            repo.save(new Topic("filo", "Filosofie", "Existentiële prietpraat."));
        };
    }

    @Bean
    @Order(2) // do this secondly
    CommandLineRunner runnerInfo(TopicInfoRepository repo){
        return feedbackArgs -> {
            // info reizen
            //repo.save(new TopicInfo("8/03/2019", "Veel blabla over reizen.", "reis"));
            repo.save(new TopicInfo("reis", "8/03/2019", "Veel blabla over reizen."));

            // info cursus IP
            //repo.save(new TopicInfo("9/03/2019", "Ongeloofelijk interessante info over Spring Boot!", "ip"));
            repo.save(new TopicInfo("ip", "9/03/2019", "Ongeloofelijk interessante info over Spring Boot!"));

            // info filosofie
            //repo.save(new TopicInfo("10/03/2019", "I think, therefore I am. But where am I?", "filo"));
            repo.save(new TopicInfo("filo", "10/03/2019", "I think, therefore I am. But where am I?"));
        };
    }

    @Bean
    @Order(3) // do this thirdly
    CommandLineRunner runnerFeedbacks(FeedbackRepository repo){
        return feedbackArgs -> {
            // topic reizen
            repo.save(new Feedback("Jozef", "Dat kan hier niet ver meer zijn!", "reis"));
            repo.save(new Feedback("Maria", "Maar vraag dan toch eens de weg!", "reis"));

            // topic cursus IP
            repo.save(new Feedback("Rudy", "Dat kan hier veel beter!", "ip"));
            repo.save(new Feedback("Elke", "Dit is het beste wat je kan krijgen!", "ip"));
            repo.save(new Feedback("Rudi", "Dat gaat hier niet vooruit!", "ip"));

            // topic filosofie
            repo.save(new Feedback("Jules Kabas", "'t zijn zotten die werken!", "filo"));
            repo.save(new Feedback("Rudy", "Waar blijven die AI's om mijn werk over te nemen?", "filo"));
        };
    }

    /*
    @Bean
    @Order(4)
    CommandLineRunner runnerComments1(CommentRepository repo){
        return commentArgs -> {
            // topic reizen - feedback 2
            Comment topComment = new Comment("Bob", "Vrouwen zagen altijd over de weg vragen!", 2, null);
            Comment subCommentL1_1 = new Comment("Alice", "Hah, mannen zijn nooit mans genoeg om de weg te vragen!", 2, topComment);
            Comment subCommentL2_1 = new Comment("Bob", "Mannen hebben een ingebouwde GPS!", 2, subCommentL1_1);
            Comment subCommentL3_1 = new Comment("Alice", "Die is dan wel frequent het noorden kwijt!", 2, subCommentL2_1);
            repo.save(subCommentL3_1);
        };
    }
    */
}
