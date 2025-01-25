package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;

public class Shield extends Equipment {
    public Shield(int id, int type, int y, int x) {
        super(id, type, y, x);
        this.capacity = 0.0f;
    }

    @Override
    public String getEquipmentTypeName() {
        return "Escudo de madeira";
    }

    @Override
    public int getTypeId(){
        return 0;
    }

    @Override
    public String toString(){
        return getId() + " | " + getEquipmentTypeName() +
                " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")") ;
    }

}
