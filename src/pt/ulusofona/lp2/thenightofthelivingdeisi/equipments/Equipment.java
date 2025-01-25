package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;


import pt.ulusofona.lp2.thenightofthelivingdeisi.EquipmentType;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Unit;

//type 0 -> escudo | 1 -> espadaSamurai | 2 -> Pistola | 3 -> Lixivia
public abstract class Equipment extends Unit {
    protected boolean captured;
    protected float capacity;
    protected int type;

    public Equipment(int id, int type, int y, int x) {
        super(id, y, x);
        this.type = type;
        this.captured = false;
    }

    public abstract String getEquipmentTypeName();

    public abstract int getTypeId();

    public EquipmentType getType() {
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

    public EquipmentType getType(int type) {
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

    public boolean getCaptured(){
        return captured;
    }

    public void setCaptured(boolean captured){
        this.captured = captured;
    }

    public float getCapacity(){
        return capacity;
    }

    public boolean isDefensive(){
        return this.getType() == EquipmentType.SHIELD || this.getType() == EquipmentType.BLEACH;
    }

    public boolean isEquipment(){
        return true;
    }

    public boolean isCreature(){
        return false;
    }

    public void used(){
    }

    public String[] getEquipmentInfo(){
        String[] info = new String[5];
        info[0] = String.valueOf(getId());
        info[1] = String.valueOf(getTypeId());
        info[2] = String.valueOf(gety());
        info[3] = String.valueOf(getx());
        info[4] = null;
        return info;
    }

}