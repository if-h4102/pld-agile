package services.command;

import java.util.Stack;
import javafx.beans.property.*;

public class CommandManager {
    /**
     * The stack storing the already done commands.
     */
    private Stack<AbstractCommand> done;

    /**
     * The stack storing the undone commands.
     */
    private Stack<AbstractCommand> undone;

    private SimpleBooleanProperty undoable = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty redoable = new SimpleBooleanProperty(false);

    /**
     * Construct a new CommandManager.
     */
    public CommandManager() {
        this.done = new Stack<>();
        this.undone = new Stack<>();
        this.setUndoable(false);
    }

    /**
     * Execute the given command and store it for an eventual undo operation.
     * @param command the command to be executed.
     */
    public void execute(AbstractCommand command) {
        this.done.push(command).execute();
        // TODO: add mechanism to tell if the command was successful or not ?
    }

    /**
     * Execute the reversed command associated to the last done command
     * and store it for an eventual redo operation.
     */
    public boolean undo() {
        if (this.done.isEmpty()) {
            return false;
        }
        this.undone.push(this.done.pop().getReversed()).execute();
        return true;
    }

    /**
     * Execute the reversed command associated to the last undone command
     * and store it for an eventual undo command.
     */
    public boolean redo() {
        if (this.undone.isEmpty()) {
            return false;
        }
        this.done.push(this.undone.pop().getReversed()).execute();
        return true;
    }

    public SimpleBooleanProperty undoableProperty() {
        return this.undoable;
    }

    public boolean getUndoable() {
        return undoableProperty().getValue();
    }

    public void setUndoable(boolean value) {
        undoableProperty().setValue(value);
    }

    public SimpleBooleanProperty isRedoable() {
        this.redoable.setValue(!this.undone.isEmpty());
        return this.redoable;
    }
}
