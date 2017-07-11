package planner.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DialogEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import planner.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The controller for the event allocator program.
 */
public class EventAllocatorController {

    // the model of the event allocator
    private EventAllocatorModel model;
    // the view of the event allocator
    private EventAllocatorView view;
    // whether the user is currently removing an event from the allocations
    private boolean removing = false;

    /*
     * invariant:
     *
     * model != null && view != null
     */

    /**
     * Initialises the controller for the event allocator program.
     * 
     * @param model
     *            the model of the event allocator
     * @param view
     *            the view of the event allocator
     */
    public EventAllocatorController(EventAllocatorModel model,
            EventAllocatorView view) {
        // store the model and the view
        this.model = model;
        this.view = view;

        // add the appropriate handles to the allocator view
        // handle when the Load Venue file menu option is clicked
        this.view.addLoadVenueHandler(new clickLoadVenues());
        // handle when the New Event button or menu items are clicked
        this.view.addNewEventHandler(new clickNewEvent());
        // handle when the Remove Event button or menu items are clicked
        this.view.addRemoveEventHandler(new clickRemoveEvent());
        // handle when the Exit menu item is clicked
        this.view.addExitHandler(new clickExit());

        try {
            // attempt to load the venues from the venues.txt file
            this.model.loadVenues("venues.txt");
        } catch (IOException | FormatException exception){
            // if there is an error loading the venue display the appropriate
            // error message as a dialog box
            view.showErrorDialog(exception, "Failed to load venues.txt",
                    new closeErrorDialog());
        }

        // update the view to reflect the added changes
        view.update();
    }

    /**
     * Event handler for loading the venues from a file chooser dialog.
     */
    private class clickLoadVenues implements EventHandler<ActionEvent> {
        @Override
        public void handle(final ActionEvent e){
            // open and display the file choosing dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Venues File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null){
                try {
                    // when a file has been selected try to reload the allocator
                    model.loadVenues(file.getAbsolutePath());
                    view.update();
                } catch (IOException | FormatException exception){
                    view.showErrorDialog(exception, "Failed to load "
                            + file.getName(), new closeErrorDialog());
                }
            }
        }
    }

    /**
     * Event handler for opening the new event dialog box when add event buttons
     * are pressed.
     */
    private class clickNewEvent implements EventHandler<ActionEvent> {
        @Override
        public void handle(final ActionEvent e){
            // open the new event dialog with the addEvent callback
            view.showNewEventDialog(new addEvent());
        }
    }

    /**
     * Event handler for starting the removal of an event by pressing the
     * remove event buttons.
     */
    private class clickRemoveEvent implements EventHandler<ActionEvent> {
        @Override
        public void handle(final ActionEvent e){
            removing = !removing;
        }
    }

    /**
     * Event handler for when the exit button is clicked.
     */
    private class clickExit implements EventHandler<ActionEvent> {
        @Override
        public void handle(final ActionEvent e){
            Platform.exit();
        }
    }

    /**
     * Event handler for when an event item is clicked, removes the event if
     * possible.
     */
    private class clickEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent e){
            // ensure that the user wants to remove the clicked event
            if (!removing) return;

            // retrieve useful information from the mouse event
            StackPane stack = (StackPane) e.getSource();
            Event event = (Event) stack.getUserData();

            // remove the event and update the display to reflect the removal
            model.removeEvent(event);
            view.update();
            view.addEventClickHandler(new clickEvent());

            removing = false;
        }
    }

    /**
     * Callback class which handles when an event has been entered into the
     * add event dialog box.
     */
    private class addEvent implements Callback<List<String>, Boolean> {
        /**
         * Handles the calling of the callback class.
         *
         * @param values A list of 3 strings corresponding to the event name,
         *               event size and venue name or null.
         * @return true iff the event is successfully added
         */
        @Override
        public Boolean call(List<String> values){
            if (values == null){
                // when one or more of the required fields was not entered open
                // an error dialog describing the error.
                view.showErrorDialog(new Exception("At least one required " +
                        "field was not entered"), "Failed to add event " +
                        "to allocations", null);
                return false;
            }
            try {
                // add the event to the model based on the returned values
                model.addEvent(values.get(0), values.get(1), values.get(2));

                // update the display to reflect the new values
                view.update();
                view.addEventClickHandler(new clickEvent());

            } catch (InvalidTrafficException | IllegalArgumentException e) {
                view.showErrorDialog(e, "Failed to add event to allocations",
                        null);
                return false;
            }
            return true;
        }
    }

    /**
     * Event handler for when the error dialog is closed which closes the
     * program.
     */
    private class closeErrorDialog implements EventHandler<DialogEvent> {
        @Override
        public void handle(final DialogEvent e){
            Platform.exit();
        }
    }

}
