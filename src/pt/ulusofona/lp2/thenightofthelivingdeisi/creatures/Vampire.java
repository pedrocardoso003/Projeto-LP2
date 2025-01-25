package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

public class Vampire extends Creature {
    public Vampire(int id, int team, int type, String name, int y, int x) {
        super(id, team, type, name, y, x);
        this.team = 10;
    }

    @Override
    public Equipment getEquipment() {
        return null;
    }

    @Override
    public CreatureType getType(){
        return CreatureType.VAMPIRE;
    }

    @Override
    public String getTypeAsString(){
        return "Vampiro";
    }

    @Override
    public boolean isValidMove(int xO, int yO, int xD, int yD){
        //pode mover ate 1 casas verticalmente ou horizontalmente ou na diagonal

        //movimento é horizontal ate 1 casas de distância
        if (Math.abs(xD - xO) > 1) {
            return false;
        }

        //movimento é vertical ate 1 casas de distância
        if (Math.abs(yD - yO) > 1) {
            return false;
        }

        //movimento é diagonal ate 1 casas de distância
        if (Math.abs(xD - xO) == Math.abs(yD - yO) && Math.abs(xD - xO) > 1) {
            return false;
        }

        return true;
    }

    @Override
    public boolean hasEquipment(int typeId){
        return false;
    }

    @Override
    public String toString(){
        return getId() + " | Vampiro | " + getName() + " | " +
                (getEquipmentCount() < 0 ? getEquipmentCount() : "-" +  getEquipmentCount()) +
                " @ (" + gety() + ", " + getx() + ")";
    }

}
