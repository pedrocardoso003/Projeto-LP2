package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;

public class Adult extends Creature {
    public Adult(int id, int team, int type, String name, int y, int x) {
        super(id, team, type, name, y, x);
    }

    @Override
    public CreatureType getType(){
        return CreatureType.ADULT;
    }

    @Override
    public String getTypeAsString(){
        return "Adulto";
    }

    @Override
    public boolean isValidMove(int xO, int yO, int xD, int yD){
        //pode mover ate 2 casas verticalmente ou horizontalmente ou na diagonal

        //horizontal
        if (xO == xD && Math.abs(yD - yO) <= 2) {
            return true;
        }

        //vertical
        if (yO == yD && Math.abs(xD - xO) <= 2) {
            return true;
        }

        //diagonal
        if (Math.abs(xD - xO) == Math.abs(yD - yO) && Math.abs(xD - xO) <= 2) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasEquipment(int typeId){
        if (this.equipment == null){
            return false;
        }
        return equipment.getType() == equipment.getType(typeId);
    }

    @Override
    public String toString(){
        String resultado;
       if (isHuman()) {
           resultado = getId() + " | Adulto | Humano | " +
                   getName() + " | " +
                   (getEquipmentCount() < 0 ? getEquipmentCount() : "+" + getEquipmentCount()) +
                   " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")") ;
       } else {
           resultado = getId() + " | Adulto | Zombie" +
                   (getTransformed() ? " (Transformado) | " : " | ") +
                   getName() + " | " +
                   (getEquipmentCount() < 0 ? getEquipmentCount() : "-" + getEquipmentCount()) +
                   " @ ("+ gety() + ", " + getx() + ")";
       }
       if (equipment != null) {
           resultado += " | " + equipment.toString();
       }

       return resultado;
    }

}
