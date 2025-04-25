package FOS;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Sample extends Application {
    private dynamic_Queue<order> orderQueue = new dynamic_Queue<>(order.class);
    private List<MenuItem> menu = new ArrayList<>();
    private TextArea orderQueueDisplay;
    private TextArea statusQueueDisplay;
    ScheduledExecutorService scheduler;
    private ScheduledFuture<?> automaticProcessTask; // To store the scheduled task
    private ScheduledFuture<?> almostReadyAlertTask;  // To store the scheduled almost ready alert
    private ScheduledFuture<?> readyAlertTask;  // To store the scheduled ready alert
    private int orderCountDelay = 0;


    @Override
    public void start(Stage primaryStage) {
        // Initialize menu
        menu.add(new MenuItem("Burger", 5.99, Arrays.asList("Bun", "Beef Patty", "Lettuce", "Tomato", "Cheese"), Arrays.asList("Bacon", "Extra Cheese", "Pickles")));
        menu.add(new MenuItem("Pizza", 8.99, Arrays.asList("Tomato Sauce", "Cheese", "Pepperoni"), Arrays.asList("Mushrooms", "Pineapples", "Bacon")));
        menu.add(new MenuItem("Salad", 6.99, Arrays.asList("Lettuce", "Tomatoes", "Cheese", "Cucumbers", "Olives"), Arrays.asList("Chicken", "Ranch", "Croutons")));

        // Create UI components
        primaryStage.setTitle("Restaurant Ordering System");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 400);

        VBox leftPanel = createLeftPanel();
        root.setLeft(leftPanel);

        // Order queue display
        orderQueueDisplay = new TextArea();
        orderQueueDisplay.setEditable(false);
        orderQueueDisplay.setPrefWidth(300);
        root.setRight(orderQueueDisplay);

        statusQueueDisplay = new TextArea();
        statusQueueDisplay.setEditable(false);
        statusQueueDisplay.setPrefWidth(150);
        root.setRight(statusQueueDisplay);
        
        HBox textAreasBox = new HBox(orderQueueDisplay, statusQueueDisplay);
        root.setCenter(textAreasBox);

        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));

        // Buttons for actions
        Button btnPlaceOrder = new Button("Place Order");
        Button btnRepeatOrder = new Button("Repeat Last Order");
        Button btnViewNextOrder = new Button("View Next Order");
        Button btnProcessOrder = new Button("Process Order");
        Button btnQueueSize = new Button("View Queue Size");
        Button btnExit = new Button("Exit");

        // Action Handlers
        btnPlaceOrder.setOnAction(this::handlePlaceOrder);
        btnRepeatOrder.setOnAction(this::handleRepeatOrder);
        btnViewNextOrder.setOnAction(this::handleViewNextOrder);
        btnProcessOrder.setOnAction(this::handleProcessOrder);
        btnQueueSize.setOnAction(this::handleQueueSize);
        btnExit.setOnAction(this::handleExit);

        leftPanel.getChildren().addAll(btnPlaceOrder, btnRepeatOrder, btnViewNextOrder, btnProcessOrder, btnQueueSize, btnExit);

        return leftPanel;
    }

    private void statusUpdate(String status) {
    	statusQueueDisplay.appendText(status + "\n");
    }
    
    private void handlePlaceOrder(ActionEvent event) {
        // Create a new Stage for the order customization
        Stage customizationStage = new Stage();
        customizationStage.setTitle("Customize Your Order");

        // Creating UI elements for customization
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        // Dropdown for selecting the menu item
        ChoiceBox<MenuItem> menuChoiceBox = new ChoiceBox<>();
        menuChoiceBox.getItems().addAll(menu);
        menuChoiceBox.getSelectionModel().selectFirst(); // default selection

        TextField customerName = new TextField();
        customerName.setPromptText("Enter Your Name");
        
        // TextField to input removed ingredients
        TextField removedIngredientsField = new TextField();
        removedIngredientsField.setPromptText("Enter ingredients to remove (comma separated)");

        // TextField to input added extras
        TextField addedExtrasField = new TextField();
        addedExtrasField.setPromptText("Enter extras to add (comma separated)");

        // ComboBox for combo option
        CheckBox comboCheckBox = new CheckBox("Make it a combo meal (+$3.00)");

        Button confirmButton = new Button("Confirm Order");

        confirmButton.setOnAction(e -> {
            // Get the selected item and input values
            MenuItem selectedItem = menuChoiceBox.getValue();
            String cName = customerName.getText();
            List<String> removedIngredients = new ArrayList<>(Arrays.asList(removedIngredientsField.getText().split(",")));
            List<String> addedExtras = new ArrayList<>();
            String extrasText = addedExtrasField.getText().trim();

            if (!extrasText.isEmpty()) {
                addedExtras = Arrays.asList(extrasText.split("\\s*,\\s*"));
            }
            boolean isCombo = comboCheckBox.isSelected();

            // Create new order
            order newOrder = new order(cName, selectedItem, removedIngredients, addedExtras, isCombo);
            orderQueue.push(newOrder);
            updateQueueDisplay();
            
            sendFoodAlmostReadyNotification(newOrder);

            // Close the customization window
            customizationStage.close();
        });

        vbox.getChildren().addAll( menuChoiceBox, customerName, removedIngredientsField, addedExtrasField, comboCheckBox, confirmButton);

        Scene scene = new Scene(vbox, 450, 300);
        customizationStage.setScene(scene);
        customizationStage.show();
    }

    private void updateQueueDisplay() {
        StringBuilder queueText = new StringBuilder();
        StringBuilder statusText = new StringBuilder();

        // Iterate through the queue using the custom dynamic queue logic
        for (int i = 0; i < orderQueue.size(); i++) {
            order ord = orderQueue.get(i);  // Assuming you have a get method for the dynamic queue
            queueText.append(ord.toString()).append("\n\n");
            statusText.append("Order #").append(ord.getOrderId()).append(" - ").append(ord.getStatus()).append("\n");
        }

        // Update the TextArea with the current state of the queue
        orderQueueDisplay.setText(queueText.toString());
        statusQueueDisplay.setText(statusText.toString());
    }

    private void handleRepeatOrder(ActionEvent event) {
        if (orderQueue.isEmpty()) {
            System.out.println("No previous orders to repeat.");
        } else {
            order lastOrder = orderQueue.front();
            order newReorder = new order(lastOrder.getCustomerName(), lastOrder.getItem(), lastOrder.getRemovedIngredients(), lastOrder.getAddedExtras(), lastOrder.getIsCombo());
            orderQueue.push(newReorder);
            updateQueueDisplay();
            sendFoodAlmostReadyNotification(newReorder);
        }
    }

    private void handleViewNextOrder(ActionEvent event) {
        if (!orderQueue.isEmpty()) {
            System.out.println("Next order is: " + orderQueue.front() + "\n");
        }
    }
    
    private void processOrder(order currentOrder, boolean isAutoProcessed) {
        currentOrder.setStatus("Processed\n");

        if (isAutoProcessed) {
            System.out.println("Order expired and was auto-processed: \n" + currentOrder);
            showAlert("Your order was not picked up in time and has been auto-processed: ");
        } else {
            System.out.println("Order manually processed: \n" + currentOrder);
            showAlert("Enjoy Your Meal: ");
        }

        orderQueue.pop();
        updateQueueDisplay();
        if (automaticProcessTask != null && !automaticProcessTask.isDone()) {
            automaticProcessTask.cancel(false);
        }
        if (readyAlertTask != null && !readyAlertTask.isDone()) {
            readyAlertTask.cancel(false);
        }
    }
  
    private void handleProcessOrder(ActionEvent event) {
        if (!orderQueue.isEmpty()) {
            order currentOrder = orderQueue.front();
            processOrder(currentOrder, false);
        }
    }

    private void sendFoodAlmostReadyNotification(order currentOrder) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        int delayPerOrder = 5; // seconds
        int baseDelay = orderCountDelay * delayPerOrder;

        // Schedule "Almost Ready" after (5 + baseDelay) seconds
        scheduler.schedule(() -> {
            Platform.runLater(() -> {
                currentOrder.setStatus("Almost Ready");
                showAlert("Order #" + currentOrder.getOrderId() + " (" + currentOrder.getCustomerName() + ") is almost ready!");
                updateQueueDisplay();
            });

            // Schedule "Ready" status 5 seconds later (10 + baseDelay total)
            startReadyAlertTimer(currentOrder, baseDelay);
        }, 5 + baseDelay, TimeUnit.SECONDS);

        // Increment delay counter for next order
        orderCountDelay++;
    }

    private void startReadyAlertTimer(order currentOrder, int baseDelay) {
        readyAlertTask = scheduler.schedule(() -> {
            Platform.runLater(() -> {
                currentOrder.setStatus("Ready");
                showAlert("Order #" + currentOrder.getOrderId() + " (" + currentOrder.getCustomerName() + ") is ready!");
                updateQueueDisplay();
            });

            // Auto-process 10 seconds later (total 20 + baseDelay)
            startAutomaticProcessingTimer(currentOrder, baseDelay);
        }, 5, TimeUnit.SECONDS); // 5 seconds after "Almost Ready"
    }

    private void startAutomaticProcessingTimer(order currentOrder, int baseDelay) {
        automaticProcessTask = scheduler.schedule(() -> {
            Platform.runLater(() -> {
                if (!currentOrder.getStatus().equals("Auto-Processed")) {
                    currentOrder.setStatus("Auto-Processed");
                    showAlert("Order #" + currentOrder.getOrderId() + " was auto-processed.");
                    processOrder(currentOrder, true);
                }
            });
        }, 10, TimeUnit.SECONDS); // 10 seconds after "Ready"
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleQueueSize(ActionEvent event) {
        System.out.println("Queue size: " + orderQueue.size());
    }

    private void handleExit(ActionEvent event) {
        System.out.println("Exiting system...");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
