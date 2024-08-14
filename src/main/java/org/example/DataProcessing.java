package org.example;

import java.util.*;

public class  DataProcessing {

    public List<PokerHandExtractor.PokerHand> pokerHands;
    public List<DataBlock> data = new ArrayList<>();

    public DataProcessing(List<PokerHandExtractor.PokerHand> pokerHands) {
        this.pokerHands = pokerHands;
    }
    //Allgemein

    public static double convertInBB(PokerHandExtractor.PokerHand hand){
        String [] parts = hand.blinds.split("/");
        double bb = Double.parseDouble(parts[1]);
        return bb;
    }

    public void processBoardCards(DataBlock block, PokerHandExtractor.PokerHand hand){
        List<Integer> flopcards = new ArrayList<>();

        if(hand.boardCards[0] != null|| hand.boardCards[1] != null||hand.boardCards[2] != null){
            int flop1 = cardToValue(hand.boardCards[0]);
            int flop2 = cardToValue(hand.boardCards[1]);
            int flop3 = cardToValue(hand.boardCards[2]);
            flopcards.add(flop1);
            flopcards.add(flop2);
            flopcards.add(flop3);
            Collections.sort(flopcards);

        }else{
            block.setHFlopCard(0);
        }
        block.setHFlopCard(flopcards.get(0));
        block.setMFlopCard(flopcards.get(1));
        block.setLFlopCard(flopcards.get(2));
        //Turn
        if(hand.boardCards[3] != null){
            block.setTurn(cardToValue(hand.boardCards[3]));
        }else{
            block.setTurn(0);
        }
        //River
        if(hand.boardCards[3] != null){
            block.setTurn(cardToValue(hand.boardCards[3]));
        }else{
            block.setTurn(0);
        }
        block.setRiver(cardToValue(hand.boardCards[4]));

        // pairedFlop/TripFlop/"normal" Flop
        if (flopcards.size() >= 3) {
            int card1 = flopcards.get(0);
            int card2 = flopcards.get(1);
            int card3 = flopcards.get(2);

            if (card1 == card2 && card2 == card3) {
                block.setTripFlop(true);
                block.setPairedFlop(false);
            } else if (card1 != card2 && card1 != card3 && card2 != card3) {
                block.setTripFlop(false);
                block.setPairedFlop(false);
            } else {
                block.setTripFlop(false);
                block.setPairedFlop(true);
            }
        }

    }

    public int cardToValue(String card){
        char rank = card.charAt(0);

        switch(rank){
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            case 'A': return 14;
            default:
                System.out.println("This is not a card. Error in DataProcessing: line 30");
                return 1;
        }
    }

    public String processStakes(PokerHandExtractor.PokerHand hand){
        return hand.blinds;
    }

    public void gameFormat(DataBlock block, PokerHandExtractor.PokerHand hand){
        block.setGameFormat(hand.gameFormat);
    }

    public void texture(PokerHandExtractor.PokerHand hand, DataBlock block){
        List<Character> suits = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (hand.boardCards[i] != null && hand.boardCards[i].length() > 1) {
                char suit = hand.boardCards[i].charAt(hand.boardCards[i].length() - 1);
                suits.add(suit);
            }
        }
        Set<Character> uniqueSuits = new HashSet<>(suits);
        int uniqueSuitCount = uniqueSuits.size();

        // Bestimme die Flop-Textur basierend auf der Anzahl der einzigartigen Suits
        if (uniqueSuitCount == 1) {
            block.setFlopTexture("monotone");
        } else if (uniqueSuitCount == 3) {
            block.setFlopTexture("rainbow");
        } else {
            block.setFlopTexture("two-tone");
        }

    }
    public static void fullAction (PokerHandExtractor.PokerHand hand, DataBlock block){
        String action = "";
        for (String a:hand.preflopAction
             ) {

            action += a+ ":";
        }
        action += ":FLOP:";
        for (String a:hand.flopAction
        ) {

            action += a+ ":";
        }
        action += ":TURN:";
        for (String a:hand.turnAction
        ) {

            action += a+ ":";
        }
        action += ":RIVER:";
        for (String a:hand.riverAction
        ) {

            action += a + ":";
        }
        block.setFullAction(action);


    }
    //Preflop

    public static String determineORPos(String action) {
        // Split the action string into individual actions
        String[] actions = action.split(":");

        // Iterate over the actions to find the first raise
        for (String act : actions) {
            if(act.contains("FLOP")){
                break;
            }
            if (act.contains("_r")) {
                // Return the position part of the action, before '_r'
                return act.split("_")[0];
            }
        }

        // If no raise is found, return "No Open Raise"
        return "NoOpenRaise";
    }

    public static double determineORinBB(PokerHandExtractor.PokerHand hand, DataBlock block) {
        // Split the action string into individual actions
        String[] actions = block.getFullAction().split(":");

        // Iterate over the actions to find the first raise
        for (String act : actions) {
            if (act.contains("FLOP")){
                break;
            }
            if (act.contains("_r")) {
                // Extract the amount after '_r' and convert it to a double
                String raiseAmount = act.split("_r")[1];
                return Double.parseDouble(raiseAmount)/convertInBB(hand);
            }
        }
        // If no raise is found, return -1 to indicate no open raise
        return -1.0;
    }
    public double determineThreeBetBB(String action) {
        String[] actions = action.split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("_r")) {
                raiseCount++;
                if (raiseCount == 2) {  // The second raise is the 3-Bet
                    return Double.parseDouble(act.split("_r")[1]);
                }
            }
        }

        // If no 3-Bet is found, return -1 to indicate no 3-Bet
        return -1.0;
    }
    public double determineFourBetBB(String action) {
        String[] actions = action.split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("_r")) {
                raiseCount++;
                if (raiseCount == 3) {  // The third raise is the 4-Bet
                    return Double.parseDouble(act.split("_r")[1]);
                }
            }
        }

        // If no 4-Bet is found, return -1 to indicate no 4-Bet
        return -1.0;
    }
    public static String determineCallPos(String action) {
        String[] actions = action.split(":");
        List<String> callers = new ArrayList<>();
        boolean raiseOccurred = false;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("r")) {
                raiseOccurred = true;  // Raise detected, start counting calls
            }
            if (raiseOccurred && act.contains("_c")) {
                callers.add(act.split("_")[0]);  // Add the position part of the action
            }
        }

        if (callers.size() == 1) {
            return callers.get(0);  // Return the single caller's position
        } else if (callers.size() > 1) {
            return String.valueOf(callers.size());  // Return the number of callers as a String
        }

        // If no valid caller is found, return "0"
        return "0";
    }
    public String determinePotType(String action) {
        String[] actions = action.split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("_r")) {
                raiseCount++;
            }
        }

        switch (raiseCount) {
            case 0:
                return "LimpedPot";
            case 1:
                return "SRP";  // Single Raised Pot
            case 2:
                return "3BetPot";  // 3-Bet Pot
            case 3:
                return "4BetPot";  // 4-Bet Pot
            default:
                return "Other";  // More than 4-Bet or no raises
        }
    }
    public static boolean isIsoraisePot(String fullAction) {
        // Split the action string into individual actions
        String[] actions = fullAction.split(":");

        boolean limpFound = false;

        for (String act : actions) {
            if (act.contains("FLOP")){
                break;
            }
            if (act.contains("_x")) {
                limpFound = true;  // Markiere, dass ein Limp gefunden wurde
            } else if (limpFound && act.contains("_r")) {
                // Wenn ein Limp gefunden wurde und danach ein Raise kommt, ist es ein Isoraise
                return true;
            }
        }

        // Wenn kein Limp gefolgt von einem Raise gefunden wird, ist es kein Isoraise
        return false;
    }
    public static String determineLastAggressorPreflop(String fullAction) {
        String[] actions = fullAction.split(":");
        String lastAggressor = "NoAggressor";  // Initialisierung auf einen Standardwert

        for (String act : actions) {
            if(act.contains("FLOP")){
                break;
            }
            if (act.contains("_r")) {
                lastAggressor = act.split("_")[0];  // Speichere die Position des Aggressors
            }
        }

        return lastAggressor;
    }

    public static int determineAggrIP(String action) {
        String[] actions = action.split(":");


        String caller = determineCallPos(action);
        String lastAggressor = determineLastAggressorPreflop(action);
        int aggrPos = 0;
        int callPos = 0;

        switch (caller){
            case "SB": callPos = 1;break;
            case "BB": callPos = 2;break;
            case "UTG": callPos = 3;break;
            case "UTG1": callPos = 4;break;
            case "UTG2": callPos = 5;break;
            case "LJ": callPos = 6;break;
            case "HJ": callPos = 7;break;
            case "CO": callPos = 8;break;
            case "BTN": callPos = 9;break;
            default: callPos = 0;
        }
        switch (lastAggressor){
            case "SB": aggrPos = 1;break;
            case "BB": aggrPos = 2;break;
            case "UTG": aggrPos = 3;break;
            case "UTG1": aggrPos = 4;break;
            case "UTG2": aggrPos = 5;break;
            case "LJ": aggrPos = 6;break;
            case "HJ": aggrPos = 7;break;
            case "CO": aggrPos = 8;break;
            case "BTN": aggrPos = 9;break;
            default: aggrPos = 0;
        }

        if(aggrPos - callPos > 0 && aggrPos != 0 && callPos != 0){
            return 1;
        }else if(aggrPos - callPos < 0 && aggrPos != 0 && callPos != 0){
            return 0;
        }else{
            return -1;
        }

    }
     //Flop-------------------------------------------------------------------------------------------------------

     // Anzahl der Spieler, die den Flop sehen
     public static int countFlopPlayers(String action) {
         String[] actions = action.split(":");


         for (String act : actions) {
             if (act.contains("FLOP")) {
                 break;
             }
             if (determineCallPos(action).equals("SB")||determineCallPos(action).equals("BB")||determineCallPos(action).equals("UTG")
                     ||determineCallPos(action).equals("UTG1")||determineCallPos(action).equals("UTG2")||determineCallPos(action).equals("LJ")
                     ||determineCallPos(action).equals("HJ")||determineCallPos(action).equals("CO")||determineCallPos(action).equals("BTN")
             ) {
                 return 2;
             }else{
                 try{
                     int player = Integer.parseInt(determineCallPos(action));
                     return player + 1;
                 }catch (Exception e){
                     return -1;
                 }
             }
         }
         return -1;
     }

    // Gibt es eine C-Bet?
    public static boolean isCBet(String action) {
        String[] actions = action.split(":");
        boolean isFlop = false;

        for (String act : actions) {

            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if(act.contains("TURN")){
                break;
            }
            if (isFlop && act.contains(determineLastAggressorPreflop(action) + "_b") ) {
                return true;  // Wenn es einen Raise oder Bet gibt, handelt es sich um eine C-Bet
            }
        }

        return false;
    }

    // Gibt es ein C-Bet Raise?
    public static boolean isCBetRaise(String action) {
        if(!isCBet(action)){
            return false;
        }
        String[] actions = action.split(":");

        boolean isFlop = false;

        for (String act : actions) {

            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if(act.contains("TURN")){
                break;
            }
            if (isFlop && act.contains("_r")) {
                return true;  // Wenn nach einer C-Bet ein Raise kommt, ist es ein C-Bet Raise
            }

        }

        return false;
    }

    // Gibt es eine C-Bet Fold?
    public static boolean isCBetFold(String action) {
        //TODO funktioniert multiway nicht
        if(!isCBet(action)){
            return false;
        }
        if(determineAggrIP(action) == -1){
            return false;
        }
        String[] actions = action.split(":");
        boolean isFlop = false;
        for (String act : actions) {
            if(act.contains("TURN")){
                break;
            }
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (isFlop && act.contains("_r")) {
                return false;
            }
            if (isFlop && act.contains("_f")) {
                return true;  // Wenn nach einer C-Bet ein Fold kommt, ist es ein C-Bet Fold
            }
        }
        return false;
    }

    // Bet after Check (macht nur Sinn, wenn der Aggressor OOP ist)
    public static boolean isBetAfterCheck(String action) {
        boolean isChecked = false;
        boolean isFlop = false;
        if(determineAggrIP(action) == 1 || determineAggrIP(action) == -1){
            return false;
        }
        String[] actions = action.split(":");
        for (String act : actions) {
            if(act.contains("TURN")){

                break;
            }
            if (act.contains("FLOP")) {

                isFlop = true;
            }
            if(isFlop && act.contains("_x")){

                isChecked = true;
            }
            if(isFlop && isChecked && act.contains("_b")){

                return true;
            }
        }

        return false;
    }

    //TURN------------------------------------------------------------------------------------------------------------
    public static boolean isTurnBarrel(String action) {
        if(!isCBet(action)){
            return false;
        }
        boolean isTurn = false;
        String[] actions = action.split(":");
        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains(determineLastAggressorPreflop(action) + "_b")) {
                return true;
            }
        }

        return false;
    }

    // Wurde am Turn nach einer Bet gefoldet?
    public static boolean isFoldToTurnBarrel(String action) {
        if (!isTurnBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains("_r")) {
                return false;
            }
            if (isTurn && act.contains("_f")) {
                return true; // Fold nach Turn-Bet
            }
        }

        return false;
    }

    // Gab es ein Raise auf die Turn-Bet?
    public static boolean isRaiseBarrel(String action) {
        if (!isTurnBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains("_r")) {
                return true; // Raise nach Turn-Bet
            }
        }

        return false;
    }
    public static boolean isCallTurnBarrel(String action) {
        if (!isTurnBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains("_c")) {
                return true; // Call auf die Turn-Bet erkannt
            }
        }
        return false;
    }
    // Gab es eine Bet nach einem Check am Turn? (nur sinnvoll, wenn der Aggressor OOP ist)
    public static boolean isBetAfterCheckTurn(String action) {
        if(determineAggrIP(action) == 1 || determineAggrIP(action) == -1){
            return false;
        }
        String[] actions = action.split(":");
        boolean isTurn = false;
        boolean checkOccurred = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains(determineLastAggressorPreflop(action) + "_x")) {
                checkOccurred = true; // Check am Turn erkannt
            }
            if (checkOccurred && act.contains("_b")) {
                return true; // Bet nach Check am Turn erkannt
            }
        }

        return false;
    }
    //RIVER-------------------------------------------------------------------------------------------

    // Gab es eine Bet nach einem Check am River? (nur sinnvoll, wenn der Aggressor OOP ist)
    public static boolean isBetAfterCheckRiver(String action) {
        if(determineAggrIP(action) == 1 || determineAggrIP(action) == -1){
            return false;
        }
        String[] actions = action.split(":");
        boolean isRiver = false;
        boolean checkOccurred = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(action) + "_x")) {
                checkOccurred = true; // Check am River erkannt
            }
            if (checkOccurred && act.contains("_b")) {
                return true; // Bet nach Check am River erkannt
            }
        }

        return false;
    }

    // Gab es einen Check nach einer Turn-Bet?
    public static boolean isCheckRiverAfterTurnBarrel(String action) {
        if (!isTurnBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(action) + "_x")) {
                return true; // Check nach Turn-Bet erkannt
            }
        }
        return false;
    }

    // Gab es eine Bet nach einer Turn-Bet am River?
    public static boolean isTripleBarrel(String action) {
        if (!isTurnBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(action) + "_b")) {
                return true; // Bet nach Turn-Bet am River erkannt
            }
        }
        return false;
    }

    // Gab es einen Call auf die 3. Barrel am River?
    public static boolean isCall3Barrel(String action) {
        if (!isTripleBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains("_c")) {
                return true; // Call auf die 3. Barrel erkannt
            }
        }
        return false;
    }
    public static boolean isRaise3Barrel(String action) {
        if (!isTripleBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            //System.out.println(isRiver + ", " + isBetAfterTurnBarrel(action) + ", " + act.contains("_r"));
            if (isRiver && act.contains("_r")) {
                return true; // Raise auf die Triple Barrel am River erkannt
            }
        }
        return false;
    }
    // Gab es einen Fold auf die Triple Barrel am River?
    public static boolean isFoldTo3Barrel(String action) {
        if (!isTripleBarrel(action)) {
            return false;
        }

        String[] actions = action.split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains("_f")) {
                return true; // Fold auf die Triple Barrel am River erkannt
            }
        }

        return false;
    }
}
