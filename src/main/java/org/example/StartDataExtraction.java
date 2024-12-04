package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartDataExtraction {
    public static void start(String filepath) {
        List<PokerHand> pokerHands = PokerHandExtractor.extract(filepath);

        List<DataBlock> dataBlocks = Collections.synchronizedList(new ArrayList<>());

        System.out.println("Processing Data in Parallel...");
        pokerHands.parallelStream().forEach(hand -> {
            if (hand.blinds != null) {
                DataBlock block = new DataBlock();
                DataProcessing.processStakes(hand, block);
                DataProcessing.processBoardCards(block, hand);
                DataProcessing.texture(hand, block);
                DataProcessing.fullAction(hand, block);
                DataProcessing.determineORPos(hand, block);
                DataProcessing.determineORinBB(hand, block);
                DataProcessing.determineThreeBetBB(hand, block);
                DataProcessing.determineFourBetBB(hand, block);
                DataProcessing.determineCallPos(hand, block);
                DataProcessing.determinePotType(hand, block);
                DataProcessing.isIsoraisePot(hand, block);
                DataProcessing.determineAggrIP(hand, block);
                DataProcessing.countFlopPlayers(hand, block);
                DataProcessing.isCBet(hand, block);
                DataProcessing.isCbetCalled(hand, block);
                DataProcessing.isCBetRaise(hand, block);
                DataProcessing.isCBetFold(hand, block);
                DataProcessing.isBetAfterCheck(hand, block);
                DataProcessing.isTurnBarrel(hand, block);
                DataProcessing.isStabTurn(hand, block);
                DataProcessing.isFoldToTurnBarrel(hand, block);
                DataProcessing.isRaiseBarrel(hand, block);
                DataProcessing.isCallTurnBarrel(hand, block);
                DataProcessing.isBetAfterCheckTurn(hand, block);
                DataProcessing.isDelayedCbet(hand, block);
                DataProcessing.isBetAfterCheckRiver(hand, block);
                DataProcessing.isStabRiver(hand, block);
                DataProcessing.isBarrelAfterDelayedCbet(hand, block);
                DataProcessing.isCheckRiverAfterTurnBarrel(hand, block);
                DataProcessing.isTripleBarrel(hand, block);
                DataProcessing.isCall3Barrel(hand, block);
                DataProcessing.isRaise3Barrel(hand, block);
                DataProcessing.isFoldTo3Barrel(hand, block);
                DataProcessing.analyzeFlushTexture(hand, block);

                synchronized (dataBlocks) {
                    dataBlocks.add(block);
                }
            }
        });

        System.out.println("Inserting Data...");
        DatabaseInserter.insertDataBlocks(dataBlocks);
        System.out.println("Inserting finished.");
    }


}
