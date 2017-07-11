package planner.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import planner.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.util.*;

/**
 * The view for the event allocator program.
 */
public class EventAllocatorView {

    // the model of the event allocator
    private EventAllocatorModel model;
    // the root border pane window for the allocator
    private BorderPane root;
    // the scene of the allocator view
    private Scene scene;

    // a list of the menu items added to the view
    private MenuItem[] menuItems;
    // a list of the buttons added to the view
    private Button[] buttons;

    /*
     * invariant:
     *
     * root != null && scene != null && model != null
     */

    /**
     * Initialises the view for the event allocator program.
     * 
     * @param model
     *            the model of the event allocator
     */
    public EventAllocatorView(EventAllocatorModel model) {
        this.model = model;

        // setup the root view node and the scene
        root = new BorderPane();
        scene = new Scene(root);

        // initialize the menu and button item lists
        menuItems = new MenuItem[4];
        buttons = new Button[2];

        // load the menu and set it to the top of the view
        root.setTop(loadMenu());
        // load the buttons and set it to the left side of the view
        root.setLeft(loadButtons());
    }

    /**
     * Update the display of the traffic, events and venues in the screen.
     */
    public void update(){
        addTraffic();
        addEvents();
        addVenues();
    }

    /**
     * Returns the scene for the event allocator application.
     * 
     * @return returns the scene for the application
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Creates a Menu bar and added the appropriate menu items.
     *
     * @return the menu bar loaded with the appropriate menu items.
     */
    private Node loadMenu(){
        MenuBar menuBar = new MenuBar();

        final Menu file = new Menu("File");
        menuItems[0] = new MenuItem("Load Venues");
        menuItems[1] = new MenuItem("New Event");
        menuItems[2] = new MenuItem("Remove Event");
        menuItems[3] = new MenuItem("Exit");

        file.getItems().addAll(menuItems[0], new SeparatorMenuItem(),
                menuItems[1], menuItems[2], new SeparatorMenuItem(),
                menuItems[3]);
        menuBar.getMenus().add(file);

        return menuBar;
    }

    /**
     * Create the buttons inside of a VBox and returns the VBox.
     *
     * @return the VBox containing the buttons.
     */
    private Node loadButtons(){
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));

        buttons[0] = new Button("New Event");
        buttons[0].setTooltip(new Tooltip("Add a new event"));

        buttons[1] = new Button("Remove Event");
        buttons[1].setTooltip(new Tooltip("Remove an event"));

        vBox.getChildren().addAll(buttons[0], buttons[1]);

        return vBox;
    }

    /**
     * Stores the string representation of all the venues in the model into
     * a HBox and packs that HBox to the bottom of the view.
     */
    private void addVenues(){
        HBox venues = new HBox();

        for (Venue venue : model.getVenues()) {
            // create a stack to hold the venue information
            StackPane stack = new StackPane();
            stack.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
                    BorderWidths.DEFAULT)));
            stack.setUserData(venue);

            // create a text node containing the string of the venue
            Text text = new Text();
            text.setText(venue.toString());
            text.setFont(new Font(20));

            // add a margin around the text node
            StackPane.setMargin(text, new Insets(20, 20, 20, 20));

            stack.getChildren().add(text);
            venues.getChildren().add(stack);
        }

        root.setBottom(venues);
    }

    /**
     * Stores the string representation of all the events in the model into
     * a FlowPane and packs that FlowPane to the center of the view.
     */
    private void addEvents(){
        FlowPane flowPane = new FlowPane();
        for (Event event : model.getEvents()){
            Venue venue = model.getVenue(event);

            // creates a stack to store the event information into
            StackPane stack = new StackPane();
            stack.setCursor(Cursor.HAND);
            stack.setUserData(event);

            // create a text node with a representation of the event
            Text eventDisplay = new Text(event.toString() +
                    System.getProperty("line.separator") + "at " +
                    venue.getName() + " (" + venue.getCapacity() + ")");
            eventDisplay.setFont(new Font(16));

            StackPane.setMargin(eventDisplay, new Insets(20, 20, 20, 20));

            stack.getChildren().addAll(eventDisplay);
            flowPane.getChildren().add(stack);
        }
        root.setCenter(flowPane);
    }

    /**
     * Adds the traffic representation to the right side of the view.
     */
    private void addTraffic(){
        Text text = new Text(model.getTraffic().toString());
        root.setRight(text);
    }

    /**
     * Displays an error dialog box based on the given exception.
     *
     * @param exception The exception which caused this dialog to be opened.
     * @param title The title of the error dialog.
     * @param closeEvent A dialog event called when the error dialog is closed.
     */
    public void showErrorDialog(Exception exception, String title,
                                EventHandler<DialogEvent> closeEvent){
        // create a new error alert with the message of the exception
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());

        // set the header of the dialog to be the type of exception
        alert.setHeaderText(exception.getClass().getSimpleName());

        alert.setTitle(title);
        alert.setOnCloseRequest(closeEvent);

        alert.show();
    }

    /**
     * Displays the dialog for adding a new event to the allocation.
     *
     * @param callback The callback for when the dialog has been filled out.
     */
    public void showNewEventDialog(Callback<List<String>, Boolean> callback){
        // create the dialog box based on the current venues
        NewEventDialog dialog = new NewEventDialog(model.getVenues());

        // show the dialog box and wait for a user response
        Optional<List<String>> result = dialog.showAndWait();

        // ensure that some value has been returned
        if (result.isPresent()){
            // check that the result was valid otherwise return null to the
            // callback
            if (result.get().size() > 0) {
                // return the result to the callback
                callback.call(result.get());
            } else {
                callback.call(null);
            }
        }
    }

    /**
     * Attach the load venue event handler to the menu item.
     *
     * @param handler An event handler for when the load venues button is pressed.
     */
    public void addLoadVenueHandler(EventHandler<ActionEvent> handler){
        menuItems[0].setOnAction(handler);
    }

    /**
     * Attach the new event button handler to the menu items and buttons.
     *
     * @param handler An event handler for when the new event button is pressed.
     */
    public void addNewEventHandler(EventHandler<ActionEvent> handler){
        menuItems[1].setOnAction(handler);
        buttons[0].setOnAction(handler);
    }

    /**
     * Attach the remove event button handler to the menu items and buttons.
     *
     * @param handler An event handler for when the remove event button is pressed.
     */
    public void addRemoveEventHandler(EventHandler<ActionEvent> handler){
        menuItems[2].setOnAction(handler);
        buttons[1].setOnAction(handler);
    }

    /**
     * Attach the exit handler to the exit button.
     *
     * @param handler An event handler which will close the program when the
     *                exit button is pressed.
     */
    public void addExitHandler(EventHandler<ActionEvent> handler){
        menuItems[3].setOnAction(handler);
    }

    /**
     * Attach the click event handler to all of the event nodes.
     *
     * @param handler An event handler which handles when an event node is pressed.
     */
    public void addEventClickHandler(EventHandler<MouseEvent> handler){
        // get the stacks from the view
        FlowPane venues = (FlowPane) root.getCenter();
        Iterator<Node> stacks = venues.getChildren().iterator();

        // iterator through the event stacks adding the mouse clicked event
        // handler to each stack
        while(stacks.hasNext()){
            StackPane stack = (StackPane) stacks.next();
            stack.setOnMouseClicked(handler);
        }
    }
}

/**
 * A dialog box for entering details about a new event.
 */
class NewEventDialog extends Dialog<List<String>>{
    // a text field for inputting the name of the event
    private TextField eventName;
    // a text field for inputting the size of the event
    private TextField eventSize;
    // a dropdown box for selecting which venue to add the event to
    private ComboBox venueList;

    /**
     * Create a new NewEventDialog adding the venues to the dropdown box.
     *
     * @param venues A list of venues to add to the dropdown venue selector box.
     */
    NewEventDialog(List<Venue> venues){
        super();

        // configure the dialog display options
        this.setTitle("Add Event");
        this.setHeaderText("Add An Event to The Allocator");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // load the event name input
        eventName = new TextField();
        eventName.setPromptText("Event Name");

        // load the event size input
        eventSize = new TextField();
        eventSize.setPromptText("Size");

        // load the venue selector input loading in the list of venues as
        // required
        venueList = new ComboBox();
        for (Venue venue : venues) {
            venueList.getItems().add(venue.getName() + " (" +
                    venue.getCapacity() + ")");
        }
        // store the venue list as user data
        venueList.setUserData(venues);
        venueList.setPromptText("Venue");

        // add the input display to the grid view
        grid.add(new Label("Event Name:"), 0, 0);
        grid.add(eventName, 1, 0);

        grid.add(new Label("Size:"), 0, 1);
        grid.add(eventSize, 1, 1);

        grid.add(new Label("Venue:"), 0, 2);
        grid.add(venueList, 1, 2);

        // add the appropriate buttons to the dialog
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,
                ButtonType.CANCEL);

        this.getDialogPane().setContent(grid);

        // set the handling of the input
        this.setResultConverter(new Callback<ButtonType, List<String>>() {
            @Override
            public List<String> call(ButtonType button) {
                // if the user cancelled return null
                if (button != ButtonType.OK) {
                    return null;
                }

                ArrayList<String> result = new ArrayList<>();
                int venueSelection = venueList.getSelectionModel().getSelectedIndex();

                // ensure that input is valid
                if (eventName.getText().isEmpty()
                        || eventSize.getText().isEmpty()
                        || venueSelection == -1){
                    return new ArrayList<>();
                }

                // retrieve the venue object from the user data and ensure that
                // it is a valid object
                List<Venue> venues = (List<Venue>) venueList.getUserData();
                Venue venue = venues.get(venueSelection);
                if (venue == null){
                    return new ArrayList<>();
                }

                // load the result and return it
                result.add(eventName.getText());
                result.add(eventSize.getText());
                result.add(venue.getName());
                return result;
            }
        });

        // set the focus to the event name input box
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                eventName.requestFocus();
            }
        });
    }
}