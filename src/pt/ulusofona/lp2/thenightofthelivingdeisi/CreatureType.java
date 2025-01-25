package pt.ulusofona.lp2.thenightofthelivingdeisi;

public enum CreatureType {
    KID(0),
    ADULT(1),
    SENIOR(2),
    DOG(3),
    VAMPIRE(4);

    private final int type;

    CreatureType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static CreatureType getCreatureType(int type) {
        switch (type) {
            case 0:
                return CreatureType.KID;
            case 1:
                return CreatureType.ADULT;
            case 2:
                return CreatureType.SENIOR;
            case 3:
                return CreatureType.DOG;
            case 4:
                return CreatureType.VAMPIRE;
            default:
                return null;
        }
    }
}
