package us.heptet.magewars.ui;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Shear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.ui.javafx.DirectedArrow;

/* Created by kay on 3/4/14. */
/**
 * A phase marquee. it slides across the screen, yay!
 */
public class PhaseMarquee extends HBox {
    private static Logger logger = LoggerFactory.getLogger(PhaseMarquee.class);
    private Text text = new Text();
    private DirectedArrow arrow;
    private Button button;
    private String phaseName;

    /**
     * Create a phase maruqee.
     * @param phaseName
     */
    public PhaseMarquee (String phaseName)
    {
        this();
        this.phaseName = phaseName;
        String id = phaseName.replace(' ', '-') + "-next-phase-button";
        logger.debug("Next button id is {}", id);
        button.setId(id);

        text.setText(phaseName);
    }
    PhaseMarquee()
    {
        super(20);
        setPickOnBounds(false);
        setAlignment(Pos.CENTER_LEFT);

        text.setFont(new Font(72));
        text.setTextOrigin(VPos.TOP);
        text.setFill(Color.WHITE);
        text.setDisable(true);
        getChildren().add(text);

        arrow = new DirectedArrow();
        arrow.setFill(Color.WHITE);
        arrow.setOpacity(100);
        arrow.setDisable(true);
        getChildren().add(arrow);

        button = new Button("Next");
        button.setFont(new Font(72));
        button.setLayoutX(100);
        //1.8 button.setPadding(new Insets(0, 30, 0, 30));
        button.getTransforms().add(new Shear(-.35, 0));
        //button.setOpacity(0);
        getChildren().add(button);
    }

    public Text getText() {
        return text;
    }

    public DirectedArrow getArrow() {
        return arrow;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public String toString() {
        return "PhaseMarquee{" +
                "phaseName='" + phaseName + '\'' +
                "} " + super.toString();
    }

}
