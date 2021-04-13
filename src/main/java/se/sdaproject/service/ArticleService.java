package se.sdaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sdaproject.repository.ArticleRepository;
import se.sdaproject.api.exception.ResourceNotFoundException;
import se.sdaproject.model.Article;

@Service
public class ArticleService {

    ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article updateArticle(Long id, Article updatedArticle) {
        articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        Article article = articleRepository.save(updatedArticle);
        return article;
    }
}
