package components.timefield;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The TimeField component is a small component able to display and edit
 * a time of the form `HH:mm:ss`.
 */
public class TimeField extends HBox {
    /**
     * The absolute value of the time (in seconds, 0 is midnight).
     */
    private final SimpleIntegerProperty time = new SimpleIntegerProperty(this, "time", 0);

    /**
     * The number of hours in the current time.
     */
    private final SimpleIntegerProperty hours = new SimpleIntegerProperty(this, "hours", 0);

    /**
     * The number of minutes in the current time.
     */
    private final SimpleIntegerProperty minutes = new SimpleIntegerProperty(this, "minutes", 0);

    /**
     * The number of seconds in the current time.
     */
    private final SimpleIntegerProperty seconds = new SimpleIntegerProperty(this, "seconds", 0);

    /**
     * The string value in the TextField for hours.
     */
    private final SimpleStringProperty hoursText = new SimpleStringProperty(this, "hoursText", "0");

    /**
     * The string value in the TextField for minutes.
     */
    private final SimpleStringProperty minutesText = new SimpleStringProperty(this, "minutesText", "0");

    /**
     * The string value in the TextField for seconds.
     */
    private final SimpleStringProperty secondsText = new SimpleStringProperty(this, "secondsText", "0");

    /**
     * Reference to the TextField for hours.
     */
    @FXML
    protected TextField hoursField;

    /**
     * References to the TextField for minutes.
     */
    @FXML
    protected TextField minutesField;

    /**
     * Reference to the TextField for seconds.
     */
    @FXML
    protected TextField secondsField;

    public TimeField() {
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

        this.timeProperty().addListener((observable, oldValue, newValue) -> this.onTimeChange(oldValue.intValue(), newValue.intValue()));
        this.hoursProperty().addListener((observable, oldValue, newValue) -> this.onHoursChange(oldValue.intValue(), newValue.intValue()));
        this.minutesProperty().addListener((observable, oldValue, newValue) -> this.onMinutesChange(oldValue.intValue(), newValue.intValue()));
        this.secondsProperty().addListener((observable, oldValue, newValue) -> this.onSecondsChange(oldValue.intValue(), newValue.intValue()));
        this.hoursTextProperty().addListener((observable, oldValue, newValue) -> this.onHoursTextChange(oldValue, newValue));
        this.minutesTextProperty().addListener((observable, oldValue, newValue) -> this.onMinutesTextChange(oldValue, newValue));
        this.secondsTextProperty().addListener((observable, oldValue, newValue) -> this.onSecondsTextChange(oldValue, newValue));
        this.disableProperty().addListener(this::onDisableChange);
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

    /**
     * @return The observable property for the minutes.
     */
    public final SimpleIntegerProperty minutesProperty() {
        return this.minutes;
    }

    /**
     * @return The current number of minutes
     */
    public final int getMinutes() {
        return this.minutesProperty().getValue();
    }

    /**
     * Set the current number of minutes
     *
     * @param value The new number of minutes
     */
    public final void setMinutes(int value) {
        this.minutesProperty().setValue(value);
    }

    /**
     * @return The observable property for the seconds.
     */
    public final SimpleIntegerProperty secondsProperty() {
        return this.seconds;
    }

    /**
     * @return The current number of seconds
     */
    public final int getSeconds() {
        return this.secondsProperty().getValue();
    }

    /**
     * Set the current number of seconds
     *
     * @param value The new number of seconds
     */
    public final void setSeconds(int value) {
        this.secondsProperty().setValue(value);
    }

    /**
     * @return The observable property for the hours string.
     */
    public final SimpleStringProperty hoursTextProperty() {
        return this.hoursText;
    }

    /**
     * @return the current text in the hours TextField.
     */
    @NotNull
    public final String getHoursText() {
        return this.hoursTextProperty().getValue();
    }

    /**
     * Set the content of the hours TextField.
     *
     * @param value The new content of the hours TextField.
     */
    public final void setHoursText(String value) {
        this.hoursTextProperty().setValue(value);
    }

    /**
     * @return The observable property for the minutes string.
     */
    public final SimpleStringProperty minutesTextProperty() {
        return this.minutesText;
    }

    /**
     * @return the current text in the minutes TextField.
     */
    @NotNull
    public final String getMinutesText() {
        return this.minutesTextProperty().getValue();
    }

    /**
     * Set the content of the minutes TextField.
     *
     * @param value The new content of the minutes TextField.
     */
    public final void setMinutesText(String value) {
        this.minutesTextProperty().setValue(value);
    }

    /**
     * @return The observable property for the seconds string.
     */
    public final SimpleStringProperty secondsTextProperty() {
        return this.secondsText;
    }

    /**
     * @return the current text in the seconds TextField.
     */
    @NotNull
    public final String getSecondsText() {
        return this.secondsTextProperty().getValue();
    }

    /**
     * Set the content of the seconds TextField.
     *
     * @param value The new content of the seconds TextField.
     */
    public final void setSecondsText(String value) {
        this.secondsTextProperty().setValue(value);
    }

    /**
     * Handle the change of the time by updating the hours, minutes and seconds.
     *
     * @param oldValue Previous value of time
     * @param newValue New value of time
     */
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

    /**
     * Handle the change of the hours by updating the time and the text of
     * the hours TextField
     *
     * @param oldValue Previous value of hours
     * @param newValue New value of hours
     */
    protected void onHoursChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String hoursText = String.valueOf(newValue);
        if (!hoursText.equals(this.getHoursText())) {
            this.setHoursText(hoursText);
        }
        TimeStructure curTime = new TimeStructure(this.getTime());
        if (curTime.hours != newValue) {
            setTime(new TimeStructure(newValue, curTime.minutes, curTime.seconds).time);
        }
    }

    /**
     * Handle the change of the minutes by updating the time and the text of
     * the minutes TextField
     *
     * @param oldValue Previous value of minutes
     * @param newValue New value of minutes
     */
    protected void onMinutesChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String minutesText = String.valueOf(newValue);
        if (!minutesText.equals(this.getMinutesText())) {
            this.setMinutesText(minutesText);
        }
        TimeStructure curTime = new TimeStructure(this.getTime());
        if (curTime.minutes != newValue) {
            setTime(new TimeStructure(curTime.hours, newValue, curTime.seconds).time);
        }
    }

    /**
     * Handle the change of the seconds by updating the time and the text of
     * the seconds TextField
     *
     * @param oldValue Previous value of seconds
     * @param newValue New value of seconds
     */
    protected void onSecondsChange(int oldValue, int newValue) {
        if (newValue == oldValue) {
            return;
        }
        String secondsText = String.valueOf(newValue);
        if (!secondsText.equals(this.getSecondsText())) {
            this.setSecondsText(secondsText);
        }
        TimeStructure curTime = new TimeStructure(this.getTime());
        if (curTime.seconds != newValue) {
            setTime(new TimeStructure(curTime.hours, curTime.minutes, newValue).time);
        }
    }

    /**
     * Handle the change of content of the hours TextField by updating
     * the hours.
     *
     * @param oldValue Previous value of the content of the hours TextField
     * @param newValue New value of the content of the hours TextField
     */
    protected void onHoursTextChange(String oldValue, String newValue) {
        if (newValue.equals(oldValue)) {
            return;
        }
        int hours;
        try {
            hours = Integer.parseInt(newValue, 10);
        } catch (NumberFormatException e) {
            hours = 0;
        }
        this.setHours(hours);
    }

    /**
     * Handle the change of content of the minutes TextField by updating
     * the minutes.
     *
     * @param oldValue Previous value of the content of the minutes TextField
     * @param newValue New value of the content of the minutes TextField
     */
    protected void onMinutesTextChange(String oldValue, String newValue) {
        if (newValue.equals(oldValue)) {
            return;
        }
        int minutes;
        try {
            minutes = Integer.parseInt(newValue, 10);
        } catch (NumberFormatException e) {
            minutes = 0;
        }
        this.setMinutes(minutes);
    }

    /**
     * Handle the change of content of the seconds TextField by updating
     * the seconds.
     *
     * @param oldValue Previous value of the content of the seconds TextField
     * @param newValue New value of the content of the seconds TextField
     */
    protected void onSecondsTextChange(String oldValue, String newValue) {
        if (newValue.equals(oldValue)) {
            return;
        }
        int seconds;
        try {
            seconds = Integer.parseInt(newValue, 10);
        } catch (NumberFormatException e) {
            seconds = 0;
        }
        this.setSeconds(seconds);
    }

    /**
     * Synchronize the style of the component according to the "disable"
     * attribute of the TimeField.
     *
     * @param observable The observable value of the disable attribute.
     * @param oldValue Previous value of "disable"
     * @param newValue New value of "disable"
     */
    protected void onDisableChange(ObservableValue<? extends Boolean> observable, boolean oldValue, boolean newValue) {
        if (newValue) {
            this.getStyleClass().add("ReadOnly");
        } else {
            this.getStyleClass().remove("ReadOnly");
        }
    }

    /**
     * Represents a parsed time.
     */
    public class TimeStructure {
        /**
         * Timestamp: seconds since midnight
         */
        public final int time;

        /**
         * Hours since midnight
         */
        public final int hours;

        /**
         * Minutes of the current hour
         */
        public final int minutes;

        /**
         * Seconds of the current minute
         */
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
