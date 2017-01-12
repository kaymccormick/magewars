package us.heptet.magewars.webapp.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/* Created by kay on 5/14/2014. */
/**
 * a spring mvc controller for the main host page for the gwt app.
 */
@Controller
public class WebAppController {
    private static final String SMALL_PAGE = "smallPage";

    /**
     * return a small page for authentication purposes.
     * @return
     */
    @RequestMapping({"/smallPage"})
    public String smallPage()
    {
        return SMALL_PAGE;
    }

    /**
     * The view for the webapp host page.
     * @return
     */
    @RequestMapping({"/", "/WebApp.html"})
    public ModelAndView webAppHostPage()
    {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        boolean anonymous = false;
        for(Object o:authentication.getAuthorities())

        {
            if(o.toString().compareTo("ROLE_ANONYMOUS") == 0)
            {
                anonymous = true;
            }
        }

        ModelAndView modelAndView = new ModelAndView("WebApp");
        modelAndView.addObject("test", "poop");
        modelAndView.addObject("authentication", authentication);
        modelAndView.addObject("anonymous", anonymous);
        return modelAndView;
    }
}
