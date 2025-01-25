package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;

public class Pistol extends Equipment {
    public Pistol(int id, int type, int y, int x) {
        super(id, type, y, x);
        this.capacity = Math.round(3.0f);
    }

    @Override
    public String getEquipmentTypeName() {
        return "Pistola Walther PPK";
    }

    @Override
    public int getTypeId(){
        return 2;
    }

    @Override
    public float getCapacity(){
        return Math.round(capacity);
    }

    @Override
    public void used(){
        this.capacity -= 1.0F;
        if (this.capacity <= 0){
            this.capacity = 0.0F;
        }
    }

    @Override
    public String toString(){
        return getId() + " | " + getEquipmentTypeName() +
                " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")") +
                " | " + Math.round(getCapacity()) + " balas";
    }

}
