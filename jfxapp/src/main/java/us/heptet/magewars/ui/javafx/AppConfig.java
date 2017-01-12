package us.heptet.magewars.ui.javafx;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.ArenaImpl;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.CreatureActionView;
import us.heptet.magewars.ui.controller.GameController;
import us.heptet.magewars.ui.controller.JfxGameController;
import us.heptet.magewars.ui.controller.PlanSpellsController;
import us.heptet.magewars.ui.factory.JavaFxUiFactory;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.generic.ArenaViewImpl;
import us.heptet.magewars.ui.view.PlanSpellsView;


/* Created by kay on 3/24/14. */
/**
 * Spring Configuration class for the JFX client.
 */
@Configuration
//@EnableSpringConfigured
//
// @EnableLoadTimeWeaving
//@Import(UiConfig.class)
@ImportResource({"classpath:gameconfig.xml"})
//@ComponentScan(//excludeFilters= {@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value=UiFactory.class)},
//        basePackages = {"us.heptet.magewars.game.controller"
                //        "us.heptet.magewars.ui", "us.heptet.magewars.ui.javafx","us.heptet.magewars.ui.controller"
//        })
@PropertySource("classpath:application.properties")
public class AppConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    @Lazy
    GameController
    gameController()
    {
        return new JfxGameController(jfxGameView(), applicationContext.getBean(GameControl.class), planSpellsController());
    }

    @Bean
    @Lazy
    PlanSpellsController
    planSpellsController()
    {
        return new PlanSpellsController(planSpellsView());
    }

    @Bean
    @Lazy
    PlanSpellsView
    planSpellsView()
    {
        return new SpellBookViewer(uiFactory());
    }

    @Lazy
    @Bean
    JfxGameView jfxGameView()
    {
        return new JfxGameView(arenaView());
    }

    @Bean
    ViewSupplier viewSupplier()
    {
        return new ViewManager(cardViewFactory(), creatureActionView());
    }

    @Bean
    UiFactory uiFactory()
    {
        return new JavaFxUiFactory(cardImageManager(), viewSupplier(), cardViewFactory());
    }

    @Bean
    CardViewFactory cardViewFactory()
    {
        return new CardViewFactoryImpl2(cardImageManager());
    }

    @Bean

    CardImageManager cardImageManager()
    {
        return new CardImageManager(cardSet());
    }

    @Bean
    CardSet cardSet()
    {
        return new BaseCardSet();
    }

    @Bean
    CreatureActionView creatureActionView()
    {
        return new CreatureActionViewImpl();
    }

    @Bean
    ArenaView arenaView()
    {
        return new ArenaViewImpl(uiFactory(), arena());
    }

    @Bean
    Arena arena()
    {
        return new ArenaImpl();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
