package org.example;

//One DataBlock is representing one row/hand in the SQL Database.

public class DataBlock {
    // Allgemein
    private String blinds;
    private String site; // kann man direkt aus der hand übergeben
    private String gameFormat; // normal/speed // kann man direkt aus der Hand übergeben
    private int H_FlopCard;
    private int M_FlopCard;
    private int L_FlopCard;
    private int turn;
    private int river;
    private String flopTexture; // monotone/rainbow/two-tone
    private boolean pairedFlop;
    private boolean tripFlop;
    private String fullAction; //gesamte Action in einem String

    //Preflop____________________________________________________________________________________________

    private String OR_Pos; // Open Raise Position
    private double ORinBB;  // Open Raise Big Blinds
    private double threeBetBB; // 3-Bet Big Blinds
    private double fourBetBB;  // 4-Bet Big Blinds
    private String CallPos;    // Position der Caller (wenn es mehr sind als einer kommt eine Zahl als String hinein)
    private boolean aggrIP; // ist der preflop last aggressor IP
    private String potType; // ist es ein SRP, 3BetPot, 4BetPot?

    //Flop ________________________________________________________________________________
    private int flopPlayers; // Anzahl der Spieler am Flop
    private boolean cbet; // Wurde eine C-Bet gemacht?
    private boolean cbetFold; // Gab es einen Fold auf die C-Bet?
    private boolean cbetRaise; // Gab es ein Raise auf die C-Bet?
    private boolean betAfterCheck; // Gab es eine Bet nach einem Check?

    // Turn ____________________________________________________________________________________________
    private boolean turnBarrel; // gibt es einen weiteren bet am turn nach einem Cbet?
    private boolean foldToTurnBarrel; // Wurde auf eine Turn-Bet gefoldet?
    private boolean raiseTurnBarrel; // Gab es ein Raise auf die Turn-Bet?
    private boolean betAfterCheckTurn; // Gab es eine Bet nach einem Check (nur sinnvoll, wenn der Aggressor OOP ist)?

    //RIVER----------------------------------------------------------------------------------------------------------------------
    private boolean betAfterCheckRiver;
    private boolean checkRiverAfterTurnBarrel;
    private boolean tripleBarrel;
    private boolean call3Barrel;
    private boolean raise3Barrel;
    private boolean foldTo3Barrel;
    // Getter und Setter für "Allgemein" Attribute::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public String getStakes() {
        return blinds;
    }

    public void setStakes(String stakes) {
        this.blinds = stakes;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getGameFormat() {
        return gameFormat;
    }

    public void setGameFormat(String gameFormat) {
        this.gameFormat = gameFormat;
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
        return fullAction;
    }

    public void setFullAction(String fullAction) {
        this.fullAction = fullAction;
    }

    // Getter/Setter für Preflop Attribute

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

    public boolean isFoldToBarrel() {
        return foldToTurnBarrel;
    }

    public void setFoldToBarrel(boolean foldToTurnBarrel) {
        this.foldToTurnBarrel = foldToTurnBarrel;
    }

    public boolean isRaiseTurnBarrel() {
        return raiseTurnBarrel;
    }

    public void setRaiseTurnBarrel(boolean raiseTurnBarrel) {
        this.raiseTurnBarrel = raiseTurnBarrel;
    }

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

}