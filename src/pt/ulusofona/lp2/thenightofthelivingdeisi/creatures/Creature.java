package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

//team: 10 -> zombie | 20 -> humano
//type: 0 -> kid | 1 -> adult | 2 -> senior | 3 -> dog | 4 -> vampire

import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureTeam;
import pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;
import pt.ulusofona.lp2.thenightofthelivingdeisi.Unit;

public abstract class Creature extends Unit {
    protected int team,type,equipmentCounter;
    protected boolean transformed;
    protected String name;
    protected Equipment equipment;

    public Creature(int id, int team, int type, String name, int y, int x) {
        super(id, y, x);
        this.team = team;
        this.type = type;
        this.name = name;
        this.equipmentCounter = 0;
        this.transformed = false;
    }

    public CreatureTeam getTeam() {
        if (team == 10){
            return CreatureTeam.ZOMBIE;
        }
        return CreatureTeam.HUMAN;
    }

    public int getTeamAsInt(){
        return team;
    }

   public abstract CreatureType getType();

    public int getTypeAsInt(){
        return type;
    }

    public abstract String getTypeAsString();

    public String getName() {
        return name;
    }

    public int getEquipmentCount() {
        return equipmentCounter;
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public boolean getTransformed(){
        return transformed;
    }

    public void transformed(){
        this.team = 10;
        this.transformed = true;
        this.equipment = null;
        this.equipmentCounter = -this.equipmentCounter;
    }

    public boolean hasEquipmentEquipped(){
        return equipment != null;
    }

    public boolean isHuman(){
        return this.getTeam() == CreatureTeam.HUMAN;
    }

    public abstract boolean isValidMove(int xO, int yO, int xD, int yD);

    public boolean move(int xO, int yO, int xD, int yD){
        if (!isValidMove(xO, yO, xD, yD)){
            return false;
        }

        this.x = yD;
        this.y = xD;
        if (this.equipment != null){
            this.equipment.setx(yD);
            this.equipment.sety(xD);
        }
        return true;
    }

    public void pickEquipment(Equipment equipment, int xO, int yO){
        if (equipment != null && !equipment.getCaptured()){
            if (this.equipment != null){
                dropEquipment(xO, yO);
            }
            this.equipment = equipment;
            this.equipmentCounter++;
            equipment.setCaptured(true);
        }
    }
    public void dropEquipment(int xO, int yO){
        this.equipment.setCaptured(false);
        this.equipment.setx(yO);
        this.equipment.sety(xO);
        this.equipment = null;
    }

    public abstract boolean hasEquipment(int typeId);

    public void destroyEquipment(Equipment equipment){
        if(equipment != null && !this.isHuman()){
            equipment = null;
            this.equipmentCounter--;
        }
    }

    public void defend()
    {
        if (this.equipment != null){
            this.equipment.used();
        }
    }

    public void attack(Creature attackedCreature){
        //zombie ataca humano
        if (!this.isHuman()){
            attackedCreature.transformed();
            this.equipmentCounter += attackedCreature.getEquipmentCount();
        }

        //humano ataca zombie
        if (this.isHuman() && !this.getEquipment().isDefensive()){
            this.equipment.used();
            attackedCreature = null;
        }
    }

    public boolean isEquipment(){
        return false;
    }

    public boolean isCreature(){
        return true;
    }

    public String[] getCreatureInfo(){
        String[] creatureInfo = new String[7];
        creatureInfo[0] = String.valueOf(this.getId());
        creatureInfo[1] = this.getTypeAsString();
        creatureInfo[2] = this.getTeam() == CreatureTeam.HUMAN ? "Humano" : "Zombie" +
                (this.getTransformed() ? " (Transformado)" : "");
        creatureInfo[3] = this.getName();
        if (this.isInSafeHaven()){
            creatureInfo[4] = null;
            creatureInfo[5] = null;
        }else {
            creatureInfo[4] = String.valueOf(this.gety());
            creatureInfo[5] = String.valueOf(this.getx());
        }
        creatureInfo[6] = null;
        return creatureInfo;
    }

}
