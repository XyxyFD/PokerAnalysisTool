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
    private boolean isoraisePot;



    //Flop ________________________________________________________________________________
    private int flopPlayers; // Anzahl der Spieler am Flop
    private boolean cbet; // Wurde eine C-Bet gemacht?
    private boolean cbetCall; // Indicates if a C-bet on the flop was called

    private boolean cbetFold; // Gab es einen Fold auf die C-Bet?
    private boolean cbetRaise; // Gab es ein Raise auf die C-Bet?
    private boolean betAfterCheck; // Gab es eine Bet nach einem Check?
    private boolean stabFlopAfterCheck;

    // Turn ____________________________________________________________________________________________
    private boolean turnBarrel; // gibt es einen weiteren bet am turn nach einem Cbet?
    private boolean foldToTurnBarrel; // Wurde auf eine Turn-Bet gefoldet?



    private boolean callTurnBarrel; // wurde turn barrel gecallt
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
    // Getter für stabFlopAfterCheck
    public boolean isStabFlopAfterCheck() {
        return stabFlopAfterCheck;
    }

    // Setter für stabFlopAfterCheck
    public void setStabFlopAfterCheck(boolean stabFlopAfterCheck) {
        this.stabFlopAfterCheck = stabFlopAfterCheck;
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

    @Override
    public String toString() {
        return "new DataBlock{" + "\n" +
                "  blinds='" + blinds + '\'' + "\n" +
                "  site='" + site + '\'' + "\n" +
                "  gameFormat='" + gameFormat + '\'' + "\n" +
                "  H_FlopCard=" + H_FlopCard + "\n" +
                "  M_FlopCard=" + M_FlopCard + "\n" +
                "  L_FlopCard=" + L_FlopCard + "\n" +
                "  turn=" + turn + "\n" +
                "  river=" + river + "\n" +
                "  flopTexture='" + flopTexture + '\'' + "\n" +
                "  pairedFlop=" + pairedFlop + "\n" +
                "  tripFlop=" + tripFlop + "\n" +
                "  fullAction='" + fullAction + '\'' + "\n" +
                "  OR_Pos='" + OR_Pos + '\'' + "\n" +
                "  ORinBB=" + ORinBB + "\n" +
                "  threeBetBB=" + threeBetBB + "\n" +
                "  fourBetBB=" + fourBetBB + "\n" +
                "  CallPos='" + CallPos + '\'' + "\n" +
                "  aggrIP=" + aggrIP + "\n" +
                "  potType='" + potType + '\'' + "\n" +
                "  isoraisePot=" + isoraisePot + "\n" +
                "  flopPlayers=" + flopPlayers + "\n" +
                "  cbet=" + cbet + "\n" +
                "  cbetCall=" + cbetCall + "\n" +
                "  cbetFold=" + cbetFold + "\n" +
                "  cbetRaise=" + cbetRaise + "\n" +
                "  betAfterCheck=" + betAfterCheck + "\n" +
                "  stabFlopAfterCheck=" + stabFlopAfterCheck + "\n" + // Neue Zeile für stabFlopAfterCheck
                "  turnBarrel=" + turnBarrel + "\n" +
                "  foldToTurnBarrel=" + foldToTurnBarrel + "\n" +
                "  callTurnBarrel=" + callTurnBarrel + "\n" +
                "  raiseTurnBarrel=" + raiseTurnBarrel + "\n" +
                "  betAfterCheckTurn=" + betAfterCheckTurn + "\n" +
                "  betAfterCheckRiver=" + betAfterCheckRiver + "\n" +
                "  checkRiverAfterTurnBarrel=" + checkRiverAfterTurnBarrel + "\n" +
                "  tripleBarrel=" + tripleBarrel + "\n" +
                "  call3Barrel=" + call3Barrel + "\n" +
                "  raise3Barrel=" + raise3Barrel + "\n" +
                "  foldTo3Barrel=" + foldTo3Barrel + "\n" +
                '}';
    }



    public boolean isFoldTo3Barrel() {
        return foldTo3Barrel;
    }

    public void setFoldTo3Barrel(boolean foldTo3Barrel) {
        this.foldTo3Barrel = foldTo3Barrel;
    }

}
