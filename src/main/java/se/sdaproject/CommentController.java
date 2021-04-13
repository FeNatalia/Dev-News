package se.sdaproject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CommentController {

    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    //Creates a new comment on article given by articleId
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long articleId, @RequestBody Comment comment){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        comment.setArticle(article);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    //Updates the given comment
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Valid @RequestBody Comment updatedComment) {
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedComment.setId(id);
        commentRepository.save(updatedComment);
        return ResponseEntity.ok(updatedComment);
    }

    //Deletes the given comment
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentRepository.delete(comment);
        return ResponseEntity.ok(comment);
    }

    //Returns all comments on article given by articleId
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<Article> listAllComments(@PathVariable Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }



}
