package pt.ulusofona.lp2.thenightofthelivingdeisi;

public enum CreatureTeam {
    HUMAN(20),
    ZOMBIE(10);

    private final int team;

    CreatureTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }

    public static CreatureTeam getCreatureTeam(int team) {
        switch (team) {
            case 20:
                return CreatureTeam.HUMAN;
            case 10:
                return CreatureTeam.ZOMBIE;
            default:
                return null;
        }
    }

}