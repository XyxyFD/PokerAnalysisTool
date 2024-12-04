package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseInserter {

    /*public static void insertDataBlock(DataBlock block) {
        String query = "INSERT INTO PokerHands (" +
                "blinds, site, gameFormat, H_FlopCard, M_FlopCard, L_FlopCard, " +
                "turn, river, flopTexture, pairedFlop, tripFlop, fullAction, OR_Pos, ORinBB, threeBetBB, fourBetBB, " +
                "CallPos, aggrIP, potType, isoraisePot, flopPlayers, cbet, cbetCall, cbetFold, cbetRaise, " +
                "betAfterCheck, turnBarrel, foldToTurnBarrel, callTurnBarrel, raiseTurnBarrel, " +
                "betAfterCheckTurn, betAfterCheckRiver, checkRiverAfterTurnBarrel, tripleBarrel, " +
                "call3Barrel, raise3Barrel, foldTo3Barrel, " +
                "isBarrelAfterDelayedCbet, isStabRiver, isStabTurn, isDelayedCbet, flushTexture" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, block.getStakes());
            stmt.setString(2, "win2day");
            stmt.setString(3, "NoLimitHoldem");
            stmt.setInt(4, block.getHFlopCard());
            stmt.setInt(5, block.getMFlopCard());
            stmt.setInt(6, block.getLFlopCard());
            stmt.setInt(7, block.getTurn());
            stmt.setInt(8, block.getRiver());
            stmt.setString(9, block.getFlopTexture());
            stmt.setBoolean(10, block.isPairedFlop());
            stmt.setBoolean(11, block.isTripFlop());
            stmt.setString(12, block.getFullAction());
            stmt.setString(13, block.getORPos());
            stmt.setDouble(14, block.getORinBB());
            stmt.setDouble(15, block.getThreeBetBB());
            stmt.setDouble(16, block.getFourBetBB());
            stmt.setString(17, block.getCallPos());
            stmt.setBoolean(18, block.isAggrIP());
            stmt.setString(19, block.getPotType());
            stmt.setBoolean(20, block.isIsoraisePot());
            stmt.setInt(21, block.getFlopPlayers());
            stmt.setBoolean(22, block.isCbet());
            stmt.setBoolean(23, block.isCbetCall());
            stmt.setBoolean(24, block.isCbetFold());
            stmt.setBoolean(25, block.isCbetRaise());
            stmt.setBoolean(26, block.isBetAfterCheck());
            stmt.setBoolean(27, block.isTurnBarrel());
            stmt.setBoolean(28, block.isFoldToTurnBarrel());
            stmt.setBoolean(29, block.isCallTurnBarrel());
            stmt.setBoolean(30, block.isRaiseTurnBarrel());
            stmt.setBoolean(31, block.isBetAfterCheckTurn());
            stmt.setBoolean(32, block.isBetAfterCheckRiver());
            stmt.setBoolean(33, block.isCheckRiverAfterTurnBarrel());
            stmt.setBoolean(34, block.isTripleBarrel());
            stmt.setBoolean(35, block.isCall3Barrel());
            stmt.setBoolean(36, block.isRaise3Barrel());
            stmt.setBoolean(37, block.isFoldTo3Barrel());

            stmt.setBoolean(38, block.isBarrelAfterDelayedCbet());
            stmt.setBoolean(39, block.isStabRiver());
            stmt.setBoolean(40, block.isStabTurn());
            stmt.setBoolean(41, block.isDelayedCbet());

            stmt.setString(42, block.getFlushTexure());


            stmt.executeUpdate();
            //System.out.println("DataBlock inserted into database successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */

    public static void insertDataBlocks(List<DataBlock> blocks) {
        String query = "INSERT INTO PokerHands (" +
                "blinds, site, gameFormat, H_FlopCard, M_FlopCard, L_FlopCard, " +
                "turn, river, flopTexture, pairedFlop, tripFlop, fullAction, OR_Pos, ORinBB, threeBetBB, fourBetBB, " +
                "CallPos, aggrIP, potType, isoraisePot, flopPlayers, cbet, cbetCall, cbetFold, cbetRaise, " +
                "betAfterCheck, turnBarrel, foldToTurnBarrel, callTurnBarrel, raiseTurnBarrel, " +
                "betAfterCheckTurn, betAfterCheckRiver, checkRiverAfterTurnBarrel, tripleBarrel, " +
                "call3Barrel, raise3Barrel, foldTo3Barrel, " +
                "isBarrelAfterDelayedCbet, isStabRiver, isStabTurn, isDelayedCbet, flushTexture" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);
            for (DataBlock block : blocks) {
                stmt.setString(1, block.getStakes());
                stmt.setString(2, "win2day");
                stmt.setString(3, "NoLimitHoldem");
                stmt.setInt(4, block.getHFlopCard());
                stmt.setInt(5, block.getMFlopCard());
                stmt.setInt(6, block.getLFlopCard());
                stmt.setInt(7, block.getTurn());
                stmt.setInt(8, block.getRiver());
                stmt.setString(9, block.getFlopTexture());
                stmt.setBoolean(10, block.isPairedFlop());
                stmt.setBoolean(11, block.isTripFlop());
                stmt.setString(12, block.getFullAction());
                stmt.setString(13, block.getORPos());
                stmt.setDouble(14, block.getORinBB());
                stmt.setDouble(15, block.getThreeBetBB());
                stmt.setDouble(16, block.getFourBetBB());
                stmt.setString(17, block.getCallPos());
                stmt.setBoolean(18, block.isAggrIP());
                stmt.setString(19, block.getPotType());
                stmt.setBoolean(20, block.isIsoraisePot());
                stmt.setInt(21, block.getFlopPlayers());
                stmt.setBoolean(22, block.isCbet());
                stmt.setBoolean(23, block.isCbetCall());
                stmt.setBoolean(24, block.isCbetFold());
                stmt.setBoolean(25, block.isCbetRaise());
                stmt.setBoolean(26, block.isBetAfterCheck());
                stmt.setBoolean(27, block.isTurnBarrel());
                stmt.setBoolean(28, block.isFoldToTurnBarrel());
                stmt.setBoolean(29, block.isCallTurnBarrel());
                stmt.setBoolean(30, block.isRaiseTurnBarrel());
                stmt.setBoolean(31, block.isBetAfterCheckTurn());
                stmt.setBoolean(32, block.isBetAfterCheckRiver());
                stmt.setBoolean(33, block.isCheckRiverAfterTurnBarrel());
                stmt.setBoolean(34, block.isTripleBarrel());
                stmt.setBoolean(35, block.isCall3Barrel());
                stmt.setBoolean(36, block.isRaise3Barrel());
                stmt.setBoolean(37, block.isFoldTo3Barrel());

                stmt.setBoolean(38, block.isBarrelAfterDelayedCbet());
                stmt.setBoolean(39, block.isStabRiver());
                stmt.setBoolean(40, block.isStabTurn());
                stmt.setBoolean(41, block.isDelayedCbet());

                stmt.setString(42, block.getFlushTexure());

                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
