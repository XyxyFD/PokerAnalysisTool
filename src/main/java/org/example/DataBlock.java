package org.example;

//One DataBlock is representing one row/hand in the SQL Database.

public class DataBlock {
    // Allgemein
    private String blinds;
    private int H_FlopCard;
    private int M_FlopCard;
    private int L_FlopCard;
    private int turn;
    private int river;
    private String flopTexture; // monotone/rainbow/two-tone
    private boolean pairedFlop;
    private boolean tripFlop;
    private String fullAction ;// Standardwert für FullAction
    //gesamte Action in einem String
    private String flushTexure; // Strings, welche eine bestimmte flushTexture als spezifischen String für Turn und river abspeichert

    //Preflop____________________________________________________________________________________________

    private String OR_Pos; // Open Raise Position
    private double ORinBB;  // Open Raise Big Blinds
    private double threeBetBB; // 3-Bet Big Blinds
    private double fourBetBB;  // 4-Bet Big Blinds
    private String CallPos;    // Position der Caller (wenn es mehr sind als einer kommt eine Zahl als String hinein)
    private boolean aggrIP; // ist der preflop last aggressor IP
    private String potType; // ist es ein SRP, 3BetPot, 4BetPot?
    private boolean isoraisePot;
    private int flopPlayers; // Anzahl der Spieler am Flop

    //Flop ________________________________________________________________________________

    private boolean cbet; // Wurde eine C-Bet gemacht?
    private boolean cbetCall; // Indicates if a C-bet on the flop was called

    private boolean cbetFold; // Gab es einen Fold auf die C-Bet?
    private boolean cbetRaise; // Gab es ein Raise auf die C-Bet?
    private boolean betAfterCheck; // Gab es eine Bet nach einem Check?

    // Turn ____________________________________________________________________________________________
    private boolean turnBarrel; // gibt es einen weiteren bet am turn nach einem Cbet?
    private boolean foldToTurnBarrel; // Wurde auf eine Turn-Bet gefoldet?



    private boolean callTurnBarrel; // wurde turn barrel gecallt
    private boolean raiseTurnBarrel; // Gab es ein Raise auf die Turn-Bet?
    private boolean betAfterCheckTurn; // Gab es eine Bet nach einem Check (nur sinnvoll, wenn der Aggressor OOP ist)?
    private boolean delayedCbet;
    //RIVER----------------------------------------------------------------------------------------------------------------------
    private boolean betAfterCheckRiver;
    private boolean checkRiverAfterTurnBarrel;
    private boolean tripleBarrel;
    private boolean call3Barrel;
    private boolean raise3Barrel;
    private boolean foldTo3Barrel;
    private boolean stabTurn; //when aggressor checks flop back and villain stabs the turn
    private boolean stabRiver; //when aggressor checks the turn after cbet gets called and villain stabs the river
    private boolean barrelAfterDelayedCbet;
    @Override
    public String toString() {
        return "DataBlock{" +
                "blinds='" + blinds + '\'' +
                ", H_FlopCard=" + H_FlopCard +
                ", M_FlopCard=" + M_FlopCard +
                ", L_FlopCard=" + L_FlopCard +
                ", turn=" + turn +
                ", river=" + river +
                ", flopTexture='" + flopTexture + '\'' +
                ", pairedFlop=" + pairedFlop +
                ", tripFlop=" + tripFlop +
                ", fullAction='" + fullAction + '\'' +
                ", OR_Pos='" + OR_Pos + '\'' +
                ", ORinBB=" + ORinBB +
                ", threeBetBB=" + threeBetBB +
                ", fourBetBB=" + fourBetBB +
                ", CallPos='" + CallPos + '\'' +
                ", aggrIP=" + aggrIP +
                ", potType='" + potType + '\'' +
                ", isoraisePot=" + isoraisePot +
                ", flopPlayers=" + flopPlayers +
                ", cbet=" + cbet +
                ", cbetCall=" + cbetCall +
                ", cbetFold=" + cbetFold +
                ", cbetRaise=" + cbetRaise +
                ", betAfterCheck=" + betAfterCheck +
                ", turnBarrel=" + turnBarrel +
                ", foldToTurnBarrel=" + foldToTurnBarrel +
                ", callTurnBarrel=" + callTurnBarrel +
                ", raiseTurnBarrel=" + raiseTurnBarrel +
                ", betAfterCheckTurn=" + betAfterCheckTurn +
                ", betAfterCheckRiver=" + betAfterCheckRiver +
                ", checkRiverAfterTurnBarrel=" + checkRiverAfterTurnBarrel +
                ", tripleBarrel=" + tripleBarrel +
                ", call3Barrel=" + call3Barrel +
                ", raise3Barrel=" + raise3Barrel +
                ", foldTo3Barrel=" + foldTo3Barrel +
                ", stabTurn=" + stabTurn +
                ", stabRiver=" + stabRiver +
                '}';
    }

    public boolean isDelayedCbet() {
        return delayedCbet;
    }

    public String getBlinds() {
        return blinds;
    }

    public void setBlinds(String blinds) {
        this.blinds = blinds;
    }

    public int getH_FlopCard() {
        return H_FlopCard;
    }

    public void setH_FlopCard(int h_FlopCard) {
        H_FlopCard = h_FlopCard;
    }

    public String getFlushTexure() {
        return flushTexure;
    }

    public void setFlushTexure(String flushTexure) {
        this.flushTexure = flushTexure;
    }

    public int getM_FlopCard() {
        return M_FlopCard;
    }

    public void setM_FlopCard(int m_FlopCard) {
        M_FlopCard = m_FlopCard;
    }

    public int getL_FlopCard() {
        return L_FlopCard;
    }

    public void setL_FlopCard(int l_FlopCard) {
        L_FlopCard = l_FlopCard;
    }

    public String getOR_Pos() {
        return OR_Pos;
    }

    public void setOR_Pos(String OR_Pos) {
        this.OR_Pos = OR_Pos;
    }

    public void setDelayedCbet(boolean delayedCbet) {
        this.delayedCbet = delayedCbet;
    }

    public boolean isBarrelAfterDelayedCbet() {
        return barrelAfterDelayedCbet;
    }

    public void setBarrelAfterDelayedCbet(boolean barrelAfterDelayedCbet) {
        this.barrelAfterDelayedCbet = barrelAfterDelayedCbet;
    }

    // Getter und Setter für "Allgemein" Attribute::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public String getStakes() {
        return blinds;
    }

    public void setStakes(String stakes) {
        this.blinds = stakes;
    }



    public int getHFlopCard() {
        return H_FlopCard;
    }

    public void setHFlopCard(int hFlopCard) {
        this.H_FlopCard = hFlopCard;
    }

    public int getMFlopCard() {
        return M_FlopCard;
    }

    public void setMFlopCard(int mFlopCard) {
        this.M_FlopCard = mFlopCard;
    }

    public int getLFlopCard() {
        return L_FlopCard;
    }

    public void setLFlopCard(int lFlopCard) {
        this.L_FlopCard = lFlopCard;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getRiver() {
        return river;
    }

    public void setRiver(int river) {
        this.river = river;
    }

    public String getFlopTexture() {
        return flopTexture;
    }
    // Getter für stabFlopAfterCheck


    public void setFlopTexture(String flopTexture) {
        this.flopTexture = flopTexture;
    }

    public boolean isPairedFlop() {
        return pairedFlop;
    }

    public void setPairedFlop(boolean pairedFlop) {
        this.pairedFlop = pairedFlop;
    }

    public boolean isTripFlop() {
        return tripFlop;
    }

    public void setTripFlop(boolean tripFlop) {
        this.tripFlop = tripFlop;
    }

    public String getFullAction() {
        return (fullAction == null || fullAction.isEmpty()) ? "NO_ACTION" : fullAction;
    }

    public void setFullAction(String fullAction) {
        this.fullAction = (fullAction == null || fullAction.isEmpty()) ? "NO_ACTION" : fullAction;
    }


    // Getter/Setter für Preflop Attribute
    public boolean isIsoraisePot() {
        return isoraisePot;
    }

    public void setIsoraisePot(boolean isoraisePot) {
        this.isoraisePot = isoraisePot;
    }

    public String getORPos() {
        return OR_Pos;
    }

    public void setORPos(String ORPos) {
        this.OR_Pos = ORPos;
    }

    public double getORinBB() {
        return ORinBB;
    }

    public void setORinBB(double ORinBB) {
        this.ORinBB = ORinBB;
    }

    public double getThreeBetBB() {
        return threeBetBB;
    }

    public void setThreeBetBB(double threeBetBB) {
        this.threeBetBB = threeBetBB;
    }

    public double getFourBetBB() {
        return fourBetBB;
    }

    public void setFourBetBB(double fourBetBB) {
        this.fourBetBB = fourBetBB;
    }

    public String getCallPos() {
        return CallPos;
    }

    public void setCallPos(String CallPos) {
        this.CallPos = CallPos;
    }
    public boolean isAggrIP() {
        return aggrIP;
    }

    public void setAggrIP(boolean aggrIP) {
        this.aggrIP = aggrIP;
    }

    public String getPotType() {
        return potType;
    }

    public void setPotType(String potType) {
        this.potType = potType;
    }


    //Test
    public void printOut(){
        System.out.println(getFullAction());
    }
    // Getter und Setter für Flop Attribute

    public int getFlopPlayers() {
        return flopPlayers;
    }

    public void setFlopPlayers(int flopPlayers) {
        this.flopPlayers = flopPlayers;
    }

    public boolean isCbet() {
        return cbet;
    }

    public void setCbet(boolean cbet) {
        this.cbet = cbet;
    }
    public boolean isCbetCall() {
        return cbetCall;
    }

    public void setCbetCall(boolean cbetCall) {
        this.cbetCall = cbetCall;
    }

    public boolean isCbetFold() {
        return cbetFold;
    }

    public void setCbetFold(boolean cbetFold) {
        this.cbetFold = cbetFold;
    }

    public boolean isCbetRaise() {
        return cbetRaise;
    }

    public void setCbetRaise(boolean cbetRaise) {
        this.cbetRaise = cbetRaise;
    }

    public boolean isBetAfterCheck() {
        return betAfterCheck;
    }

    public void setBetAfterCheck(boolean betAfterCheck) {
        this.betAfterCheck = betAfterCheck;
    }
    // Getter und Setter für Turn Attribute

    public boolean isTurnBarrel() {
        return turnBarrel;
    }

    public void setTurnBarrel(boolean turnBarrel) {
        this.turnBarrel = turnBarrel;
    }

    public boolean isFoldToTurnBarrel() {
        return foldToTurnBarrel;
    }

    public void setFoldToTurnBarrel(boolean foldToTurnBarrel) {
        this.foldToTurnBarrel = foldToTurnBarrel;
    }
    public boolean isCallTurnBarrel() {
        return callTurnBarrel;
    }

    public void setCallTurnBarrel(boolean callTurnBarrel) {
        this.callTurnBarrel = callTurnBarrel;
    }

    public boolean isRaiseTurnBarrel() {
        return raiseTurnBarrel;
    }

    public void setRaiseTurnBarrel(boolean raiseTurnBarrel) {
        this.raiseTurnBarrel = raiseTurnBarrel;
    }

    //River

    public boolean isBetAfterCheckTurn() {
        return betAfterCheckTurn;
    }

    public void setBetAfterCheckTurn(boolean betAfterCheckTurn) {
        this.betAfterCheckTurn = betAfterCheckTurn;
    }
    public boolean isBetAfterCheckRiver() {
        return betAfterCheckRiver;
    }

    public void setBetAfterCheckRiver(boolean betAfterCheckRiver) {
        this.betAfterCheckRiver = betAfterCheckRiver;
    }

    public boolean isCheckRiverAfterTurnBarrel() {
        return checkRiverAfterTurnBarrel;
    }

    public void setCheckRiverAfterTurnBarrel(boolean checkRiverAfterTurnBarrel) {
        this.checkRiverAfterTurnBarrel = checkRiverAfterTurnBarrel;
    }

    public boolean isTripleBarrel() {
        return tripleBarrel;
    }

    public void setTripleBarrel(boolean tripleBarrel) {
        this.tripleBarrel = tripleBarrel;
    }

    public boolean isCall3Barrel() {
        return call3Barrel;
    }

    public void setCall3Barrel(boolean call3Barrel) {
        this.call3Barrel = call3Barrel;
    }

    public boolean isRaise3Barrel() {
        return raise3Barrel;
    }

    public void setRaise3Barrel(boolean raise3Barrel) {
        this.raise3Barrel = raise3Barrel;
    }


    public boolean isFoldTo3Barrel() {
        return foldTo3Barrel;
    }

    public void setFoldTo3Barrel(boolean foldTo3Barrel) {
        this.foldTo3Barrel = foldTo3Barrel;
    }

    public boolean isStabRiver() {
        return stabRiver;
    }

    public void setStabRiver(boolean stabRiver) {
        this.stabRiver = stabRiver;
    }

    public boolean isStabTurn() {
        return stabTurn;
    }

    public void setStabTurn(boolean stabTurn) {
        this.stabTurn = stabTurn;
    }
}
