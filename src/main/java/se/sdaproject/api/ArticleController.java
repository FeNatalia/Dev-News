package se.sdaproject.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.model.Article;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.service.ArticleService;
import se.sdaproject.api.exception.ResourceNotFoundException;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    ArticleRepository articleRepository;
    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleService articleService){
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    //Returns all articles
    @GetMapping
    public List<Article> listAllArticles() {
        List <Article> articles = articleRepository.findAll();
        return articles;
    }

    //Returns a specific article based on the provided id
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id){
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }

    //Updates the given article
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle){
        Article article = articleService.updateArticle(id, updatedArticle);
        return ResponseEntity.ok(article);
    }

    //Deletes the given article
    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articleRepository.delete(article);
        return ResponseEntity.ok(article);
    }

    //Creates a new article
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

}

