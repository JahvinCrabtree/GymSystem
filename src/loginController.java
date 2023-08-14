import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/*  
    public void comboBoxData() {
        //Populating all the combo boxes.

        ObservableList<String> armOptions = 
            FXCollections.observableArrayList("Bicep Curl", "Hammer Curl", "Reverse Curl", "EZ Bar Curl", "Spider Curls",
            "Tricep Pushdown", "Skull Crushers", "Rope Pushdown", "Close Grip Press");
        armComboBox.setItems(armOptions);

        ObservableList<String> backOptions = 
            FXCollections.observableArrayList("Bent-over Barbell Row", "Pull Ups", "Seated Cable Row", "Seal Row", "Lat Pulldown");
        armComboBox.setItems(backOptions);

        ObservableList<String> chestOptions = 
            FXCollections.observableArrayList("Barbell Bench Press", "Dumbbell Bench Press", "Cable Flies", "Push Ups", "Incline Bench Press",
            "Decline Bench Press", "Dips", "Dumbbell Pullover");
        armComboBox.setItems(chestOptions);

        ObservableList<String> legOptions = 
            FXCollections.observableArrayList("Barbell Squat", "Hack Squat", "Leg Extension", "Hmastring Curl", "Sissy Squat", "Leg Press", "Lunges");
        armComboBox.setItems(legOptions);

        ObservableList<String> shoulderOptions = 
            FXCollections.observableArrayList("Military Press", "Dumbbell Shoulder Press", "Lateral Raises", "Reverse Flies", "Rope Face Pulls", "Front Raises");
        armComboBox.setItems(shoulderOptions);
    }
     */
public class loginController {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField usernameField;

    // X AND Y are used to track the original position of the mouse when dragging
    // panes etc.

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;

    @FXML
    void login(ActionEvent event) {
        String sql = "SELECT * FROM admin_table WHERE adminUser = ? and adminPassword = ?";
        connect = dbConnection.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameField.getText());
            prepare.setString(2, passwordField.getText());

            result = prepare.executeQuery();
            Alert alert;

            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            }

            else if (result.next()) {
                getData.username = usernameField.getText();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfull Login.");

                loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXML/memberDashboard.fxml"));
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
            }

            else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password is incorrect.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpPageForm(ActionEvent event) {
        try {
            loginBtn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/register.fxml"));
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
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

}
