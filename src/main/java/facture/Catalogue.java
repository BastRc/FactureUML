package facture;

import java.util.HashMap;
import java.util.Map;

public class Catalogue {
    
    Map<String, Article> articles = new HashMap<>();
    
    public void addArticle(Article article) {
        articles.put(article.getCode(), article);
    }
    
    public Article findByCode(String code) {
        return articles.get(code);
    }
    
}
