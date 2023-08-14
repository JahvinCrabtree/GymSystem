import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class memberDashboardController {

    @FXML
    private Button addBtn;

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
    private TextField benchTextField;

    @FXML
    private Button classedBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Button compareBtn;

    @FXML
    private TextField deadliftTextField;

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
    private TextField squatTextField;

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
    private TextField weightTextField;

    @FXML
    void addMouseEvent(ActionEvent event) {

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

    public void initialize() {
        
    }

}
