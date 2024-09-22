package streams.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import streams.exception.StreamsException;
import streams.task.DeadlineTask;
import streams.task.EventTask;
import streams.task.Task;
import streams.task.TaskList;
import streams.util.Storage;
import streams.util.Ui;

/**
 * Represents a command to list tasks on a specific date.
 */
public class ListDateCommand extends Command {
    private LocalDate date;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a ListDateCommand with the specified date.
     *
     * @param dateString The date to list tasks for.
     * @throws StreamsException If there's an error executing the command.
     */
    public ListDateCommand(String dateString) throws StreamsException {
        try {
            this.date = LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new StreamsException("invalid date format. Please use yyyy-MM-dd");
        }
    }

    /**
     * Executes the list date command, showing tasks on the specified date.
     *
     * @param tasks The task list to search in.
     * @param ui The user interface to display the results.
     * @param storage The storage (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task instanceof DeadlineTask) {
                if (((DeadlineTask) task).getBy().toLocalDate().equals(date)) {
                    tasksOnDate.add(task);
                }
            } else if (task instanceof EventTask) {
                EventTask eventTask = (EventTask) task;
                if (eventTask.getFrom().toLocalDate().equals(date) || eventTask.getTo().toLocalDate().equals(date)) {
                    tasksOnDate.add(task);
                }
            }
        }

        if (tasksOnDate.isEmpty()) {
            ui.showMessage("no tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
        } else {
            ui.showMessage("tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
            for (int i = 0; i < tasksOnDate.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasksOnDate.get(i));
            }
        }
    }
}
