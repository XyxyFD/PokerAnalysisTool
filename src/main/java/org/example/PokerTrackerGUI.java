package org.example;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PokerTrackerGUI extends Application {

    public static String filterQuery = "";
    private CheckBox rainbowCheckbox;
    private CheckBox monotoneCheckbox;
    private CheckBox twoTonedCheckbox;
    private CheckBox aggressorIPCheckbox;
    private CheckBox aggressorOOPCheckbox;
    private CheckBox threeBetPotCheckbox;
    private CheckBox fourBetPotCheckbox;
    private CheckBox isoraiseCheckbox;
    private TableView<Scenario> tableView;

    // New variables for flop, turn, and river filtering
    private TextField flopHighCard;
    private CheckBox flopHighCardLEQ;
    private TextField flopMidCard;
    private CheckBox flopMidCardLEQ;
    private TextField flopLowCard;
    private CheckBox flopLowCardLEQ;

    private TextField turnCard;
    private CheckBox turnCardLEQ;

    private TextField riverCard;
    private CheckBox riverCardLEQ;

    @Override
    public void start(Stage primaryStage) {
        // Erstelle die TabPane, um zwischen verschiedenen Seiten zu wechseln
        TabPane tabPane = new TabPane();

        // Tab für die Pokeranalyse-Seite (dein aktueller Code)
        Tab pokerAnalysisTab = new Tab("Poker Analysis");
        pokerAnalysisTab.setContent(createAnalysisLayout());  // Verwende dein bestehendes Layout
        pokerAnalysisTab.setClosable(false); // Schließen des Tabs deaktivieren

        // Starte die Berechnungen für die Pokeranalyse-Seite sofort
        startBackgroundCalculations(tableView);

        // Tab für die zweite leere Seite
        Tab secondTab = new Tab("Upload Page");
        secondTab.setContent(createUploadPage()); // Leere Seite hinzufügen
        secondTab.setClosable(false);

        // Tabs zur TabPane hinzufügen
        tabPane.getTabs().addAll(pokerAnalysisTab, secondTab);

        // Erstelle die Hauptszene mit der TabPane
        Scene scene = new Scene(tabPane, 1600, 900);
        primaryStage.setTitle("Poker Analysis Tool");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Fenster auf Fullscreen setzen
        primaryStage.show();
    }

    // Methode zum Erstellen der bestehenden Pokeranalyse-Seite
    // Methode zum Erstellen der bestehenden Pokeranalyse-Seite
    private HBox createAnalysisLayout() {
        tableView = new TableView<>();

        // Spalte für Szenarien
        TableColumn<Scenario, String> scenarioColumn = new TableColumn<>("Scenario");
        scenarioColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scenarioColumn.setPrefWidth(200);

        // Spalte für Prozente
        TableColumn<Scenario, String> percentageColumn = new TableColumn<>("Percentage");
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        percentageColumn.setPrefWidth(100);

        // Spalte für mögliche Fälle
        TableColumn<Scenario, String> possibleCasesColumn = new TableColumn<>("Possible Cases");
        possibleCasesColumn.setCellValueFactory(new PropertyValueFactory<>("possibleCases"));
        possibleCasesColumn.setPrefWidth(150);

        tableView.getColumns().add(scenarioColumn);
        tableView.getColumns().add(percentageColumn);
        tableView.getColumns().add(possibleCasesColumn);

        // Szenarien zur Tabelle hinzufügen (bestehender Code)
        tableView.getItems().addAll(
                new Scenario("C-Bet Flop"),
                new Scenario("Fold to CBet"),
                new Scenario("Call vs C-Bet"),
                new Scenario("Raise vs C-Bet"),
                new Scenario("double Barrel Turn"),
                new Scenario("Triple Barrel River"),
                new Scenario("Call vs Double Barrel"),
                new Scenario("Fold vs Double Barrel"),
                new Scenario("Raise vs Double Barrel"),
                new Scenario("Call vs Triple Barrel"),
                new Scenario("Fold vs Triple Barrel"),
                new Scenario("Raise vs Triple Barrel"),
                new Scenario("Bet after Preflop Aggr checks Flop OOP"),
                new Scenario("Bet After Aggressor checks turn OOP after Flop Cbet"),
                new Scenario("Bet After Aggressor checks the River OOP after turn Barrel")
        );

        // Filterbereich erstellen (bestehender Code)
        VBox filterBox = createFilterBox();
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: black; -fx-border-width: 1;");

        // Hauptlayout
        HBox mainLayout = new HBox(20, tableView, filterBox);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setPrefWidth(1600);
        tableView.setPrefWidth(800); // 50% für die Tabelle
        filterBox.setPrefWidth(800); // 50% für den Filter

        return mainLayout;
    }


    private VBox createUploadPage() {
        VBox uploadPage = new VBox();
        uploadPage.setPadding(new Insets(20));


        // Create a button for file import
        Button importButton = new Button("Import Hand History");

        // Create a label to show the selected file path
        Label fileLabel = new Label("No file selected");

        // Add action to the import button to open file chooser
        importButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Hand History File");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                fileLabel.setText(selectedFile.getAbsolutePath());
                // Trigger extraction script with the selected file
                triggerExtraction(selectedFile.getAbsolutePath());
            } else {
                fileLabel.setText("No file selected");
            }
        });

        // Add components to the layout
        uploadPage.getChildren().addAll( importButton, fileLabel);
        return uploadPage;
    }

    // Method to trigger extraction script
    private void triggerExtraction(String filepath) {
        StartDataExtraction.start(filepath);
    }



    private VBox createFilterBox() {
        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.TOP_LEFT);

        Label filterLabel = new Label("Filter");
        // Checkboxen nebeneinander
        HBox checkboxBox = new HBox(10);
        rainbowCheckbox = new CheckBox("rainbow");
        monotoneCheckbox = new CheckBox("monotone");
        twoTonedCheckbox = new CheckBox("Two-toned");
        rainbowCheckbox.setOnAction(e -> {
            if (rainbowCheckbox.isSelected()) {
                monotoneCheckbox.setSelected(false);
                twoTonedCheckbox.setSelected(false);
            }
        });

        monotoneCheckbox.setOnAction(e -> {
            if (monotoneCheckbox.isSelected()) {
                rainbowCheckbox.setSelected(false);
                twoTonedCheckbox.setSelected(false);
            }
        });

        twoTonedCheckbox.setOnAction(e -> {
            if (twoTonedCheckbox.isSelected()) {
                rainbowCheckbox.setSelected(false);
                monotoneCheckbox.setSelected(false);
            }
        });
        checkboxBox.getChildren().addAll(rainbowCheckbox, monotoneCheckbox, twoTonedCheckbox);

        // Flop-Bereich mit Textfeldern im Kartenformat
        HBox flopBox = new HBox(10);
        VBox flopCardBox1 = new VBox(5);
        flopHighCard = new TextField();
        flopHighCard.setPrefSize(60, 90);
        flopHighCardLEQ = new CheckBox("<=");
        flopCardBox1.getChildren().addAll(flopHighCard, flopHighCardLEQ);

        VBox flopCardBox2 = new VBox(5);
        flopMidCard = new TextField();
        flopMidCard.setPrefSize(60, 90);
        flopMidCardLEQ = new CheckBox("<=");
        flopCardBox2.getChildren().addAll(flopMidCard, flopMidCardLEQ);

        VBox flopCardBox3 = new VBox(5);
        flopLowCard = new TextField();
        flopLowCard.setPrefSize(60, 90);
        flopLowCardLEQ = new CheckBox("<=");
        flopCardBox3.getChildren().addAll(flopLowCard, flopLowCardLEQ);

        flopBox.getChildren().addAll(flopCardBox1, flopCardBox2, flopCardBox3);

        Label flopLabel = new Label("Flop");

        // Turn-Bereich mit fixierter Breite
        VBox turnCardBox = new VBox(5);
        turnCard = new TextField();
        turnCard.setPrefSize(60, 90); // Fixierte Breite und Höhe
        turnCard.setMaxWidth(60); // Maximalbreite setzen
        turnCardLEQ = new CheckBox("<=");
        turnCardBox.getChildren().addAll(turnCard, turnCardLEQ);

        Label turnLabel = new Label("Turn");

        // River-Bereich mit fixierter Breite
        VBox riverCardBox = new VBox(5);
        riverCard = new TextField();
        riverCard.setPrefSize(60, 90); // Fixierte Breite und Höhe
        riverCard.setMaxWidth(60); // Maximalbreite setzen
        riverCardLEQ = new CheckBox("<=");
        riverCardBox.getChildren().addAll(riverCard, riverCardLEQ);

        Label riverLabel = new Label("River");


        // Checkboxen für weitere Filter
        CheckBox noMultiwayCheckbox = new CheckBox("No Multiway");
        noMultiwayCheckbox.setSelected(true); // Standardmäßig angekreuzt
        // Aggressor IP checkbox
        aggressorIPCheckbox = new CheckBox("Aggressor IP");
        aggressorOOPCheckbox = new CheckBox("Aggressor OOP");

        threeBetPotCheckbox = new CheckBox("3Bet Pot");
        fourBetPotCheckbox = new CheckBox("4Bet Pot");
        isoraiseCheckbox = new CheckBox("Isoraise Pot");

        aggressorIPCheckbox.setOnAction(e -> {
            if (aggressorIPCheckbox.isSelected()) {
                aggressorOOPCheckbox.setSelected(false);
            }
        });

        aggressorOOPCheckbox.setOnAction(e -> {
            if (aggressorOOPCheckbox.isSelected()) {
                aggressorIPCheckbox.setSelected(false);
            }
        });



        threeBetPotCheckbox.setOnAction(e -> {
            if (threeBetPotCheckbox.isSelected()) {
                fourBetPotCheckbox.setSelected(false);
            }
        });
        fourBetPotCheckbox.setOnAction(e -> {
            if (fourBetPotCheckbox.isSelected()) {
                threeBetPotCheckbox.setSelected(false);
            }
        });

        //Calculate-Button
        Button calculateButton = new Button("Calculate");
        calculateButton.setPrefWidth(150);
        calculateButton.setOnAction(e -> {
            // Trigger filter calculations
            filterCalculations();
            // Recalculate percentages based on the new filter query
            startBackgroundCalculations(tableView);
        });

        //Calculate-Button Container
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER); // Center the button horizontally
        buttonContainer.getChildren().add(calculateButton);

        // Push the button to the bottom by setting the VBox's spacing and filling with the remaining space
        VBox.setVgrow(buttonContainer, Priority.ALWAYS); // Allow space above to push the button down


        filterBox.getChildren().addAll(
                filterLabel, checkboxBox, flopLabel, flopBox,
                turnLabel, turnCardBox,
                riverLabel, riverCardBox,
                new Label(" "),
                noMultiwayCheckbox, aggressorIPCheckbox, aggressorOOPCheckbox, threeBetPotCheckbox, fourBetPotCheckbox, isoraiseCheckbox, buttonContainer
        );

        return filterBox;
    }
    private void filterCalculations() {
        filterQuery = ""; // Reset the query

        // Existing filters for flop texture
        if (rainbowCheckbox.isSelected()) {
            filterQuery += " AND flopTexture = 'rainbow'";
        } else if (monotoneCheckbox.isSelected()) {
            filterQuery += " AND flopTexture = 'monotone'";
        } else if (twoTonedCheckbox.isSelected()) {
            filterQuery += " AND flopTexture = 'two-tone'";
        }

        // Aggressor IP filter
        if (aggressorIPCheckbox.isSelected()) {
            filterQuery += " AND aggrIP = TRUE";
        }else if(aggressorOOPCheckbox.isSelected()){
            filterQuery += " AND aggrIP = FALSE";
        }
        if(threeBetPotCheckbox.isSelected()){
            filterQuery += " AND threeBetBB > -1";
        }
        if(fourBetPotCheckbox.isSelected()){
            filterQuery += " AND fourBetBB > -1";
        }

        if (isoraiseCheckbox.isSelected()) {
            filterQuery += " AND isoraisePot = TRUE";
        }

        // Flop high card filtering logic
        String highCard = flopHighCard.getText();
        if (!highCard.isEmpty()) {
            int highCardValue = DataProcessing.cardToValue(highCard);
            if (highCardValue != -1) {
                if (flopHighCardLEQ.isSelected()) {
                    filterQuery += " AND H_FlopCard <= " + highCardValue;
                } else {
                    filterQuery += " AND H_FlopCard = " + highCardValue;
                }
            }
        }

        // Flop middle card filtering logic
        String midCard = flopMidCard.getText();
        if (!midCard.isEmpty()) {
            int midCardValue = DataProcessing.cardToValue(midCard);
            if (midCardValue != -1) {
                if (flopMidCardLEQ.isSelected()) {
                    filterQuery += " AND M_FlopCard <= " + midCardValue;
                } else {
                    filterQuery += " AND M_FlopCard = " + midCardValue;
                }
            }
        }

        // Flop low card filtering logic
        String lowCard = flopLowCard.getText();
        if (!lowCard.isEmpty()) {
            int lowCardValue = DataProcessing.cardToValue(lowCard);
            if (lowCardValue != -1) {
                if (flopLowCardLEQ.isSelected()) {
                    filterQuery += " AND L_FlopCard <= " + lowCardValue;
                } else {
                    filterQuery += " AND L_FlopCard = " + lowCardValue;
                }
            }
        }

        // Turn card filtering logic
        String turn = turnCard.getText();
        if (!turn.isEmpty()) {
            int turnCardValue = DataProcessing.cardToValue(turn);
            if (turnCardValue != -1) {
                if (turnCardLEQ.isSelected()) {
                    filterQuery += " AND turn <= " + turnCardValue;
                } else {
                    filterQuery += " AND turn = " + turnCardValue;
                }
            }
        }

        // River card filtering logic
        String river = riverCard.getText();
        if (!river.isEmpty()) {
            int riverCardValue = DataProcessing.cardToValue(river);
            if (riverCardValue != -1) {
                if (riverCardLEQ.isSelected()) {
                    filterQuery += " AND river <= " + riverCardValue;
                } else {
                    filterQuery += " AND river = " + riverCardValue;
                }
            }
        }

        // Additional filter conditions can be added here as needed
    }




    private void startBackgroundCalculations(TableView<Scenario> tableView) {
        for (Scenario currentScenario : tableView.getItems()) {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    // Lokale Kopie des Scenario-Objekts verwenden
                    Scenario scenario = currentScenario;

                    // Hier wird die Berechnung simuliert
                    String result = calculatePercentage(scenario);
                    scenario.setPercentage(result);
                    return null;
                }

                @Override
                protected void succeeded() {
                    tableView.refresh();
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }


    private String calculatePercentage(Scenario scenario) {
        switch (scenario.getName()) {
            case "C-Bet Flop":
                return calculateCbetFlop(scenario);
            case "Fold to CBet":
                return calculateFoldVsCbet(scenario);
            case "Call vs C-Bet":
                return calculateCallVsCbet(scenario);
            case "Raise vs C-Bet":
                return calculateRaiseVsCbet(scenario);

            case "double Barrel Turn":
                return calculateDoubleBarrelTurn(scenario);
            case "Triple Barrel River":
                return calculateTripleBarrelRiver(scenario);
            case "Call vs Double Barrel":
                return calculateCallVsDoubleBarrel(scenario);
            case "Fold vs Double Barrel":
                return calculateFoldVsDoubleBarrel(scenario);
            case "Raise vs Double Barrel":
                return calculateRaiseVsDoubleBarrel(scenario);
            case "Call vs Triple Barrel":
                return calculateCallVsTripleBarrel(scenario);
            case "Fold vs Triple Barrel":
                return calculateFoldVsTripleBarrel(scenario);
            case "Raise vs Triple Barrel":
                return calculateRaiseVsTripleBarrel(scenario);
            case "Bet after Preflop Aggr checks Flop OOP":
                return calculateBetAfterCheck(scenario);
            case "Bet After Aggressor checks turn OOP after Flop Cbet":
                return calculateBetAfterCheckTurn(scenario);
            case "Bet After Aggressor checks the River OOP after turn Barrel":
                return calculateBetAfterCheckRiver(scenario);




            default:
                return "N/A"; // Return a default value if the scenario is not recognized
        }
    }



// Example methods for new scenarios:

    private String calculateCallVsDoubleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE callTurnBarrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE turnBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateFoldVsDoubleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE foldToTurnBarrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE turnBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateRaiseVsDoubleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE raiseTurnBarrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE turnBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return"N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateCallVsTripleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE call3Barrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE tripleBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateFoldVsTripleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE foldTo3Barrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE tripleBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateRaiseVsTripleBarrel(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE raise3Barrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE tripleBarrel = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }


    private String calculateTripleBarrelRiver(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE tripleBarrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE callTurnBarrel = TRUE" + filterQuery); //Leads werden nicht berücksichtigt
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";
        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateDoubleBarrelTurn(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE turnBarrel = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbetCall = TRUE" + filterQuery);//Leads werden nicht berücksichtigt
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }

    private String calculateCbetFlop(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbet = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE flopPlayers = 2 AND potType != 'LimpedPot'" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));
        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }
    private String calculateFoldVsCbet(Scenario scenario) {

        // Abfrage zur Ermittlung der Anzahl der Fälle, in denen auf eine C-Bet gefoldet wurde
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbetFold = TRUE" + filterQuery);

        // Abfrage zur Ermittlung der Gesamtzahl der möglichen C-Bet-Fälle
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbet = TRUE" + filterQuery);

        // Festlegung der möglichen Fälle im Szenario
        scenario.setPossibleCases(String.valueOf(possibleCases));

        // Falls keine möglichen Fälle existieren, wird "N/A" zurückgegeben
        if (possibleCases == 0) return "N/A";

        // Berechnung des Prozentsatzes
        double percentage = (double) casesMet / possibleCases * 100;

        // Rückgabe des formatierten Prozentsatzes
        return String.format("%.2f%%", percentage);
    }
    private String calculateCallVsCbet(Scenario scenario) {
        // Abfrage zur Ermittlung der Anzahl der Fälle, in denen eine C-Bet gecallt wurde
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbetCall = TRUE" + filterQuery);

        // Abfrage zur Ermittlung der Gesamtzahl der möglichen C-Bet-Fälle
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbet = TRUE" + filterQuery);

        // Festlegung der möglichen Fälle im Szenario
        scenario.setPossibleCases(String.valueOf(possibleCases));

        // Falls keine möglichen Fälle existieren, wird "N/A" zurückgegeben
        if (possibleCases == 0) return "N/A";

        // Berechnung des Prozentsatzes
        double percentage = (double) casesMet / possibleCases * 100;

        // Rückgabe des formatierten Prozentsatzes
        return String.format("%.2f%%", percentage);
    }
    private String calculateRaiseVsCbet(Scenario scenario) {
        // Abfrage zur Ermittlung der Anzahl der Fälle, in denen eine C-Bet geraist wurde
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbetRaise = TRUE" + filterQuery);

        // Abfrage zur Ermittlung der Gesamtzahl der möglichen C-Bet-Fälle
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE cbet = TRUE" + filterQuery);

        // Festlegung der möglichen Fälle im Szenario
        scenario.setPossibleCases(String.valueOf(possibleCases));

        // Falls keine möglichen Fälle existieren, wird "N/A" zurückgegeben
        if (possibleCases == 0) return "N/A";

        // Berechnung des Prozentsatzes
        double percentage = (double) casesMet / possibleCases * 100;

        // Rückgabe des formatierten Prozentsatzes
        return String.format("%.2f%%", percentage);
    }

    //when aggressor checks oop and caller bets
    private String calculateBetAfterCheck(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE betAfterCheck = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE flopPlayers = 2 AND potType != 'LimpedPot' AND cbet = FALSE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));

        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }
    private String calculateBetAfterCheckTurn(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE betAfterCheckTurn = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE turnBarrel = FALSE AND cbetCall = TRUE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));

        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }
    private String calculateBetAfterCheckRiver(Scenario scenario) {
        int casesMet = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE betAfterCheckRiver = TRUE" + filterQuery);
        int possibleCases = DatabaseConnector.executeCountQuery("SELECT COUNT(*) FROM PokerHands WHERE callTurnBarrel = TRUE AND tripleBarrel = FALSE" + filterQuery);
        scenario.setPossibleCases(String.valueOf(possibleCases));

        if (possibleCases == 0) return "N/A";

        double percentage = (double) casesMet / possibleCases * 100;
        return String.format("%.2f%%", percentage);
    }







    public static void main(String[] args) {
        launch(args);
    }

    // Klasse, die ein Szenario repräsentiert
    public static class Scenario {
        private String name;
        private String percentage;
        private String possibleCases; // Neue Variable für die möglichen Fälle

        public Scenario(String name) {
            this.name = name;
            this.percentage = "Calculating..."; // Initialer Status
            this.possibleCases = "N/A"; // Standardwert
        }

        public String getName() {
            return name;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getPossibleCases() {
            return possibleCases;
        }

        public void setPossibleCases(String possibleCases) {
            this.possibleCases = possibleCases;
        }
    }

}
