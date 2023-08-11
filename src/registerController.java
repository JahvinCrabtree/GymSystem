import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


public class registerController {

    @FXML
    private Button closeButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField memberNumberTextField;

    @FXML
    private ComboBox<String> membershipComboBox;

    public void initialize() {
        // Populating the ComboBox with items
        ObservableList<String> options = 
            FXCollections.observableArrayList("Gold", "Silver", "Bronze");
        membershipComboBox.setItems(options);
    }

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private TextField usernameTextField;

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;

    private double x;
    private double y;

    private boolean isValidEmail(String email) {
        // CodeWars challenges to the rescue.
        
        String regex = "^[\\w&.-]+@([\\w-]+\\.)+(com|co\\.uk|gmail\\.com)$";
        return email.matches(regex);
    }

    private boolean isStrongPassword(String password) {
        // Example: At least one uppercase, one lowercase, one digit, one special char, and 8-20 characters long
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$";
        return password.matches(regex);
    }

    private boolean isValidPhoneNumber(String phone) {
        // Example: 10-15 digits
        String regex = "^\\d{10,15}$";
        return phone.matches(regex);
    }

    // Alert method to reduce redundancy later down the line. 
    
    public Alert alert = new Alert(Alert.AlertType.NONE);

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showTimedAlertAndRedirect(Alert.AlertType type, String title, String content, int seconds) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    
        // Pause for the alert display duration
        PauseTransition delayForAlert = new PauseTransition(Duration.seconds(seconds));
        delayForAlert.setOnFinished(event -> {
            alert.close();
    
            // Optional: Another short delay before redirecting to the login page
            PauseTransition delayForRedirect = new PauseTransition(Duration.seconds(1));
            delayForRedirect.setOnFinished(e -> loginPageForm());
            delayForRedirect.play();
        });
        delayForAlert.play();
    }

    private void loginPageForm() {
        try {
            registerButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                stage.setX(mouseEvent.getSceneX() - x);
                stage.setX(mouseEvent.getSceneY() - y);
            });

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registerButtonOnAction(ActionEvent event) {
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
        } 
        else {
            Alert alert;

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message!");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();
        }
    }

private void registerUser() {
    connect = dbConnection.getConnection();

    // storing the users inputs.
    String username = usernameTextField.getText();
    String password = setPasswordField.getText();
    String email = emailTextField.getText();
    String phone = phoneTextField.getText();
    String membership = (String) membershipComboBox.getSelectionModel().getSelectedItem();
    String memberNum = memberNumberTextField.getText();

    // Validation checks.
    if (!isValidEmail(email)) {
        showAlert(Alert.AlertType.ERROR, "Error Message!", "Invalid email format!");
        return;
    }

    if (!isStrongPassword(password)) {
        showAlert(Alert.AlertType.ERROR, "Error Message!", "Invalid password format!");
        return;
    }

    if (!isValidPhoneNumber(phone)) {
        showAlert(Alert.AlertType.ERROR, "Error Message!", "Invalid phone format!");
        return;
    }

    // Check if username already exists in database - preventing duplicates.
    String checkUser = "SELECT * FROM member_table WHERE username = ? OR email = ?";
    try {
        PreparedStatement checkStatement = connect.prepareStatement(checkUser);
        checkStatement.setString(1, username);
        checkStatement.setString(2, email);

        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next()) {
            // User with the provided username or email already exists
            // Notify the user and return from the method
            showAlert(Alert.AlertType.ERROR, "Error Message!", "Username already exists!");
            return; // Optionally return if you don't want to continue processing.
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // preparing SQL using PreparedStatement
    String insertToRegister = "INSERT INTO member_table(username, password, email, phone, membership, memberNum) VALUES (?, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement preparedStatement = connect.prepareStatement(insertToRegister);

        // Setting the parameters
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, membership);
        preparedStatement.setString(6, memberNum);

        // executing the statement
        preparedStatement.executeUpdate();  

        // Alert to confirm it's added.
        showTimedAlertAndRedirect(Alert.AlertType.INFORMATION, "Information Message!", "Details added successfully!", 3);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
