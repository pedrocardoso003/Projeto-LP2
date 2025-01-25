package pt.ulusofona.lp2.thenightofthelivingdeisi;

public enum EquipmentType {
    SHIELD(0),
    SAMURAI_SWORD(1),
    PISTOL(2),
    BLEACH(3);

    private final int type;

    EquipmentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static EquipmentType getEquipmentType(int type) {
        switch (type) {
            case 0:
                return EquipmentType.SHIELD;
            case 1:
                return EquipmentType.SAMURAI_SWORD;
            case 2:
                return EquipmentType.PISTOL;
            case 3:
                return EquipmentType.BLEACH;
            default:
                return null;
        }
    }

}