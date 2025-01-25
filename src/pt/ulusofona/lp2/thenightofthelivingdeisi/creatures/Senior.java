package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

public class Senior extends Creature {
    public Senior(int id,int team, int type, String name, int y, int x) {
        super(id, team, type, name, y, x);
    }

    @Override
    public CreatureType getType(){
        return CreatureType.SENIOR;
    }

    @Override
    public String getTypeAsString(){
        return "Idoso";
    }

    @Override
    public boolean isValidMove(int xO, int yO, int xD, int yD){
        //se for vivo so move de dia

        //diagonal
        if (Math.abs(xD - xO) == Math.abs(yD - yO) && Math.abs(xD - xO) <= 1) {
            return true;
        }

        return false;
    }

    @Override
    public void pickEquipment(Equipment equipment,int xO, int yO){
        if (equipment != null && !equipment.getCaptured()){
            this.equipment = equipment;
            this.equipmentCounter++;
            equipment.setCaptured(true);;
        }
    }

    @Override
    public boolean move(int xO, int yO, int xD, int yD){
        if (!isValidMove(xO, yO, xD, yD)){
            return false;
        }

        if (this.equipment != null){
            dropEquipment(xO, yO);
        }

        this.x = yD;
        this.y = xD;

        return true;
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
        String resultado = "";
        if (isHuman()) {
            resultado = getId() + " | Idoso | Humano | "+ getName() + " | " +
                    (getEquipmentCount() < 0 ? getEquipmentCount() : "+" +  getEquipmentCount()) +
                    " @ " + (isInSafeHaven() ? "Safe Haven" : "(" + gety() + ", " + getx() + ")");
        } else {
            resultado = getId() + " | Idoso | Zombie" + (getTransformed() ? " (Transformado) | " : " | ")
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

