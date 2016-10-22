package services.command;

public abstract class AbstractCommand implements Command {

    /**
     * Execute the current command.
     */
    @Override
    public abstract void execute();

    /**
     * Whether or not the current command is reversible.
     * @return true only if the current command is reversible.
     * Return false otherwise.
     */
    public abstract boolean isReversible();

    /**
     * Create the opposite command of the current command,
     * i.e. the one which cancel the current one.
     * @return the newly create opposite command.
     */
    public abstract AbstractCommand getReversed();
}
