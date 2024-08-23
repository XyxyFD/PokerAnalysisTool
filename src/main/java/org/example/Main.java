package org.example;

import org.example.DataBlock;
import org.example.DataProcessing;
import org.example.PokerHandExtractor;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Extrahiere alle Pokerhände aus dem PokerHandExtractor
        List<PokerHandExtractor.PokerHand> pokerHands = PokerHandExtractor.extract();
        System.out.println(pokerHands.get(0).boardCards);
        for (String card: pokerHands.get(1).boardCards) {
            System.out.println(card);
        }

        // Erstelle eine Liste für die Datenblöcke
        List<DataBlock> dataBlocks = new ArrayList<>();

        // Schleife über alle extrahierten Hände
        for (PokerHandExtractor.PokerHand hand : pokerHands) {
            // Erstelle einen neuen Datenblock für jede Hand
            DataBlock block = new DataBlock();

            // Verarbeite allgemeine Daten
            DataProcessing.processStakes(hand, block);
            DataProcessing.gameFormat(hand, block);
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

        // Optional: Drucke alle verarbeiteten Datenblöcke aus
        for (DataBlock block : dataBlocks) {
            System.out.println(block.toString());
        }

        // Die Datenblöcke können jetzt in die Datenbank geschrieben werden
    }
}
