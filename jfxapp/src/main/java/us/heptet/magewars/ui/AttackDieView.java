package us.heptet.magewars.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import us.heptet.magewars.game.attack.AttackDie;

/* Created by kay on 2/24/14. */
/**
 * View for an attack die.
 */
public class AttackDieView extends Region {
    SimpleObjectProperty<AttackDie> attackDie = new SimpleObjectProperty<>();
    private static final boolean JFX18 = false;

    /***
     * Create the view.
     * @param attackDie
     */
    public AttackDieView(AttackDie attackDie) {
        setAttackDie(attackDie);
        setPrefHeight(50);
        setPrefWidth(50);
        Rectangle rect = new Rectangle();
        rect.widthProperty().bind(widthProperty());
        rect.heightProperty().bind(heightProperty());
        setClip(rect);
        if(JFX18)
        {
            setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(.15, true), new Insets(0))));
        }
        Ellipse ellipse = new Ellipse();
        ellipse.layoutXProperty().bind(widthProperty().divide(2));
        ellipse.layoutYProperty().bind(heightProperty().divide(2));

        ellipse.radiusXProperty().bind(widthProperty().divide(2).subtract(5));
        ellipse.radiusYProperty().bind(heightProperty().divide(2).subtract(5));
        ellipse.setFill(Color.WHITE);

        //ellipse.visibleProperty().bind(attackDie.criticalProperty().and(attackDie.damageProperty().greaterThan(0)));
        getChildren().add(ellipse);
        Text faceText = new Text(15, 38, "1");
        faceText.setFill(Color.WHITE);
        //faceText.fillProperty().bind(Bindings.when(attackDie.criticalProperty()).then(Color.BLACK).otherwise(Color.WHITE));
        //faceText.textProperty().bind(Bindings.createStringBinding(() -> getAttackDie().getDamage() > 0 ? String.format("%d", getAttackDie().getDamage()) : "", getAttackDie().damageProperty()));
        faceText.setFont(new Font(36));

        getChildren().add(faceText);
        if(JFX18) {
            //setBorder(new Border(new BorderStroke(Color.BLACK)));
        }
    }

    public AttackDie getAttackDie() {
        return attackDie.get();
    }

    public void setAttackDie(AttackDie attackDie) {
        this.attackDie.set(attackDie);
    }
}
