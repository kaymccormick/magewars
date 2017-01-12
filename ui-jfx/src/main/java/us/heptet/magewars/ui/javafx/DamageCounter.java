package us.heptet.magewars.ui.javafx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.VPos;
//1.8 import javafx.scene.layout.Background;
//1.8 import javafx.scene.layout.BackgroundFill;
//1.8 import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import java.util.concurrent.Callable;

/* Created by kay on 2/24/14. */
/**
 * Damage counter for creatures.
 */
public class DamageCounter extends Region {
    private SimpleIntegerProperty damageAmount = new SimpleIntegerProperty();

    /**
     * Create a damage counter.
     */
    public DamageCounter() {
        Circle circle = new Circle();
        circle.radiusProperty().bind(Bindings.min(widthProperty(), heightProperty()));
        circle.layoutXProperty().bind(widthProperty().divide(2));
        circle.layoutYProperty().bind(heightProperty().divide(2));
        circle.setFill(Color.BLACK);
        setClip(circle);
        //1.8 setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(1, true), new Insets(0))));
        final Text amountText = new Text();

        amountText.setTextOrigin(VPos.CENTER);
        amountText.setBoundsType(TextBoundsType.VISUAL);
        amountText.setFont(new Font(42));
        amountText.setTextAlignment(TextAlignment.CENTER);
        amountText.textProperty().bind(Bindings.convert(damageAmount));
        amountText.layoutXProperty().bind(widthProperty().subtract(
                Bindings.createDoubleBinding(new Callable<Double>() {
                    @Override
                    public Double call() throws Exception {
                        return amountText.getLayoutBounds().getWidth();
                    }
                },                        amountText.layoutBoundsProperty()))

                .divide(2));
        amountText.layoutYProperty().bind(heightProperty().divide(2));
        //.subtract(
          //      Bindings.createDoubleBinding(() -> amountText.getLayoutBounds().getHeight(),
            //            amountText.layoutBoundsProperty())));
        amountText.setFill(Color.WHITE);
        getChildren().add(amountText);
        setPrefWidth(60);
        setPrefHeight(60);
    }

    public int getDamageAmount() {
        return damageAmount.get();
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount.set(damageAmount);
    }
}
