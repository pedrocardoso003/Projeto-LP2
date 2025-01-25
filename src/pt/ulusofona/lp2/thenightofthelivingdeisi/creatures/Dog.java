package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

public class Dog extends Creature {
    public Dog(int id, int team, int type, String name, int y, int x) {
        super(id, team, type, name, y, x);
        this.team = 20;
    }

    @Override
    public Equipment getEquipment() {
        return null;
    }

    @Override
    public CreatureType getType(){
        return CreatureType.DOG;
    }

    @Override
    public String getTypeAsString(){
        return "Cão";
    }

    @Override
    public boolean isValidMove(int xO, int yO, int xD, int yD){
        //so pode mover verticalmente ou horizontalmente 2 casas

        //horizontal
        if (Math.abs(xD - xO) > 2) {
            return false;
        }
        //vertical
        if (Math.abs(yD - yO) > 2) {
            return false;
        }
        //diagonal
        if (Math.abs(xD - xO) == Math.abs(yD - yO) && Math.abs(xD - xO) > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean hasEquipment(int typeId){
        return false;
    }

    @Override
    public void attack(Creature attackedCreature){
       //nao ataca
    }

    @Override
    public String toString(){
        return getId() + " | Cão | " + getName() + " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")");
    }
}
