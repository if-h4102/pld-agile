package components.timefield;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class TimeField extends HBox {
    private final SimpleIntegerProperty time = new SimpleIntegerProperty(this, "time", 0);
    private final SimpleIntegerProperty hours = new SimpleIntegerProperty(this, "hours", 0);
    private final SimpleIntegerProperty minutes = new SimpleIntegerProperty(this, "minutes", 0);
    private final SimpleIntegerProperty seconds = new SimpleIntegerProperty(this, "seconds", 0);
    private final SimpleStringProperty hoursText = new SimpleStringProperty(this, "hoursText", "0");
    private final SimpleStringProperty minutesText = new SimpleStringProperty(this, "minutesText", "0");
    private final SimpleStringProperty secondsText = new SimpleStringProperty(this, "secondsText", "0");
    @FXML
    protected TextField hoursField;
    @FXML
    protected TextField minutesField;
    @FXML
    protected TextField secondsField;

    public TimeField () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/timefield/TimeField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        hoursField.textProperty().bindBidirectional(this.hoursTextProperty());
        minutesField.textProperty().bindBidirectional(this.minutesTextProperty());
        secondsField.textProperty().bindBidirectional(this.secondsTextProperty());

        hoursField.disableProperty().bind(this.disableProperty());
        minutesField.disableProperty().bind(this.disableProperty());
        secondsField.disableProperty().bind(this.disableProperty());

//        hoursField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
//        minutesField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
//        secondsField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        timeProperty().addListener((observable, oldValue, newValue) -> this.onTimeChange(oldValue.intValue(), newValue.intValue()));
        hoursProperty().addListener((observable, oldValue, newValue) -> this.onHoursChange(oldValue.intValue(), newValue.intValue()));
        minutesProperty().addListener((observable, oldValue, newValue) -> this.onMinutesChange(oldValue.intValue(), newValue.intValue()));
        secondsProperty().addListener((observable, oldValue, newValue) -> this.onSecondsChange(oldValue.intValue(), newValue.intValue()));
    }

    /**
     * @return The observable property for the time.
     */
    public final SimpleIntegerProperty timeProperty() {
        return this.time;
    }

    /**
     * @return The current time.
     */
    public final int getTime() {
        return this.timeProperty().getValue();
    }

    /**
     * @param value The new time.
     */
    public final void setTime(int value) {
        this.timeProperty().setValue(value);
    }

    /**
     * @return The observable property for the hours.
     */
    public final SimpleIntegerProperty hoursProperty() {
        return this.hours;
    }

    /**
     * @return The current hours.
     */
    public final int getHours() {
        return this.hoursProperty().getValue();
    }

    /**
     * @param value The new hours.
     */
    public final void setHours(int value) {
        this.hoursProperty().setValue(value);
    }

    public final SimpleIntegerProperty minutesProperty() {
        return this.minutes;
    }

    public final int getMinutes() {
        return this.minutesProperty().getValue();
    }

    public final void setMinutes(int value) {
        this.minutesProperty().setValue(value);
    }

    public final SimpleIntegerProperty secondsProperty() {
        return this.seconds;
    }

    public final int getSeconds() {
        return this.secondsProperty().getValue();
    }

    public final void setSeconds(int value) {
        this.secondsProperty().setValue(value);
    }

    public final SimpleStringProperty hoursTextProperty() {
        return this.hoursText;
    }

    public final String getHoursText() {
        return this.hoursTextProperty().getValue();
    }

    public final void setHoursText(String value) {
        this.hoursTextProperty().setValue(value);
    }

    public final SimpleStringProperty minutesTextProperty() {
        return this.minutesText;
    }

    public final String getMinutesText() {
        return this.minutesTextProperty().getValue();
    }

    public final void setMinutesText(String value) {
        this.minutesTextProperty().setValue(value);
    }

    public final SimpleStringProperty secondsTextProperty() {
        return this.secondsText;
    }

    public final String getSecondsText() {
        return this.secondsTextProperty().getValue();
    }

    public final void setSecondsText(String value) {
        this.secondsTextProperty().setValue(value);
    }

    protected void onTimeChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        TimeStructure ts = new TimeStructure(newValue);
        if (ts.hours != this.getHours()) {
            this.setHours(ts.hours);
        }
        if (ts.minutes != this.getMinutes()) {
            this.setMinutes(ts.minutes);
        }
        if (ts.seconds != this.getSeconds()) {
            this.setSeconds(ts.seconds);
        }
    }

    protected void onHoursChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String hoursText = String.valueOf(newValue);
        if (!hoursText.equals(this.getHoursText())) {
            this.setHoursText(hoursText);
        }
        // TODO: update time
    }

    protected void onMinutesChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String minutesText = String.valueOf(newValue);
        if (!minutesText.equals(this.getMinutesText())) {
            this.setMinutesText(minutesText);
        }
        // TODO: update time
    }

    protected void onSecondsChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String secondsText = String.valueOf(newValue);
        if (!secondsText.equals(this.getSecondsText())) {
            this.setSecondsText(secondsText);
        }
        // TODO: update time
    }

    // TODO: onHoursTextChange
    // TODO: onMinutesTextChange
    // TODO: onSecondsTextChange

    public class TimeStructure {
        public final int time;
        public final int hours;
        public final int minutes;
        public final int seconds;
        TimeStructure(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            this.time = hours * 3600 + minutes * 60 + seconds;
        }
        TimeStructure(int time) {
            this.time = time;
            this.hours = time / 3600;
            time %= 3600;
            this.minutes = time / 60;
            time %= 60;
            this.seconds = time;
        }
    }
}
