package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;

public class Bleach extends Equipment {
    public Bleach(int id, int type, int y, int x) {
        super(id, type, y, x);
        this.capacity = 1.0f;
    }

    @Override
    public String getEquipmentTypeName() {
        return "Lixívia";
    }

    @Override
    public int getTypeId(){
        return 3;
    }

    @Override
    public float getCapacity(){
        return capacity;
    }

    public void used(){
        this.capacity -= 0.3F;
        this.capacity = Math.round(this.capacity * 100.0) / 100.0F;//arrendondar para 2 casas decimais
        if (this.capacity <= 0){
            this.capacity = 0.0F;
        }
    }

    @Override
    public String toString(){
        return getId() + " | " + getEquipmentTypeName() +
                " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")") +
                 " | " + getCapacity() + " litros";
    }
}