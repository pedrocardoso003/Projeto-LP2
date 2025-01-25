package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;

public class SamuraiSword extends Equipment {
    public SamuraiSword(int id, int type, int y, int x) {
        super(id, type, y, x);
        this.capacity = 0.0f;
    }

    @Override
    public String getEquipmentTypeName() {
        return "Espada samurai";
    }

    @Override
    public int getTypeId(){
        return 1;
    }

    @Override
    public String toString(){
        return getId() + " | " + getEquipmentTypeName() +
                " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")") ;
    }

}
