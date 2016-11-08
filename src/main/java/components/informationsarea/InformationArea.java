package components.informationsarea;

import javafx.scene.control.TextArea;

public class InformationArea extends TextArea {
    private static final String DEFAULT_TEXT = "Click somewhere !";

    public InformationArea() {
        super(InformationArea.DEFAULT_TEXT);
    }
}
