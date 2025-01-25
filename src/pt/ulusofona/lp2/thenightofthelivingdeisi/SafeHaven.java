package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.creatures.Creature;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

import java.util.ArrayList;

public class SafeHaven extends Unit {
    protected ArrayList<Unit> units ;

    public SafeHaven(int y, int x) {
        super(y, x);
        this.units = new ArrayList<>();
    }

    public ArrayList<Creature> getCreatures() {
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.isCreature()) {
                creatures.add((Creature) unit);
            }
        }
        return creatures;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addCreature(Creature creature){
        units.add(creature);
    }

    public void addEquipment(Equipment equipment){
        units.add(equipment);
    }

    public boolean isEquipment() {
        return false;
    }

    public boolean isCreature() {
        return false;
    }

    public boolean isInSafeHaven() {
        return true;
    }

    @Override
    public String toString(){
        return "Porta para Safe Heaven";
    }

}
