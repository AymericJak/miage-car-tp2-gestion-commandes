package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/store/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/store/article/index");
        modelAndView.addObject("articles", articleService.findAll());
        return modelAndView;
    }
}
