import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private ComboBox<?> membershipComboBox;

    @FXML
    private PasswordField phoneTextField;

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

    @FXML
    void closeButtonOnAction(ActionEvent event) {

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

    // preparing SQL using PreparedStatement
    String insertToRegister = "INSERT INTO member_table(username, password, email, phone, membership) VALUES (?, ?, ?, ?, ?)";

    try {
        PreparedStatement preparedStatement = connect.prepareStatement(insertToRegister);

        // Setting the parameters
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, membership);

        // executing the statement
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
