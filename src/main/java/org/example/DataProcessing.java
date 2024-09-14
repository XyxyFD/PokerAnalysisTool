package org.example;

import java.util.*;

public class  DataProcessing {


    //Allgemein

    public static double convertInBB(PokerHandExtractor.PokerHand hand){
        String [] parts = hand.blinds.split("/");
        double bb = Double.parseDouble(parts[1]);
        return bb;
    }

    // Verarbeitet die Boardkarten und speichert sie im DataBlock
    public static void processBoardCards(DataBlock block, PokerHandExtractor.PokerHand hand) {
        List<Integer> flopcards = new ArrayList<>();

        // Füge die Flopkarten hinzu, wenn sie vorhanden sind
        if (hand.boardCards[0] != null) {
            flopcards.add(cardToValue(hand.boardCards[0]));
        }
        if (hand.boardCards[1] != null) {
            flopcards.add(cardToValue(hand.boardCards[1]));
        }
        if (hand.boardCards[2] != null) {
            flopcards.add(cardToValue(hand.boardCards[2]));
        }

        // Wenn weniger als 3 Karten vorhanden sind, setze die Werte im Block auf 0
        if (flopcards.size() < 3) {
            block.setHFlopCard(0);
            block.setMFlopCard(0);
            block.setLFlopCard(0);
        } else {

            flopcards.sort(Comparator.reverseOrder());
            block.setHFlopCard(flopcards.get(0));
            block.setMFlopCard(flopcards.get(1));
            block.setLFlopCard(flopcards.get(2));
        }

        // Turn
        if (hand.boardCards[3] != null) {
            block.setTurn(cardToValue(hand.boardCards[3]));
        } else {
            block.setTurn(0);
        }

        // River
        if (hand.boardCards[4] != null) {
            block.setRiver(cardToValue(hand.boardCards[4]));
        } else {
            block.setRiver(0);
        }

        // Überprüfe auf Paired Flop oder Trip Flop
        if (flopcards.size() == 3) {
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
        } else {
            block.setTripFlop(false);
            block.setPairedFlop(false);
        }
    }



    public static int cardToValue(String card) {
        if (card == null || card.isEmpty()) {
            System.out.println("Invalid card: " + card);
            return -1;
        }

        char rank = card.charAt(0);

        switch (rank) {
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                System.out.println("This is not a valid card rank: " + card + ", " + "Rank: " +  rank);
                return -1;
        }
    }

    // Verarbeitet die Einsätze (Stakes) und speichert sie im DataBlock
    public static void processStakes(PokerHandExtractor.PokerHand hand, DataBlock block) {
        block.setStakes(hand.blinds);
    }

    public static void gameFormat(PokerHandExtractor.PokerHand hand, DataBlock block) {
        block.setGameFormat(hand.gameFormat);
    }
    // Verarbeitet die Textur des Flops und speichert sie im DataBlock
    public static void texture(PokerHandExtractor.PokerHand hand, DataBlock block) {
        List<Character> suits = new ArrayList<>();

        // Überprüfe, ob der Flop existiert
        if (hand.boardCards[0] == null || hand.boardCards[1] == null || hand.boardCards[2] == null) {
            block.setFlopTexture("NoFlop");
            return;
        }

        // Sammle die Suits der Flop-Karten
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
        } else if (uniqueSuitCount == 2) {
            block.setFlopTexture("two-tone");
        }
    }

    public static void fullAction(PokerHandExtractor.PokerHand hand, DataBlock block) {
        StringBuilder action = new StringBuilder();

        for (String a : hand.preflopAction) {
            action.append(a).append(":");
        }

        action.append(":FLOP:");
        for (String a : hand.flopAction) {
            action.append(a).append(":");
        }

        action.append(":TURN:");
        for (String a : hand.turnAction) {
            action.append(a).append(":");
        }

        action.append(":RIVER:");
        for (String a : hand.riverAction) {
            action.append(a).append(":");
        }

        block.setFullAction(action.toString());
    }
    //Preflop

    // Bestimmt die Position des Open Raisers und speichert sie im DataBlock
    public static void determineORPos(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("_r")) {
                block.setORPos(act.split("_")[0]);
                return;
            }
        }

        block.setORPos("NoOR");
    }

    // Bestimmt den Betrag des Open Raises in Big Blinds und speichert ihn im DataBlock
    public static void determineORinBB(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("_r")) {
                String raiseAmount = act.split("_r")[1];
                block.setORinBB(Double.parseDouble(raiseAmount) / convertInBB(hand));
                return;
            }
        }

        block.setORinBB(-1.0);
    }
    // Bestimmt den Betrag der 3-Bet in Big Blinds und speichert ihn im DataBlock
    public static void determineThreeBetBB(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("_r")) {
                raiseCount++;
                if (raiseCount == 2) {  // Die zweite Erhöhung ist die 3-Bet
                    block.setThreeBetBB(Double.parseDouble(act.split("_r")[1]) / convertInBB(hand));
                    return;
                }
            }
        }

        block.setThreeBetBB(-1.0);
    }

    // Bestimmt den Betrag der 4-Bet in Big Blinds und speichert ihn im DataBlock
    public static void determineFourBetBB(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("_r")) {
                raiseCount++;
                if (raiseCount == 3) {  // Die dritte Erhöhung ist die 4-Bet
                    block.setFourBetBB(Double.parseDouble(act.split("_r")[1]) / convertInBB(hand));
                    return;
                }
            }
        }

        block.setFourBetBB(-1.0);
    }
    public static String determineCallPos(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");
        List<String> callers = new ArrayList<>();
        boolean raiseOccurred = false;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("r")) {
                raiseOccurred = true;
                callers.clear();
            }
            if (raiseOccurred && act.contains("_c")) {
                callers.add(act.split("_")[0]);
            }
        }

        if (callers.size() == 1) {
            block.setCallPos(callers.get(0));
            return callers.get(0);  // Return the single caller's position
        } else if (callers.size() > 1) {
            block.setCallPos(String.valueOf(callers.size()));
            return String.valueOf(callers.size());  // Return the number of callers as a String
        } else {
            block.setCallPos("0");
            return "0";
        }
    }
    public static void determinePotType(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");
        int raiseCount = 0;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("_r")) {
                raiseCount++;
            }
        }

        switch (raiseCount) {
            case 0:
                block.setPotType("LimpedPot");
                break;
            case 1:
                block.setPotType("SRP");
                break;
            case 2:
                block.setPotType("3BetPot");
                break;
            case 3:
                block.setPotType("4BetPot");
                break;
            default:
                block.setPotType("Other");
        }
    }
    // Bestimmt, ob es sich um einen Isoraise-Pot handelt und speichert das Ergebnis im DataBlock
    public static void isIsoraisePot(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");

        boolean limpFound = false;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (act.contains("_c")) {
                limpFound = true;
            } else if (limpFound && act.contains("_r")) {
                block.setIsoraisePot(true);
                return;
            }
        }

        block.setIsoraisePot(false);
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

    public static int determineAggrIP(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");

        String caller = block.getCallPos();
        String lastAggressor = determineLastAggressorPreflop(block.getFullAction());
        int aggrPos = 0;
        int callPos = 0;

        switch (caller) {
            case "SB":
                callPos = 1;
                break;
            case "BB":
                callPos = 2;
                break;
            case "UTG":
                callPos = 3;
                break;
            case "UTG1":
                callPos = 4;
                break;
            case "UTG2":
                callPos = 5;
                break;
            case "LJ":
                callPos = 6;
                break;
            case "HJ":
                callPos = 7;
                break;
            case "CO":
                callPos = 8;
                break;
            case "BTN":
                callPos = 9;
                break;
            default:
                callPos = 0;
        }

        switch (lastAggressor) {
            case "SB":
                aggrPos = 1;
                break;
            case "BB":
                aggrPos = 2;
                break;
            case "UTG":
                aggrPos = 3;
                break;
            case "UTG1":
                aggrPos = 4;
                break;
            case "UTG2":
                aggrPos = 5;
                break;
            case "LJ":
                aggrPos = 6;
                break;
            case "HJ":
                aggrPos = 7;
                break;
            case "CO":
                aggrPos = 8;
                break;
            case "BTN":
                aggrPos = 9;
                break;
            default:
                aggrPos = 0;
        }
        //TODO vielleicht besser kein boolean wert
        if (aggrPos - callPos > 0 && aggrPos != 0 && callPos != 0) {
            block.setAggrIP(true);
            return 1;
        } else if (aggrPos - callPos < 0 && aggrPos != 0 && callPos != 0) {
            block.setAggrIP(false);
            return 0;
        } else {
            block.setAggrIP(false);
            return -1;
        }
    }
     //Flop-------------------------------------------------------------------------------------------------------

    // Bestimmt die Anzahl der Spieler, die den Flop sehen, und speichert sie im DataBlock
    public static void countFlopPlayers(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("FLOP")) {
                break;
            }
            if (determineCallPos(hand, block).equals("SB") || determineCallPos(hand, block).equals("BB") ||
                    determineCallPos(hand, block).equals("UTG") || determineCallPos(hand, block).equals("UTG1") ||
                    determineCallPos(hand, block).equals("UTG2") || determineCallPos(hand, block).equals("LJ") ||
                    determineCallPos(hand, block).equals("HJ") || determineCallPos(hand, block).equals("CO") ||
                    determineCallPos(hand, block).equals("BTN")) {
                block.setFlopPlayers(2);
                return;
            } else {
                try {
                    int player = Integer.parseInt(determineCallPos(hand, block));
                    block.setFlopPlayers(player + 1);
                    return;
                } catch (Exception e) {
                    block.setFlopPlayers(-1);
                    return;
                }
            }
        }
        block.setFlopPlayers(-1);
    }

    // Gibt es eine C-Bet?
    public static void isCBet(PokerHandExtractor.PokerHand hand, DataBlock block) {
        String[] actions = block.getFullAction().split(":");
        boolean isFlop = false;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (act.contains("TURN")) {
                break;
            }
            if (isFlop && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_b")) {
                block.setCbet(true);
                return;
            }
        }

        block.setCbet(false);
    }
    public static void isCbetCalled(PokerHandExtractor.PokerHand hand, DataBlock block) {
        // Check if a C-bet was made
        if (!block.isCbet()) {
            block.setCbetCall(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isFlop = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                break;
            }
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if(isFlop && act.contains("_r")){
                block.setCall3Barrel(false);
                return;
            }
            if (isFlop && act.contains("_c")) {
                block.setCbetCall(true);
                return;
            }
        }

        block.setCbetCall(false);
    }

    // Gibt es ein C-Bet Raise?
    // Bestimmt, ob es ein C-Bet Raise am Flop gibt, und speichert das Ergebnis im DataBlock
    public static void isCBetRaise(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isCbet()) {
            block.setCbetRaise(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isFlop = false;

        for (String act : actions) {
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (act.contains("TURN")) {
                break;
            }
            if (isFlop && act.contains("_r")) {
                block.setCbetRaise(true);
                return;
            }
        }

        block.setCbetRaise(false);
    }

    // Bestimmt, ob es ein C-Bet Fold am Flop gibt, und speichert das Ergebnis im DataBlock
    public static void isCBetFold(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isCbet()) {
            block.setCbetFold(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isFlop = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                break;
            }
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (isFlop && act.contains("_r")) {
                block.setCbetFold(false);
                return;
            }
            if (isFlop && act.contains("_f")) {
                block.setCbetFold(true);
                return;
            }
        }

        block.setCbetFold(false);
    }

    // Bet after Check (macht nur Sinn, wenn der Aggressor OOP ist)
    // Bestimmt, ob es eine Bet nach einem Check am Flop gibt, und speichert das Ergebnis im DataBlock
    public static void isBetAfterCheck(PokerHandExtractor.PokerHand hand, DataBlock block) {
        boolean isChecked = false;
        boolean isFlop = false;

        if (determineAggrIP(hand, block) == 1 || determineAggrIP(hand, block) == -1) {
            block.setBetAfterCheck(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("TURN")) {
                break;
            }
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (isFlop && act.contains("_x")) {
                isChecked = true;
            }
            if (isFlop && isChecked && act.contains("_b")) {
                block.setBetAfterCheck(true);
                return;
            }
        }

        block.setBetAfterCheck(false);
    }
    //TODO Die gleiche Methode für den Turn wenn Aggr IP würde auch noch sinn machen
    public static void isBetAfterFlopCheckOOP(PokerHandExtractor.PokerHand hand, DataBlock block) {
        // Prüfen, ob der Aggressor OOP ist und keine C-Bet gemacht hat
        if (determineAggrIP(hand, block) != 0 || block.isCbet()) {
            block.setStabFlopAfterCheck(false);
            return;
        }

        boolean isChecked = false;
        boolean isFlop = false;

        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("TURN")) {
                break;
            }
            if (act.contains("FLOP")) {
                isFlop = true;
            }
            if (isFlop && act.contains("_x")) {
                isChecked = true;
            }
            if (isFlop && isChecked && act.contains("_b")) {
                block.setStabFlopAfterCheck(true);
                return;
            }
        }

        block.setStabFlopAfterCheck(false);
    }



    //TURN------------------------------------------------------------------------------------------------------------
    // Bestimmt, ob es eine Turn-Barrel gibt, und speichert das Ergebnis im DataBlock. Gibt einen Rückgabewert zurück, da diese Methode in anderen verwendet wird.
    public static boolean isTurnBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isCbet()) {
            block.setTurnBarrel(false);
            return false;
        }
        boolean isTurn = false;
        String[] actions = block.getFullAction().split(":");

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_b")) {
                block.setTurnBarrel(true);
                return true;
            }
        }

        block.setTurnBarrel(false);
        return false;
    }


    // Wurde am Turn nach einer Bet gefoldet?
    // Bestimmt, ob am Turn nach einer Bet gefoldet wurde, und speichert das Ergebnis im DataBlock
    public static void isFoldToTurnBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTurnBarrel()) {
            block.setFoldToTurnBarrel(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains("_r")) {
                block.setFoldToTurnBarrel(false);
                return;
            }
            if (isTurn && act.contains("_f")) {
                block.setFoldToTurnBarrel(true);
                return;
            }
        }

        block.setFoldToTurnBarrel(false);
    }

    // Gab es ein Raise auf die Turn-Bet?
    // Bestimmt, ob es ein Raise auf die Turn-Barrel gibt, und speichert das Ergebnis im DataBlock
    public static void isRaiseBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTurnBarrel()) {
            block.setRaiseTurnBarrel(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains("_r")) {
                block.setRaiseTurnBarrel(true);
                return;
            }
        }

        block.setRaiseTurnBarrel(false);
    }
    // Bestimmt, ob es einen Call auf die Turn-Barrel gibt, und speichert das Ergebnis im DataBlock
    public static void isCallTurnBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTurnBarrel()) {
            block.setCallTurnBarrel(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isTurn = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if(isTurn && act.contains("_r")){
                block.setCall3Barrel(false);
                return;
            }
            if (isTurn && act.contains("_c")) {
                block.setCallTurnBarrel(true);
                return;
            }
        }

        block.setCallTurnBarrel(false);
    }
    // Gab es eine Bet nach einem Check am Turn? (nur sinnvoll, wenn der Aggressor OOP ist)
    // Bestimmt, ob es eine Bet nach einem Check am Turn gibt, und speichert das Ergebnis im DataBlock
    public static boolean isBetAfterCheckTurn(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (determineAggrIP(hand, block) == 1 || determineAggrIP(hand, block) == -1) {
            block.setBetAfterCheckTurn(false);
            return false;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isTurn = false;
        boolean checkOccurred = false;

        for (String act : actions) {
            if (act.contains("TURN")) {
                isTurn = true;
            }
            if (act.contains("RIVER")) {
                break;
            }
            if (isTurn && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_x")) {
                checkOccurred = true;
            }
            if (checkOccurred && act.contains("_b")) {
                block.setBetAfterCheckTurn(true);
                return true;
            }
        }

        block.setBetAfterCheckTurn(false);
        return false;
    }
    //RIVER-------------------------------------------------------------------------------------------

    // Bestimmt, ob es eine Bet nach einem Check am River gibt, und speichert das Ergebnis im DataBlock
    public static void isBetAfterCheckRiver(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (determineAggrIP(hand, block) == 1 || determineAggrIP(hand, block) == -1) {
            block.setBetAfterCheckRiver(false);
        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;
        boolean checkOccurred = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_x")) {
                checkOccurred = true; // Check am River erkannt
            }
            if (checkOccurred && act.contains("_b")) {
                block.setBetAfterCheckRiver(true);
            }
        }

        block.setBetAfterCheckRiver(false);
    }

    // Bestimmt, ob es einen Check nach einer Turn-Bet am River gibt, und speichert das Ergebnis im DataBlock
    public static void isCheckRiverAfterTurnBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTurnBarrel()) {
            block.setCheckRiverAfterTurnBarrel(false);

        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_x")) {
                block.setCheckRiverAfterTurnBarrel(true);
            }
        }

        block.setCheckRiverAfterTurnBarrel(false);

    }

    // Gab es eine Bet nach einer Turn-Bet am River?
    // Bestimmt, ob es eine Triple Barrel (Bet nach einer Turn-Bet am River) gibt, und speichert das Ergebnis im DataBlock
    public static void isTripleBarrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTurnBarrel()) {
            block.setTripleBarrel(false);
            return;

        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains(determineLastAggressorPreflop(block.getFullAction()) + "_b")) {
                block.setTripleBarrel(true);
                return;
            }
        }

        block.setTripleBarrel(false);
    }

    // Gab es einen Call auf die 3. Barrel am River?
    // Bestimmt, ob es einen Call auf die Triple Barrel am River gibt, und speichert das Ergebnis im DataBlock
    public static void isCall3Barrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTripleBarrel()) {
            block.setCall3Barrel(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if(isRiver && act.contains("_r")){
                block.setCall3Barrel(false);
                return;
            }
            if (isRiver && act.contains("_c")) {
                block.setCall3Barrel(true);
                return; // Call auf die 3. Barrel erkannt
            }
        }

        block.setCall3Barrel(false);
    }
    // Bestimmt, ob es ein Raise auf die Triple Barrel am River gibt, und speichert das Ergebnis im DataBlock
    public static void isRaise3Barrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTripleBarrel()) {
            block.setRaise3Barrel(false);

        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains("_r")) {
                block.setRaise3Barrel(true);

            }
        }

        block.setRaise3Barrel(false);

    }
    // Bestimmt, ob es einen Fold auf die Triple Barrel am River gibt, und speichert das Ergebnis im DataBlock
    public static void isFoldTo3Barrel(PokerHandExtractor.PokerHand hand, DataBlock block) {
        if (!block.isTripleBarrel()) {
            block.setFoldTo3Barrel(false);
            return;
        }

        String[] actions = block.getFullAction().split(":");
        boolean isRiver = false;

        for (String act : actions) {
            if (act.contains("RIVER")) {
                isRiver = true;
            }
            if (isRiver && act.contains("_f")) {
                block.setFoldTo3Barrel(true);
                return; // Fold auf die Triple Barrel am River erkannt
            }
        }

        block.setFoldTo3Barrel(false);
    }
}
