package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.creatures.Creature;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

import java.util.ArrayList;
import java.util.List;

public class Game {
    //agora: team 10 -> zombie | 20 -> humano
    protected int line, column,team,currentTeamId,turnCount,turnCountWithoutAttack;
    protected ArrayList<Unit> units;

    public Game(int line, int column, int currentTeamId ,int team, ArrayList<Unit> units) {
        this.line = line;
        this.column = column;
        this.currentTeamId = currentTeamId;
        this.turnCount = 0;
        this.turnCountWithoutAttack = 0;
        this.team = team;
        this.units = units;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getTeamId() {
        return team;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public int[] getWorldSize() {
        return new int[]{getLine(), getColumn()};
    }

    public int getInitialTeamId() {
        return team;
    }

    public int getCurrentTeamId() {
        if (getTurnCount() == 0) {
            return getInitialTeamId();
        }
        return currentTeamId;
    }

    //ver melhor na parte do enum
    public CreatureTeam getCurrentTeam() {
        if (getCurrentTeamId() == CreatureTeam.ZOMBIE.getTeam()) {
            return CreatureTeam.ZOMBIE;
        } else {
            return CreatureTeam.HUMAN;
        }
    }

    public ArrayList<Creature> getCreatures(){
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Unit currentUnit : getUnits()){
            if (currentUnit.isCreature()){
                creatures.add((Creature) currentUnit);
            }
        }
        return creatures;
    }

    public ArrayList<Equipment> getEquipments() {
        ArrayList<Equipment> equipments = new ArrayList<>();
        for (Unit currentUnit : getUnits()){
            if (currentUnit.isEquipment()){
                equipments.add((Equipment) currentUnit);
            }
        }
        return equipments;
    }

    public ArrayList<SafeHaven> getSafeHavens() {
        ArrayList<SafeHaven> safeHavens = new ArrayList<>();
        for (Unit currentUnit : getUnits()){
            if (!currentUnit.isCreature() && !currentUnit.isEquipment()){
                safeHavens.add((SafeHaven) currentUnit);
            }
        }
        return safeHavens;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public int getTurnCountWithoutAttack() {
        return turnCountWithoutAttack;
    }

    //TODO: MUDAR ISTO PARA NAO MOSTRAR 10 E 20
    public void changeCurrentTeamId() {
        CreatureTeam currentTeam = getCurrentTeam();
        if (currentTeamId == CreatureTeam.ZOMBIE.getTeam()) {
            currentTeamId = CreatureTeam.HUMAN.getTeam();
        } else {
            currentTeamId = CreatureTeam.ZOMBIE.getTeam();
        }
    }

    public boolean isDay() {
        return (getTurnCount() / 2) % 2 == 0;
    }

    public void humanEnterSafeHaven(Creature currentCreature, SafeHaven safeHaven) {
        safeHaven.addCreature(currentCreature);
        currentCreature.enterSafeHaven();
        if (currentCreature.hasEquipmentEquipped()){
            safeHaven.addEquipment(currentCreature.getEquipment());
            currentCreature.getEquipment().enterSafeHaven();
        }
    }

    public String getSquareInfo(int x, int y) {
        if (x < 0 || x >= getColumn() || y < 0 || y >= getLine()) {
            return null;
        }

        StringBuilder SquareInfo = new StringBuilder();

        for (Unit currentUnit : getUnits()){
            if (currentUnit.getx() == y && currentUnit.gety() == x){
                if (currentUnit.isInSafeHaven()){
                    SquareInfo.append("SH");
                    return SquareInfo.toString();
                }
                else if (currentUnit.isCreature()){
                    Creature currentCreature = (Creature) currentUnit;
                    if(currentCreature.getTeam() == CreatureTeam.ZOMBIE){
                        SquareInfo.append("Z").append(":").append(currentCreature.getId());
                    }
                    else {
                        SquareInfo.append("H").append(":").append(currentCreature.getId());
                    }
                    return SquareInfo.toString();
                }
                else if (currentUnit.isEquipment()){
                    Equipment currentEquipment = (Equipment) currentUnit;
                    SquareInfo.append("E").append(":").append(currentEquipment.getId());
                    return SquareInfo.toString();
                }
            }
        }

        if (SquareInfo.isEmpty()) {
            return "";
        }
        return SquareInfo.toString();
    }

    public String[] getCreatureInfo(int id) {
        String [] creatureInfo = new String[7];

        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getId() == id) {
                 return currentCreature.getCreatureInfo();
            }
        }
        return new String[]{};
    }

    public String isZombie(Creature currentCreature) {
        String creatureInfo = "";
        if(currentCreature.getTeam() == CreatureTeam.ZOMBIE){
            creatureInfo +=("Zombie | ");
            if (currentCreature.getEquipmentCount() == 0){
                creatureInfo+=(currentCreature.getName()) + (" | -");
            }
            else {
                creatureInfo+=(currentCreature.getName()) + (" | ");
            }
        }
        return creatureInfo;
    }

    public String getCreatureTypeAndTeamInfo(Creature currentCreature) {
        StringBuilder info = new StringBuilder();

        // Verificar se a criatura foi transformada
        if (currentCreature.getTransformed()) {
            info.append("Zombie (Transformado) | ");
            if(currentCreature.getEquipmentCount() == 0){
                info.append(currentCreature.getName()).append(" | -");
            }
            else {
                info.append(currentCreature.getName()).append(" | ");
            }
        } else {
            if (currentCreature.getType() != CreatureType.VAMPIRE && currentCreature.getType() != CreatureType.DOG) {
                info.append(
                        (currentCreature.getTeam() == CreatureTeam.ZOMBIE)
                                ? (currentCreature.getEquipmentCount() < 0
                                ? "Zombie" + " | " + currentCreature.getName() + " | "
                                : "Zombie" + " | " + currentCreature.getName() + " | -")
                                : "Humano" + " | " + currentCreature.getName() + " | +"
                );
            } else {
                info.append(
                        (currentCreature.getTeam() == CreatureTeam.ZOMBIE)
                                ? (currentCreature.getEquipmentCount() < 0
                                ? currentCreature.getName() + " | "
                                : currentCreature.getName() + " | -")
                                : currentCreature.getName()
                );
            }
        }

        return info.toString();
    }

    private String getCreatureEquipmentInfo(Creature currentCreature) {
        if (currentCreature.getType() == CreatureType.DOG) {
            return " @";
        } else {
            return currentCreature.getEquipmentCount() + " @";
        }
    }

    public String getCreatureInfoAsString(int id) {
        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getId() == id) {

                return currentCreature.toString();
            }
        }
        return null;
    }

    // retorna null se deixar de estar disponivel para ser capturado/destruido
    public String[] getEquipmentInfo(int id) {
        //x -> coluna | y -> linha
        String [] equipmentInfo = new String[5];
        for (Equipment currentEquipment : getEquipments()) {
            if (currentEquipment.getId() == id) {

                if (currentEquipment.getCaptured()){
                    return null;
                }

                return currentEquipment.getEquipmentInfo();
            }
        }
        return null;
    }
    public String getEquipmentInfoAsString(int id) {
        for (Equipment currentEquipment : getEquipments()) {
            if (currentEquipment.getId() == id) {

                if (currentEquipment.getCaptured()){
                    return null;
                }

                return currentEquipment.toString();
                }
            }
            return null;
        }

    public Equipment getEquipment(int x, int y) {
        for (Equipment currentEquipment : getEquipments()) {
            if (currentEquipment.getx() == x && currentEquipment.gety() == y) {
                return currentEquipment;
            }
        }
        return null;
    }

    public int getEquipmentCount(int creatureId){
        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getId() == creatureId) {
                return currentCreature.getEquipmentCount();
            }
        }
        return 0;
    }

    public EquipmentType getEquipmentType(int equipmentId){
        for (Equipment currentEquipment : getEquipments()) {
            if (currentEquipment.getTypeId() == equipmentId) {
                return currentEquipment.getType();
            }
        }
        return null;
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId){
        for (Creature currentHuman : getCreatures()) {
            if (currentHuman.getId() == creatureId) {
                if (currentHuman.hasEquipment(equipmentTypeId)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verifyValidMove(int xO, int yO, int xD, int yD) {
        //verifica se o move respeita o tamanho do tabuleiro
        if (xO < 0 || xO >= getColumn() || yO < 0 || yO >= getLine()) {
            return false;
        }
        if (xD < 0 || xD >= getColumn() || yD < 0 || yD >= getLine()) {
            return false;
        }
        if (xD == xO && yD == yO) {
            return false;
        }

        return true;
    }

    public boolean checkEquipment(Creature creatureAtLocation) {
        if (!creatureAtLocation.hasEquipmentEquipped()) {
            return false;
        }
        Equipment equipment = creatureAtLocation.getEquipment();

        if (equipment.getType() == EquipmentType.BLEACH && equipment.getCapacity() <= 0.0f ||
                equipment.getType() == EquipmentType.PISTOL && equipment.getCapacity() <= 0.0f){
            return false;
        }
        return true;
    }

    public boolean canPickEquipment(Creature currentCreature, Equipment equipmentToPick) {
        if (equipmentToPick == null){
            return false;
        }

        if (!currentCreature.isHuman()){
            return false;
        }
        if (currentCreature.getType() == CreatureType.DOG){
            return false;
        }

        if (currentCreature.getType() == CreatureType.KID &&  !equipmentToPick.isDefensive()){
            return false;
        }

        return true;
    }

    //ADULTO:ate 2 casas horizontal vertical ou diagonal(pode saltar por cima do zombie)
    //CRIANÇA:1 casa horizontal ou vertical
    //IDOSO:1 casa diagonal
    //CAO:ate 2 casas horizontal ou vertical
    //VAMPIRO:1 casa horizontal ou vertical ou diagonal
    public boolean verifyCreature(int xO, int yO, int xD, int yD, Creature currentCreature) {
        if (!(currentCreature.getx() == yO && currentCreature.gety() == xO)) {
            return false;
        }

        if (currentCreature.getTeam() != getCurrentTeam()) {
            return false;
        }

        if (currentCreature.isHuman() && getSquareInfo(xD, yD).startsWith("H")) {
            return false;
        }

        if (!currentCreature.isHuman() && getSquareInfo(xD, yD).startsWith("Z")) {
            return false;
        }

        if (currentCreature.getType() == CreatureType.SENIOR && currentCreature.isHuman()) {
            if (!isDay()){
                return false;
            }
        }

        if (currentCreature.getType() == CreatureType.VAMPIRE){
            if (isDay()){
                return false;
            }
        }

        if (currentCreature.isValidMove(xO, yO, xD, yD)) {
            return true;
        }

        return false;
    }

    public boolean movingToSafeHaven(Creature currentCreature, int xD, int yD){
        if (!getSquareInfo(xD, yD).startsWith("SH")) {
            return false;
        }

        if (currentCreature.isHuman()) {
            for (SafeHaven currentSafeHaven : getSafeHavens()) {
                if (currentSafeHaven.getx() == yD && currentSafeHaven.gety() == xD) {
                    humanEnterSafeHaven(currentCreature, currentSafeHaven);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGonnaAttack(Creature currentCreature, int xO, int yO, int xD,int yD){
        //zombie ataca humano
        if (!currentCreature.isHuman() && getSquareInfo(xD, yD).startsWith("H")) {
            for (Creature creatureAtLocation : getCreatures()) {
                if (creatureAtLocation.getType() == CreatureType.DOG)
                {
                    continue;
                }

                if (creatureAtLocation.getx() == yD && creatureAtLocation.gety() == xD) {

                    if (checkEquipment(creatureAtLocation)){
                        currentCreature.move(xO, yO, xO, yO);
                        creatureAtLocation.defend();
                        return true;
                    }

                    currentCreature.move(xO, yO, xO, yO);
                    currentCreature.attack(creatureAtLocation);
                    turnCountWithoutAttack = -1;
                    return true;
                }
            }
        }

        //humano ataca zombie
        if (currentCreature.isHuman() && getSquareInfo(xD, yD).startsWith("Z")) {
            if (!currentCreature.hasEquipmentEquipped()){
                return false;
            }

            for (Creature creatureAtLocation : getCreatures()) {
                if (creatureAtLocation.getx() == yD && creatureAtLocation.gety() == xD) {
                    if (!currentCreature.getEquipment().isDefensive() && checkEquipment(currentCreature)){
                        currentCreature.move(xO, yO, xD, yD);
                        currentCreature.attack(creatureAtLocation);
                        turnCountWithoutAttack = -1;
                        destroyCreature(creatureAtLocation);//TODO:ALTERAR ISTO PARA NAO REMOVER DO ARRAYLIST
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isGonnaPickEquipment(Creature currentCreature, int xO, int yO, int xD,int yD){
        if (!getSquareInfo(xD, yD).startsWith("E")) {
            return false;
        }

        if (currentCreature.isHuman()){
            Equipment equipmentToPick = getEquipment(yD, xD);

            if (canPickEquipment(currentCreature, equipmentToPick)){
                currentCreature.move(xO, yO, xD, yD);
                currentCreature.pickEquipment(equipmentToPick,xO,yO);
                return true;
            }
            return false;
        }

        if (!currentCreature.isHuman()){
            Equipment equipmentToDestroy = getEquipment(yD, xD);

            currentCreature.destroyEquipment(equipmentToDestroy);
            destroyEquipment(equipmentToDestroy);//TODO:ALTERAR ISTO PARA NAO REMOVER DO ARRAYLIST(not sure)
            currentCreature.move(xO, yO, xD, yD);

            return true;
        }

        return false;
    }

    public boolean movingToEmptySquare(Creature currentCreature, int xO, int yO, int xD, int yD){
        if (getSquareInfo(xD, yD).isEmpty()){
            currentCreature.move(xO, yO, xD, yD);
            return true;
        }
        return false;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        if (!verifyValidMove(xO, yO, xD, yD)) {
            return false;
        }

        for (Creature currentCreature : getCreatures()) {
            if (!verifyCreature(xO,yO,xD,yD,currentCreature)) {
                continue;
            }

            boolean actionPerformed = false;

            if (movingToSafeHaven(currentCreature, xD, yD)) {
                actionPerformed = true;
            }

            if (isGonnaAttack(currentCreature, xO, yO, xD, yD)) {
                actionPerformed = true;
            }

            if (isGonnaPickEquipment(currentCreature, xO, yO, xD, yD)) {
                actionPerformed = true;
            }

            if (movingToEmptySquare(currentCreature, xO, yO, xD, yD)){
                actionPerformed = true;
            }

            if (actionPerformed) {
                turnCount++;
                turnCountWithoutAttack++;
                changeCurrentTeamId();

                if (gameIsOver()) {
                    getSurvivors();
                }

                return true;
            }
        }
        return false;
    }

    public void destroyEquipment(Equipment equipmentToDestroy){
        for (Equipment currentEquipment : getEquipments()) {
            if (currentEquipment.getId() == equipmentToDestroy.getId()) {
                units.remove(currentEquipment);
                units.remove(equipmentToDestroy);
                return;
            }
        }
    }

    public void destroyCreature(Creature creatureToDestroy){
        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getId() == creatureToDestroy.getId()) {
                units.remove(currentCreature);
                return;
            }
        }
    }

    public boolean gameIsOver() {
        int humanCount = 0;
        int zombieCount = 0;

        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.isHuman() && !getIdsInSafeHaven().contains(currentCreature.getId())) {
                humanCount++;
            } else if (!currentCreature.isHuman()){
                zombieCount++;
            }
        }

        if (humanCount == 0 || zombieCount == 0) {
            return true;
        }

        return turnCountWithoutAttack >= 8;
    }

    public ArrayList<String> getSurvivors() {
        ArrayList<String> survivors = new ArrayList<>();
        survivors.add("Nr. de turnos terminados:");
        survivors.add(getTurnCount()+"");
        survivors.add("");
        survivors.add("OS VIVOS");

        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getTeam() == CreatureTeam.HUMAN) {
                survivors.add(currentCreature.getId() + " " + currentCreature.getName());
            }
        }

        survivors.add("");
        survivors.add("OS OUTROS");
        for (Creature currentCreature : getCreatures()) {
            if (currentCreature.getTeam() == CreatureTeam.ZOMBIE) {
                survivors.add(currentCreature.getId() + " (antigamente conhecido como " + currentCreature.getName()+")");
            }
        }

        survivors.add("-----");
        return survivors;
    }

    public List<Integer> getIdsInSafeHaven() {
        List<Integer> ids = new ArrayList<>();
        for (SafeHaven currentSafeHaven : getSafeHavens()) {
            for (Creature currentCreature : currentSafeHaven.getCreatures()) {
                ids.add(currentCreature.getId());
            }
        }
        return ids;
    }
}

