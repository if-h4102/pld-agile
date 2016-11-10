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
     * If there was some undone actions, they all will be lost.
     * @param command the command to be executed.
     */
    public void execute(AbstractCommand command) {
        if(!this.undone.isEmpty()) {
          this.undone.clear();
        }
        this.done.push(command).execute();
        this.undone.clear();
        updateUndoableRedoable();
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
        updateUndoableRedoable();
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
        updateUndoableRedoable();
        return true;
    }
    
    /**
     * Update undoable et redoable attributes values checking if done
     * and undone stacks are empties.
     */
    private void updateUndoableRedoable() {
        if (this.undoable.getValue() == false && !this.done.isEmpty()) {
            this.undoable.setValue(true);
        } else if (this.undoable.getValue() == true && this.done.isEmpty()) {
            this.undoable.setValue(false);
        }
        if (this.redoable.getValue() == false && !this.undone.isEmpty()) {
            this.redoable.setValue(true);
        } else if (this.redoable.getValue() == true && this.undone.isEmpty()) {
            this.redoable.setValue(false);
        }
    }

    /**
     * Express the possibility to use the undo() method.
     * @return A SimpleBooleanProperty whose value expresses the 
     * possibility to use the undo() method.
     */
    public SimpleBooleanProperty undoableProperty() {
        return this.undoable;
    }

    /**
     * Express the possibility to use the undo() method.
     * @return A boolean which expresses the 
     * possibility to use the undo() method.
     */
    public boolean getUndoable() {
        return undoableProperty().getValue();
    }

    /**
     * Allows to modify the undoable attribute value which express
     * the possibility to use the undo() method.
     * @param value The new value of the undoable attribute.
     */
    public void setUndoable(boolean value) {
        undoableProperty().setValue(value);
    }
    
    /**
     * Express the possibility to use the redo() method.
     * @return A SimpleBooleanProperty whose value expresses the 
     * possibility to use the redo() method.
     */
    public SimpleBooleanProperty redoableProperty() {
        return this.redoable;
    }

    /**
     * Express the possibility to use the redo() method.
     * @return A boolean which expresses the 
     * possibility to use the redo() method.
     */
    public boolean getRedoable() {
        return redoableProperty().getValue();
    }

    /**
     * Allows to modify the redoable attribute value which express
     * the possibility to use the redo() method.
     * @param value The new value of the redoable attribute
     */
    public void setRedoable(boolean value) {
        redoableProperty().setValue(value);
    }

    /**
     * Express the possibility to use the undo() method  
     * having updated the undoable attribute value.
     * @return A SimpleBooleanProperty whose value expresses the 
     * possibility to use the undo() method.
     */
    public SimpleBooleanProperty isUndoable() {
        this.undoable.setValue(!this.done.isEmpty());
        return this.undoable;
    }
    
    /**
     * Express the possibility to use the redo() method  
     * having updated the redoable attribute value.
     * @return A SimpleBooleanProperty whose value expresses the 
     * possibility to use the redo() method.
     */
    public SimpleBooleanProperty isRedoable() {
        this.redoable.setValue(!this.undone.isEmpty());
        return this.redoable;
    }
}
