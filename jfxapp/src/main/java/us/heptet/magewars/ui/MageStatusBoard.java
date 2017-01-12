package us.heptet.magewars.ui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/* Created by kay on 3/10/14. */

/**
 * A Mage status board
 */
public class MageStatusBoard extends GridPane {
    private Color channelingColor = Color.rgb(0x1e, 0x23, 0x5a); // 1e235a
    private Color manaSupplyColor = Color.rgb(0x5c, 0x2f, 0x80);// 5c2f80
    private int channelingStart = 5;
    private int channelingEnd = 20;
    private int channelingCols = 4;
    private int manaStart = 0;
    private int manaEnd = 34;
    private int manaCols = 5;
    private int damageStart = 0;
    private int damageEnd = 41;
    private int damageCols = 6;
    private double cellWidth = 50;
    private double cellHeight = 50;
    private SimpleIntegerProperty channeling = new SimpleIntegerProperty();
    private SimpleIntegerProperty manaSupply = new SimpleIntegerProperty();
    private SimpleIntegerProperty damage = new SimpleIntegerProperty();
    private List<Label> channelLabelList = new ArrayList<>(channelingEnd + 1);
    private List<Label> manaSupplyLabelList = new ArrayList<>(manaEnd + 1);
    private List<Label> damageLabelList = new ArrayList<>(damageEnd + 1);


    MageStatusBoard() {
        int channelCol = 0;
        int channelRow = 1;

        manaSupplyProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue != null && oldValue.intValue() <= manaEnd) {
                    Label old = manaSupplyLabelList.get(oldValue.intValue());
                    if (old != null) {
                        old.getStyleClass().remove("mana-supply-current-value");
                    }
                }

                if (newValue != null && newValue.intValue() <= manaEnd) {
                    Label newLabel = manaSupplyLabelList.get(newValue.intValue());
                    if (newLabel != null) {
                        newLabel.getStyleClass().add("mana-supply-current-value");
                    }
                }
            }

        });

        Label channelLabel = new Label("Channeling");
        channelLabel.setId("channeling-label");
        String mageStatusLabelClass = "mage-status-label";
        channelLabel.getStyleClass().add(mageStatusLabelClass);
        MageStatusBoard.setConstraints(channelLabel, 0, 0, channelingCols, 1, HPos.CENTER, VPos.CENTER);

        getChildren().add(channelLabel);

        Label manaLabel = new Label("Mana Supply");
        manaLabel.setId("mana-supply-label");
        manaLabel.getStyleClass().add(mageStatusLabelClass);
        MageStatusBoard.setConstraints(manaLabel, channelingCols, 0, manaCols, 1, HPos.CENTER, VPos.CENTER);

        getChildren().add(manaLabel);

        Label damageLabel = new Label("Life / Damage");
        damageLabel.setId("damage-label");
        damageLabel.getStyleClass().add(mageStatusLabelClass);

        MageStatusBoard.setConstraints(damageLabel, channelingCols + manaCols, 0, damageCols, 1, HPos.CENTER, VPos.CENTER);

        getChildren().add(damageLabel);

        for (
                int i = 0;
                i < channelingStart; i++)

        {
            channelLabelList.add(null);
        }
        for (
                int i = channelingStart;
                i <= channelingEnd; i++)

        {
            Label t = new Label(Integer.toString(i));
            t.setPrefWidth(cellWidth);
            t.setPrefHeight(cellHeight);
            t.getStyleClass().add("channeling-status-value");
            channelLabelList.add(i, t);
            MageStatusBoard.setConstraints(t, channelCol, channelRow, 1, 1, HPos.CENTER, VPos.CENTER);
            getChildren().add(t);
            channelCol++;
            if (channelCol == channelingCols) {
                channelCol = 0;
                channelRow++;
            }
        }

        int manaStartCol = channelingCols;
        int manaCol = manaStartCol;
        int manaRow = 1;
        for (
                int i = 0;
                i < manaStart; i++)

        {
            manaSupplyLabelList.add(null);
        }
        for (
                int i = manaStart;
                i <= manaEnd; i++)

        {
            Label t = new Label(Integer.toString(i));
            t.setPrefWidth(cellWidth);
            t.setPrefHeight(cellHeight);
            t.getStyleClass().add("mana-supply-status-value");
            manaSupplyLabelList.add(i, t);
            MageStatusBoard.setConstraints(t, manaCol, manaRow, 1, 1, HPos.CENTER, VPos.CENTER);
            getChildren().add(t);
            manaCol++;
            if ((manaCol - manaStartCol) == manaCols) {
                manaCol = manaStartCol;
                manaRow++;
            }
        }

        int damageStartCol = channelingCols + manaCols;
        int damageCol = damageStartCol;
        int damageRow = 1;

        for (
                int i = 0;
                i < damageStart; i++)

        {
            damageLabelList.add(null);
        }

        for (
                int i = damageStart;
                i <= damageEnd; i++)

        {
            Label t = new Label(Integer.toString(i));
            t.setPrefWidth(cellWidth);
            t.setPrefHeight(cellHeight);
            t.getStyleClass().add("damage-status-value");
            damageLabelList.add(i, t);
            MageStatusBoard.setConstraints(t, damageCol, damageRow, 1, 1, HPos.CENTER, VPos.CENTER);
            getChildren().add(t);
            damageCol++;
            if ((damageCol - damageStartCol) == damageCols) {
                damageCol = damageStartCol;
                damageRow++;
            }
        }

    }

    public int getChanneling() {
        return channeling.get();
    }

    public void setChanneling(int channeling) {
        this.channeling.set(channeling);
    }

    public int getManaSupply() {
        return manaSupply.get();
    }

    private SimpleIntegerProperty manaSupplyProperty() {
        return manaSupply;
    }

    public void setManaSupply(int manaSupply) {
        this.manaSupply.set(manaSupply);
    }

    public int getDamage() {
        return damage.get();
    }

    public void setDamage(int damage) {
        this.damage.set(damage);
    }
}
