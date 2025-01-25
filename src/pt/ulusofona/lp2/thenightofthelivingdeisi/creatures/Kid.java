package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;

public class Kid extends Creature {

    public Kid(int id, int team, int type, String name, int y, int x) {
        super(id, team, type, name, y, x);
    }

    @Override
    public CreatureType getType(){
        return CreatureType.KID;
    }

    @Override
    public String getTypeAsString(){
        return "Criança";
    }

    @Override
    public boolean isValidMove(int xO, int yO, int xD, int yD){
        //so pode mover verticalmente ou horizontalmente uma casa
        //horizontal
        if (Math.abs(xD - xO) > 1) {
            return false;
        }
        //vertical
        if (Math.abs(yD - yO) > 1) {
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
        if (this.equipment == null || !this.equipment.isDefensive()){
            return false;
        }
        return equipment.getType() == equipment.getType(typeId);
    }

    @Override
    public String toString(){
        String resultado = "";
        boolean test = getEquipmentCount() < 0 ? true : false;
        if (isHuman()) {
            resultado = getId() + " | Criança | Humano | " + getName() + " | " +
                    (getEquipmentCount() < 0 ? getEquipmentCount() : "+" + getEquipmentCount()) +
                    " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")");
        } else {
            resultado = getId() + " | Criança | Zombie" + (getTransformed() ? " (Transformado) | " : " | ")
                    + getName() + " | " +
                    (getEquipmentCount() < 0 ? getEquipmentCount() : "-" +  getEquipmentCount()) +
                    " @ (" + gety() + ", " + getx() + ")";
        }
        if (equipment != null) {
            resultado += " | " + equipment.toString();
        }
        return resultado;
    }

}
