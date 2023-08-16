import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.dbConnection;
import models.memberData;
import models.memberDataFetcher;

public class memberDashboardController {

    @FXML
    private Button addBtn;

    @FXML
    private TableView<?> sbdTableView;

    @FXML
    private TableColumn<?, ?> addBenchCol;

    @FXML
    private TableColumn<?, ?> addDeadliftCol;

    @FXML
    private TableColumn<?, ?> addSquatCol;

    @FXML
    private TableColumn<?, ?> addTotalCol;

    @FXML
    private ComboBox<String> armComboBox;

    @FXML
    private ComboBox<String> backComboBox;

    @FXML
    private ComboBox<String> chestComboBox;

    @FXML
    private ComboBox<String> legComboBox;

    @FXML
    private ComboBox<String> shoulderComboBox;

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

    @FXML
    private TextField exerciseWeightTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private Button classedBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Button compareBtn;

    @FXML
    private TextField repetitonsTextField;

    @FXML
    private Label exerciseInfoLabel;

    @FXML
    private WebView exerciseVideo;

    @FXML
    private Button fitnessGetInvolvedBtn;

    @FXML
    private Button fitnessInfoBtn;

    @FXML
    private Button fundamentalGetInvolvedBtn;

    @FXML
    private Button fundamentalInfoBtn;

    @FXML
    private Label greetingLabel;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button mapBtn;

    @FXML
    private Button minimiseBtn;

    @FXML
    private Label peopleInGymLabel;

    @FXML
    private TextField quoteText;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button spartanChallengeBtn;

    @FXML
    private Button spinClassBtn;

    @FXML
    private Button spinClassGetInvolved;

    @FXML
    private TextField exerciseNameTextField;

    @FXML
    private Button sweatGetInvolvedBtn;

    @FXML
    private Button sweatInfoBtn;

    @FXML
    private Button trainingBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button updateGymNumberBtn;

    @FXML
    private AnchorPane homeForm;

    @FXML
    private AnchorPane statsForm;

    @FXML
    private AnchorPane classForm;

    @FXML
    private AnchorPane mapForm;

    @FXML
    private AnchorPane trainForm;

    @FXML
    private TextField memberWeightTextField;

    private Connection connect;
    PreparedStatement preparedStatement;

    private double x;
    private double y;

    // Alert method to reduce redundancy later down the line. 
    
    public Alert alert = new Alert(Alert.AlertType.NONE);

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void addMouseEvent(ActionEvent event) {
        insertMemberStats();
    }

    // Validation Checks
    private boolean isEmpty(TextField textField) {
        String input = textField.getText();
        return (input == null || input.trim().isEmpty());
    }

    private boolean isInteger(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(TextField textField) {
        try {
            Double.parseDouble(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDateValid(TextField textField) {
        String dateString = textField.getText();
        try {
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void insertMemberStats() {
        connect = dbConnection.getConnection();

        int memberNum = new memberDataFetcher().fetchMemberNumByUsername(memberDataFetcher.username);

        memberData newMemberData = new memberData(
            exerciseNameTextField.getText(),
            Double.parseDouble(exerciseWeightTextField.getText()),
            Integer.parseInt(repetitonsTextField.getText()),
            dateTextField.getText(),
            Double.parseDouble(memberWeightTextField.getText())
        );

        // The same validation checks from earlier.
        if (isEmpty(exerciseNameTextField) ||
                isEmpty(exerciseWeightTextField) ||
                isEmpty(repetitonsTextField) ||
                isEmpty(dateTextField) ||
                isEmpty(memberWeightTextField)) {

            showAlert(Alert.AlertType.ERROR, "Error Message!", "All fields must be filled out");
            return;
        }

        if (!isInteger(repetitonsTextField)) {
            showAlert(Alert.AlertType.ERROR, "Error Message!", "Repetitions must be a whole number!");
            return;
        }

        if (!isDouble(exerciseWeightTextField) ||
                !isDouble(memberWeightTextField)) {
            showAlert(Alert.AlertType.ERROR, "Error Message!", "Exercise Weight must be a valid number!");
            return;
        }

        if (!isDateValid(dateTextField)) {
            showAlert(Alert.AlertType.ERROR, "Error Message!", "Please use the correct format 'YYYY-MM-DD'");
            return;
        }

        String insertToTable = "INSERT INTO member_info(memberNum, exerciseName, exerciseWeight, repetitions, date, memberWeight) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connect.prepareStatement(insertToTable);

            // Setting the parameters using MemberData object
            preparedStatement.setInt(1, memberNum);
            preparedStatement.setString(2, newMemberData.getExerciseName());
            preparedStatement.setDouble(3, newMemberData.getExerciseWeight());
            preparedStatement.setInt(4, newMemberData.getRepetitions());
            preparedStatement.setDate(5, newMemberData.getDate()); // Assuming the date format is correctly converted in MemberData
            preparedStatement.setDouble(6, newMemberData.getMemberWeight());
    
            // executing the statement
            preparedStatement.executeUpdate();

            // Alert to confirm it's added.
            showAlert(Alert.AlertType.INFORMATION, "Information Message!", "Details added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // finally is used to clean up after the try and catch and close down the db
            // connection.
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void armExerciseSelect(ActionEvent event) {

    }

    @FXML
    void backExerciseSelect(ActionEvent event) {

    }

    @FXML
    void chestExerciseSelect(ActionEvent event) {

    }

    @FXML
    void closeMouseEvent(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void exerciseInfoUpdate(MouseEvent event) {

    }

    @FXML
    void fitnessGetInvolved(ActionEvent event) {

    }

    @FXML
    void fundamentalgetInvolved(ActionEvent event) {

    }

    @FXML
    void getFitnessClassInfo(ActionEvent event) {

    }

    @FXML
    void getFundamentalClassInfo(ActionEvent event) {

    }

    @FXML
    void getSpinClassInfo(ActionEvent event) {

    }

    @FXML
    void getSweatClassInfo(ActionEvent event) {

    }
    
    @FXML
    void swapForm(ActionEvent event) {
        // Cycling through the forms on the member dashboard.
        if(event.getSource() == homeBtn) {
            homeForm.setVisible(true);
            statsForm.setVisible(false);
            trainForm.setVisible(false);
            classForm.setVisible(false);
            mapForm.setVisible(false);
        } else if (event.getSource() == compareBtn) {
            homeForm.setVisible(false);
            statsForm.setVisible(true);
            trainForm.setVisible(false);
            classForm.setVisible(false);
            mapForm.setVisible(false);
        } else if (event.getSource() == trainingBtn) {
            homeForm.setVisible(false);
            statsForm.setVisible(false);
            trainForm.setVisible(true);
            classForm.setVisible(false);
            mapForm.setVisible(false);
        } else if (event.getSource() == classedBtn) {
            homeForm.setVisible(false);
            statsForm.setVisible(false);
            trainForm.setVisible(false);
            classForm.setVisible(true);
            mapForm.setVisible(false);
        } else if (event.getSource() == mapBtn) {
            homeForm.setVisible(false);
            statsForm.setVisible(false);
            trainForm.setVisible(false);
            classForm.setVisible(false);
            mapForm.setVisible(true);
        }
    }
    
    @FXML
    void legsExerciseSelect(ActionEvent event) {

    }

    @FXML
    void logoutMouseEvent(ActionEvent event) {

    }

    @FXML
    void minimiseMouseEvent(ActionEvent event) {

    }

    @FXML
    void refreshMouseEvent(ActionEvent event) {

    }

    @FXML
    void searchMouseEvent(ActionEvent event) {

    }

    @FXML
    void shoulderExerciseSelect(ActionEvent event) {

    }

    @FXML
    void spinGetInvolved(ActionEvent event) {

    }

    @FXML
    void sweatGetInvolved(ActionEvent event) {

    }

    @FXML
    void updateGymNumber(ActionEvent event) {

    }

    @FXML
    void updateMouseEvent(ActionEvent event) {

    }

    public void setGreeting() {

        // Updating the greeting label to match the Username of the user.
        // Quality of life and what not.

        String user = memberDataFetcher.username;

        if (user != null && !user.trim().isEmpty()) {
            greetingLabel.setText(user);
        } else {
            greetingLabel.setText("Member!");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        setGreeting();
    }

}
