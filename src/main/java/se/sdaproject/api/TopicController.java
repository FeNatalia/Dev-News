package se.sdaproject.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.api.exception.ResourceNotFoundException;
import se.sdaproject.model.Article;
import se.sdaproject.model.Topic;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.repository.TopicRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicController {

    TopicRepository topicRepository;
    ArticleRepository articleRepository;

    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    //Creates a topic
    @PostMapping("topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    //Deletes the given topic
    @DeleteMapping("topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        topicRepository.delete(topic);
    }

    //Updates the given topic
    @PutMapping("topics/{topicId}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long topicId, @Valid @RequestBody Topic updatedTopic) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        updatedTopic.setId(topicId);
        topicRepository.save(updatedTopic);
        return ResponseEntity.ok(updatedTopic);
    }

    //Returns all topics
    @GetMapping("topics")
    public List<Topic> listAllTopics(){
        List <Topic> topics = topicRepository.findAll();
        return topics;
    }

    //Associates the given topic by topicId with the article given by articleId.
    @PostMapping("topics/{topicId}/articles/{articleId}")
    public ResponseEntity<Topic> createTag(@PathVariable Long topicId, @PathVariable Long articleId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        topic.getTags().add(article);
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    //Returns all articles associated with the topic given by topicId
    @GetMapping("topics/{topicId}/articles")
    public ResponseEntity<List<Article>> listTopicArticles(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        List<Article> tags = topic.getTags();
        return ResponseEntity.ok(tags);
    }

    //Deletes the association of a topic for the given article. The topic and article themselves remain.
    @DeleteMapping("articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long topicId, @PathVariable Long articleId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        if (topic.getTags().contains(article)) {
            topic.getTags().remove(article);
            topicRepository.save(topic);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
