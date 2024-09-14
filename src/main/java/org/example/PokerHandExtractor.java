package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokerHandExtractor {

    public static class PokerHand {

        public String blinds;
        public String handNumber;
        public String[] holeCards;
        public String[] boardCards;

        public List<String> preflopAction;
        public List<String> flopAction;
        public List<String> turnAction;
        public List<String> riverAction;
        public List<String> actions;
        public String pokerSite;
        public String gameFormat;
        public String maxPlayers;
        public String btnSeat;

        public String LJ;
        public String HJ;
        public String CO;
        public String BTN;
        public String SB;
        public String BB;
        public String UTG;
        public String UTG1;
        public String UTG2;

        public double ljStack;
        public double hjStack;
        public double coStack;
        public double btnStack;
        public double sbStack;
        public double bbStack;
        public double utgStack;
        public double utg1Stack;
        public double utg2Stack;
        public PokerHand(String handNumber) {
            this.handNumber = handNumber;
            this.holeCards = new String[2];
            this.boardCards = new String[5];
            this.actions = new ArrayList<>();
            this.preflopAction = new ArrayList<>();
            this.flopAction = new ArrayList<>();
            this.turnAction = new ArrayList<>();
            this.riverAction = new ArrayList<>();
        }
    }

    public static List<PokerHand> extract(String filepath) {

        List<PokerHand> pokerHands = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            PokerHand currentHand = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#Game No :")) {
                    if (currentHand != null) {
                        pokerHands.add(currentHand);
                    }
                    currentHand = new PokerHand(line.split(" : ")[1]);

                }
                //Extracts Blinds and gametype;
                else if (line.startsWith("*****")) {
                    extractGameType(line, currentHand);
                    line = br.readLine();
                    currentHand.blinds = extractBlinds(line);
                    line = br.readLine();
                    extractGameType(line, currentHand);
                }
                else if(line.contains("is the button")){
                    try {
                        allocatePosInNormalGameFormat(line, currentHand, br);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Extract Positions
                else if (line.startsWith("Total number of players :")) {
                    int totalPlayers = extractTotalNumberOfPlayers(line);
                    while ((line = br.readLine()) != null){
                        if(!line.startsWith("Seat")){
                            break;
                        }else{
                            extractPosByPlayerNameSnap(line, currentHand, totalPlayers);
                        }
                    }
                    
                } else if (line.startsWith("Dealt to")) {
                    boolean summary = false;
                    String[] parts = line.split(" \\[");
                    currentHand.holeCards = parts[1].replace("]", "").split(", ");
                    while ((line = br.readLine()) != null){

                        if (summary){
                            break;
                        }
                        String [] actionparts = line.split(" ");
                        String playerName = actionparts[0];
                        String currentPosition = addPosToAction(playerName, currentHand);

                        //PREFLOP   //PREFLOP   //PREFLOP   //PREFLOP   //PREFLOP   //PREFLOP



                        if(line.startsWith("** Summary **")){
                            summary = true;
                        }
                        else if(line.contains("folds")){
                            currentHand.preflopAction.add(currentPosition + "_" + "f");
                        }else if(line.contains("calls")){
                            currentHand.preflopAction.add(currentPosition + "_" + "c");
                        }else if(line.contains("raises")){
                            currentHand.preflopAction.add(currentPosition + "_" + "r" + extractAmount(line));
                        }else if (line.contains("bets")){
                            currentHand.preflopAction.add(currentPosition + "_" + "b" + extractAmount(line));
                        }
                        else if (line.contains("checks")) {
                            currentHand.preflopAction.add(currentPosition + "_" + "x");
                        }else if(line.startsWith("** Dealing flop **")){

                            //FLOP  //FLOP  //FLOP  //FLOP  //FLOP  //FLOP

                            String cardString = line.split("\\[")[1].replace("]", "").trim();
                            parts = cardString.split(",\\s*");

                            // Debug-Ausgabe, um sicherzustellen, dass die Karten korrekt extrahiert werden


                            // Kopiere die extrahierten Karten ins BoardCards-Array
                            System.arraycopy(parts, 0, currentHand.boardCards, 0, parts.length);
                            while ((line = br.readLine()) != null){

                                if (summary){
                                    break;
                                }

                                actionparts = line.split(" ");
                                playerName = actionparts[0];
                                currentPosition = addPosToAction(playerName, currentHand);

                                if(line.startsWith("** Summary **")){
                                    summary = true;
                                }
                                else if(line.contains("folds")){
                                    currentHand.flopAction.add(currentPosition + "_" + "f");
                                }else if(line.contains("calls")){
                                    currentHand.flopAction.add(currentPosition + "_" + "c");
                                }else if(line.contains("raises")){
                                    currentHand.flopAction.add(currentPosition + "_" + "r" + extractAmount(line));
                                }else if (line.contains("bets")){
                                    currentHand.flopAction.add(currentPosition + "_" + "b" + extractAmount(line));
                                }
                                else if (line.contains("checks")) {
                                    currentHand.flopAction.add(currentPosition + "_" + "x");
                                }else if(line.startsWith("** Dealing turn **")){

                                    //TURN  //TURN  //TURN  //TURN  //TURN  //TURN

                                    String turnCard = line.split("\\[")[1].replace("]", "").trim();

                                    // Debug-Ausgabe zur Überprüfung der extrahierten Karte

                                    currentHand.boardCards[3] = turnCard;
                                    while ((line = br.readLine()) != null){

                                        if (summary){
                                            break;
                                        }
                                        actionparts = line.split(" ");
                                        playerName = actionparts[0];
                                        currentPosition = addPosToAction(playerName, currentHand);

                                        if(line.startsWith("** Summary **")){
                                            summary = true;
                                        }
                                        else if(line.contains("folds")){
                                            currentHand.turnAction.add(currentPosition + "_" + "f");
                                        }else if(line.contains("calls")){
                                            currentHand.turnAction.add(currentPosition + "_" + "c");
                                        }else if(line.contains("raises")){
                                            currentHand.turnAction.add(currentPosition + "_" + "r" + extractAmount(line));
                                        }else if (line.contains("bets")){
                                            currentHand.turnAction.add(currentPosition + "_" + "b" + extractAmount(line));
                                        }else if (line.contains("checks")) {
                                            currentHand.turnAction.add(currentPosition + "_" + "x");
                                        } else if(line.startsWith("** Dealing river **")){

                                            //RIVER //RIVER //RIVER //RIVER //RIVER //RIVER

                                            String riverCard = line.split("\\[")[1].replace("]", "").trim();

                                            // Debug-Ausgabe zur Überprüfung der extrahierten Karte

                                            currentHand.boardCards[4] = riverCard;
                                            while ((line = br.readLine()) != null) {

                                                if (summary){
                                                    break;
                                                }

                                                actionparts = line.split(" ");
                                                playerName = actionparts[0];
                                                currentPosition = addPosToAction(playerName, currentHand);

                                                if (line.startsWith("** Summary **")) {
                                                    summary = true;
                                                }
                                                else if (line.contains("folds")) {
                                                    currentHand.riverAction.add(currentPosition + "_" + "f");
                                                } else if (line.contains("calls")) {
                                                    currentHand.riverAction.add(currentPosition + "_" + "c");
                                                } else if (line.contains("raises")) {
                                                    currentHand.riverAction.add(currentPosition + "_" + "r" + extractAmount(line));
                                                } else if (line.contains("bets")) {
                                                    currentHand.riverAction.add(currentPosition + "_" + "b" + extractAmount(line));
                                                }else if (line.contains("checks")) {
                                                    currentHand.riverAction.add(currentPosition + "_" + "x");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Add the last hand
            if (currentHand != null) {
                pokerHands.add(currentHand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //printResults(pokerHands);

        return pokerHands;
    }

    public static String extractBlinds(String line) {
        int startIndex = line.indexOf('$') + 1;
        int endIndex = line.indexOf(' ', startIndex);

        if (startIndex > 0 && endIndex > startIndex) {
            String blinds = line.substring(startIndex, endIndex);
            int separatorIndex = blinds.indexOf('/');
            String smallBlind = blinds.substring(0, separatorIndex);
            String bigBlind = blinds.substring(separatorIndex + 2); // +2 to skip past '$' after '/'
            return smallBlind + "/" + bigBlind;
        }

        return null;
    }




    public static String extractAmount(String line){
        int startIndex = line.indexOf('$') + 1;
        int endIndex = line.indexOf(']', startIndex);

        if (startIndex > 0 && endIndex > startIndex) {
            return line.substring(startIndex, endIndex);
        }

        return null;
    }

    //Extract GameType
    public static void extractGameType(String line, PokerHand currentHand) {
        if (line.startsWith("***** 888poker")) {
            currentHand.pokerSite = "888poker";
            if (line.contains("Snap Poker")) {
                currentHand.gameFormat = "Snap";
            } else {
                currentHand.gameFormat = "Normal";
            }
        } else if (line.startsWith("Table")) {
            if (line.contains("6 Max")) {
                currentHand.maxPlayers = "6 Max";
            } else if (line.contains("9 Max")) {
                currentHand.maxPlayers = "9 Max";
            }
        }
    }



    //EXTRACT POSITIONS----------------------------

    public static int extractTotalNumberOfPlayers(String line) {
        if (line.startsWith("Total number of players :")) {
            String[] parts = line.split(" : ");
            return Integer.parseInt(parts[1].trim());
        }
        return -1; // oder einen anderen Standardwert oder Fehlerbehandlung
    }

    // attach a name to a position and tracks the stacksize of this position. the playername is only needed to identify the position
    // JUST SNAP

    public static void extractPosByPlayerNameSnap(String line, PokerHand currentHand, int totalPlayers){
        String[] parts = line.split(": ");
        String seatInfo = parts[0];
        String playerName = parts[1].split(" ")[0];
        double stacksize = Double.parseDouble(parts[1].split(" ")[2].
                replace("(", "").replace("$", "").replace(")", ""));

        if(totalPlayers == 6){
            if (seatInfo.contains("Seat 1")) {
                currentHand.BTN = playerName;
                currentHand.btnStack = stacksize;
            } else if (seatInfo.contains("Seat 2")) {
                currentHand.SB = playerName;
                currentHand.sbStack = stacksize;
            } else if (seatInfo.contains("Seat 4")) {
                currentHand.BB = playerName;
                currentHand.bbStack = stacksize;
            } else if (seatInfo.contains("Seat 6")) {
                currentHand.LJ = playerName;
                currentHand.ljStack = stacksize;
            } else if (seatInfo.contains("Seat 7")) {
                currentHand.HJ = playerName;
                currentHand.hjStack = stacksize;
            } else if (seatInfo.contains("Seat 9")) {
                currentHand.CO = playerName;
                currentHand.coStack = stacksize;
            }
        }else{
            if (seatInfo.contains("Seat 1")) {
                currentHand.BTN = playerName;
                currentHand.btnStack = stacksize;
            } else if (seatInfo.contains("Seat 2")) {
                currentHand.SB = playerName;
                currentHand.sbStack = stacksize;
            } else if (seatInfo.contains("Seat 4")) {
                currentHand.BB = playerName;
                currentHand.bbStack = stacksize;
            } else if (seatInfo.contains("Seat 6")) {
                currentHand.HJ = playerName;
                currentHand.hjStack = stacksize;
            } else if (seatInfo.contains("Seat 7")) {
                currentHand.CO = playerName;
                currentHand.coStack = stacksize;
            }
        }


    }

    public static String addPosToAction(String playerName, PokerHand currentHand) {
        if (playerName.equals(currentHand.LJ)) {
            return "LJ";
        } else if (playerName.equals(currentHand.HJ)) {
            return "HJ";
        } else if (playerName.equals(currentHand.CO)) {
            return "CO";
        } else if (playerName.equals(currentHand.BTN)) {
            return "BTN";
        } else if (playerName.equals(currentHand.SB)) {
            return "SB";
        } else if (playerName.equals(currentHand.BB)) {
            return "BB";
        } else {
            return "Unknown";
        }
    }

    //extracts which seat the btn is in a normal game.

    public static void extractButtonPosition(String line, PokerHand currentHand) {
        if (line.contains("is the button")) {
            String[] parts = line.split(" ");
            String seatNumber = "Seat " + parts[1];
            currentHand.btnSeat = seatNumber;
        }
    }

    public static void allocatePosInNormalGameFormat(String line, PokerHand currentHand, BufferedReader br) throws IOException {
        extractButtonPosition(line, currentHand);
        line = br.readLine();
        int btnindex = -1;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null){
            if (!line.startsWith("Seat")){
                break;
            }else{
                lines.add(line);
            }
        }

        for(int i = 0; i< lines.size(); i++){
            if (lines.get(i).contains(currentHand.btnSeat)){
                btnindex = i;
                currentHand.BTN = extractNameNormal(lines.get(i));
                break;
            }
        }
        if (lines.size() > 2) {
            currentHand.SB = extractNameNormal(lines.get((btnindex + 1) % lines.size()));
            currentHand.sbStack = extractStackNormal(lines.get((btnindex + 1) % lines.size()));
            currentHand.BB = extractNameNormal(lines.get((btnindex + 2) % lines.size()));
            currentHand.bbStack = extractStackNormal(lines.get((btnindex + 2) % lines.size()));
        }

        if (lines.size() >= 4) {
            currentHand.CO = extractNameNormal(lines.get((btnindex + (lines.size() - 1)) % lines.size()));
            currentHand.coStack = extractStackNormal(lines.get((btnindex + (lines.size() - 1)) % lines.size()));
        }

        if (lines.size() >= 5) {
            currentHand.HJ = extractNameNormal(lines.get((btnindex + (lines.size() - 2)) % lines.size()));
            currentHand.hjStack = extractStackNormal(lines.get((btnindex + (lines.size() - 2)) % lines.size()));
        }

        if (lines.size() >= 6) {
            currentHand.LJ = extractNameNormal(lines.get((btnindex + (lines.size() - 3)) % lines.size()));
            currentHand.ljStack = extractStackNormal(lines.get((btnindex + (lines.size() - 3)) % lines.size()));
        }

        if (lines.size() >= 7) {

            currentHand.UTG2 = extractNameNormal(lines.get((btnindex + (lines.size() - 6)) % lines.size()));
            currentHand.utg2Stack = extractStackNormal(lines.get((btnindex + (lines.size() - 6)) % lines.size()));
        }

        if (lines.size() >= 8) {
            currentHand.UTG1 = extractNameNormal(lines.get((btnindex + (lines.size() - 5)) % lines.size()));
            currentHand.utg1Stack = extractStackNormal(lines.get((btnindex + (lines.size() - 5)) % lines.size()));
        }

        if (lines.size() >= 9) {
            currentHand.UTG = extractNameNormal(lines.get((btnindex + (lines.size() - 4)) % lines.size()));
            currentHand.utgStack = extractStackNormal(lines.get((btnindex + (lines.size() - 4)) % lines.size()));
        }


    }
    public static String extractNameNormal(String line){
        String[] parts = line.split(": ");
        String playerName = parts[1].split(" ")[0];
        return playerName;
    }

    public static double extractStackNormal(String line){
        String[] parts = line.split(": ");
        double stacksize = Double.parseDouble(parts[1].split(" ")[2].
                replace("(", "").replace("$", "").replace(")", ""));

        return stacksize;
    }
    // Print extracted hands for verification
    /*public static void printResults(List<PokerHand> pokerHands){

        for (PokerHand hand : pokerHands) {
            System.out.println("Hand Number: " + hand.handNumber);
            System.out.println("Blinds: " + hand.blinds);
            System.out.println("Gametype: " + hand.gameFormat + " " + hand.pokerSite + " " + hand.maxPlayers);
            System.out.println("Hole Cards: " + String.join(", ", hand.holeCards));
            System.out.println("Board Cards: " + String.join(", ", hand.boardCards));
            System.out.println("Actions: " + String.join(", ", hand.actions));
            System.out.println();
            System.out.println("Preflop Action: ");
            for (String act: hand.preflopAction){
                System.out.print(act + ", ");
            }
            System.out.println();
            System.out.println("Flop Action: ");
            for (String act: hand.flopAction){
                System.out.print(act + ", ");
            }
            System.out.println();
            System.out.println("Turn Action: ");
            for (String act: hand.turnAction){
                System.out.print(act + ", ");
            }
            System.out.println();
            System.out.println("River Action: ");
            for (String act: hand.riverAction){
                System.out.print(act + ", ");
            }
            System.out.println();
            System.out.println(hand.blinds);
            System.out.println();
        }

    }
    */




}