import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import models.exerciseRecord;
import models.memberData;
import models.memberDataFetcher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class memberDashboardController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private TableView<exerciseRecord> sbdTableView;

    @FXML
    private TableColumn<exerciseRecord, Double> addBenchCol;

    @FXML
    private TableColumn<exerciseRecord, Double> addDeadliftCol;

    @FXML
    private TableColumn<exerciseRecord, Double> addSquatCol;

    @FXML
    private TableColumn<?, ?> addTotalCol;


    private void populateTableView() {
        // link columns to model properties
        addSquatCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject());
        addBenchCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject());
        addDeadliftCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject());        
    
        // Fetch data and set to table
        ObservableList<exerciseRecord> squatData = FXCollections.observableArrayList(fetchExerciseData("Squat"));
        ObservableList<exerciseRecord> benchData = FXCollections.observableArrayList(fetchExerciseData("Bench"));
        ObservableList<exerciseRecord> deadliftData = FXCollections.observableArrayList(fetchExerciseData("Deadlift"));

        addSquatCol.setCellValueFactory(cellData -> {
            if ("Squat".equalsIgnoreCase(cellData.getValue().getExerciseName())) {
                return new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject();
            }
            return null;
        });

        addBenchCol.setCellValueFactory(cellData -> {
            if ("Bench".equalsIgnoreCase(cellData.getValue().getExerciseName())) {
                return new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject();
            }
            return null;
        });

        addDeadliftCol.setCellValueFactory(cellData -> {
            if ("Deadlift".equalsIgnoreCase(cellData.getValue().getExerciseName())) {
                return new SimpleDoubleProperty(cellData.getValue().getExerciseWeight()).asObject();
            }
            return null;
        });

    ObservableList<exerciseRecord> combinedData = FXCollections.observableArrayList();
    combinedData.addAll(squatData);
    combinedData.addAll(benchData);
    combinedData.addAll(deadliftData);

    sbdTableView.setItems(combinedData);
    }

    public List<exerciseRecord> fetchExerciseData(String exerciseType) {
        List<exerciseRecord> exerciseRecords = new ArrayList<>();
        connect = dbConnection.getConnection();

        String query = "SELECT * FROM member_info WHERE LOWER(exerciseName) = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            preparedStatement.setString(1, exerciseType.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Assuming ExerciseRecord has a constructor that takes these parameters
                exerciseRecord record = new exerciseRecord(
                    resultSet.getInt("id"),
                    resultSet.getString("exerciseName"),
                    resultSet.getDouble("exerciseWeight")
                    //... add other columns as needed
                );
                exerciseRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseRecords;
    }



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
        backComboBox.setItems(backOptions);

        ObservableList<String> chestOptions = 
            FXCollections.observableArrayList("Barbell Bench Press", "Dumbbell Bench Press", "Cable Flies", "Push Ups", "Incline Bench Press",
            "Decline Bench Press", "Dips", "Dumbbell Pullover");
        chestComboBox.setItems(chestOptions);

        ObservableList<String> legOptions = 
            FXCollections.observableArrayList("Barbell Squat", "Hack Squat", "Leg Extension", "Hmastring Curl", "Sissy Squat", "Leg Press", "Lunges");
        legComboBox.setItems(legOptions);

        ObservableList<String> shoulderOptions = 
            FXCollections.observableArrayList("Military Press", "Dumbbell Shoulder Press", "Lateral Raises", "Reverse Flies", "Rope Face Pulls", "Front Raises");
        shoulderComboBox.setItems(shoulderOptions);
    }

    public void ComboBoxListeners() {
        armComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                backComboBox.getSelectionModel().clearSelection();
                chestComboBox.getSelectionModel().clearSelection();
                legComboBox.getSelectionModel().clearSelection();
                shoulderComboBox.getSelectionModel().clearSelection();
            }
        });
    
        backComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                armComboBox.getSelectionModel().clearSelection();
                chestComboBox.getSelectionModel().clearSelection();
                legComboBox.getSelectionModel().clearSelection();
                shoulderComboBox.getSelectionModel().clearSelection();
            }
        });

        chestComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                armComboBox.getSelectionModel().clearSelection();
                backComboBox.getSelectionModel().clearSelection();
                legComboBox.getSelectionModel().clearSelection();
                shoulderComboBox.getSelectionModel().clearSelection();
            }
        });

        shoulderComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                armComboBox.getSelectionModel().clearSelection();
                chestComboBox.getSelectionModel().clearSelection();
                legComboBox.getSelectionModel().clearSelection();
                backComboBox.getSelectionModel().clearSelection();
            }
        });

        legComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                armComboBox.getSelectionModel().clearSelection();
                chestComboBox.getSelectionModel().clearSelection();
                backComboBox.getSelectionModel().clearSelection();
                shoulderComboBox.getSelectionModel().clearSelection();
            }
        });

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
    private Button tutorialButton;


    private List<String> quotes; 
    private int currentQuoteIndex = 1;

    private void cycleQuotes() {
        quotes =  Arrays.asList(
            " Suffer now and live the rest of your life as a champion. - Muhammad Ali",
            "We are what we repeatedly do. Excellence then is not an act but a habit. - Aristotele",
            "Don't stop when you're tired. Stop when you're done. - David Goggins",
            "Who's gonna carry the boats? - David Goggins",
            "To keep winning, I have to keep improving."
        );

        Timeline timeline = new Timeline(new KeyFrame(
            Duration.seconds(5),
            ae -> updateQuote()
        ));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("Setting up quote cycling...");
    }

    private void updateQuote() {
        if (quotes == null || quotes.isEmpty()) return;

        quoteText.setText(quotes.get(currentQuoteIndex));
        currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size();

        System.out.println("Updating quote to: " + quotes.get(currentQuoteIndex));
    }

    

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

        int memberNum = new memberDataFetcher().fetchMemberNumByUsername(memberDataFetcher.username);

        memberData newMemberData = new memberData(
            exerciseNameTextField.getText(),
            Double.parseDouble(exerciseWeightTextField.getText()),
            Integer.parseInt(repetitonsTextField.getText()),
            dateTextField.getText(),
            Double.parseDouble(memberWeightTextField.getText())
        );

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
    void tutorialButtonActionEvent(ActionEvent event) {

    }

    public void tutorialVideo() {

    }

    @FXML
    void updateMouseEvent(ActionEvent event) {
        populateTableView();
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
        cycleQuotes();
        comboBoxData();
        ComboBoxListeners();
    }

}
