package se.sdaproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    TopicRepository topicRepository;
    ArticleRepository articleRepository;

    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    //Create a topic
    @PostMapping("topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    //Returns all topics
    @GetMapping("topics")
    public List<Topic> listAllTopics(){
        List <Topic> topics = topicRepository.findAll();
        return topics;
    }

    //Tag a topic into a given article
    @PostMapping("topics/{topicId}/articles/{articleId}")
    public ResponseEntity<Topic> createTag(@PathVariable Long topicId, @PathVariable Long articleId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        topic.getTags().add(article);
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }
}
