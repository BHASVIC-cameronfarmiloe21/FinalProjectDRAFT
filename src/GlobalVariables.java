public class GlobalVariables {
    private Boolean buildMode;
    private Boolean AttackPhase;
    private int currentTurn;
    GlobalVariables() {

    }
    public Boolean getBuildMode() {
        return buildMode;
    }

    public void setBuildMode(Boolean buildMode) {
        this.buildMode = buildMode;
    }

    public Boolean getAttackPhase() {
        return AttackPhase;
    }

    public void setAttackPhase(Boolean attackPhase) {
        AttackPhase = attackPhase;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }
}
