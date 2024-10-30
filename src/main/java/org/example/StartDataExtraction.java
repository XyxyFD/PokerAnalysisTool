package org.example;

import java.util.ArrayList;
import java.util.List;

public class StartDataExtraction {
    public static void start(String filepath){
        List<PokerHand> pokerHands = PokerHandExtractor.extract(filepath);


        // Erstelle eine Liste für die Datenblöckea
        List<DataBlock> dataBlocks = new ArrayList<>();

        System.out.println("Processing Data....");

        // Schleife über alle extrahierten Hände
        for (PokerHand hand : pokerHands) {
            if(hand.blinds == null){
                continue;
            }

            // Erstelle einen neuen Datenblock für jede Hand
            DataBlock block = new DataBlock();

            // Verarbeite allgemeine Daten
            DataProcessing.processStakes(hand, block);
            DataProcessing.processBoardCards(block, hand);
            DataProcessing.texture(hand, block);
            DataProcessing.fullAction(hand, block);

            // Verarbeite Preflop-Daten
            DataProcessing.determineORPos(hand, block);
            DataProcessing.determineORinBB(hand, block);
            DataProcessing.determineThreeBetBB(hand, block);
            DataProcessing.determineFourBetBB(hand, block);
            DataProcessing.determineCallPos(hand, block);
            DataProcessing.determinePotType(hand, block);
            DataProcessing.isIsoraisePot(hand, block);
            DataProcessing.determineAggrIP(hand, block);

            // Verarbeite Flop-Daten
            DataProcessing.countFlopPlayers(hand, block);
            DataProcessing.isCBet(hand, block);
            DataProcessing.isCbetCalled(hand, block);
            DataProcessing.isCBetRaise(hand, block);
            DataProcessing.isCBetFold(hand, block);
            DataProcessing.isBetAfterCheck(hand, block);
            DataProcessing.isBetAfterFlopCheckOOP(hand, block);

            // Verarbeite Turn-Daten
            DataProcessing.isTurnBarrel(hand, block);
            DataProcessing.isFoldToTurnBarrel(hand, block);
            DataProcessing.isRaiseBarrel(hand, block);
            DataProcessing.isCallTurnBarrel(hand, block);
            DataProcessing.isBetAfterCheckTurn(hand, block);

            // Verarbeite River-Daten
            DataProcessing.isBetAfterCheckRiver(hand, block);
            DataProcessing.isCheckRiverAfterTurnBarrel(hand, block);
            DataProcessing.isTripleBarrel(hand, block);
            DataProcessing.isCall3Barrel(hand, block);
            DataProcessing.isRaise3Barrel(hand, block);
            DataProcessing.isFoldTo3Barrel(hand, block);

            // Füge den verarbeiteten Datenblock zur Liste hinzu
            dataBlocks.add(block);
        }

        System.out.println("Inserting....");
        // Optional: Drucke alle verarbeiteten Datenblöcke aus
        for (DataBlock block : dataBlocks) {
            DatabaseInserter.insertDataBlock(block);
        }

        System.out.println("Inserting finished.");
    }
}
