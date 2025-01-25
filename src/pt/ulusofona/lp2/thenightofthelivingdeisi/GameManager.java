package pt.ulusofona.lp2.thenightofthelivingdeisi;

import pt.ulusofona.lp2.thenightofthelivingdeisi.creatures.*;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
//antes: type 0 -> zombie | 1 -> humano
//agora: team 10 -> zombie | 20 -> humano
//TODO:Melhorar o uso do Enum
public class GameManager {

    protected int currentLine;

    protected ArrayList<Unit> units;

    protected Game game;

    public GameManager() {
       this.currentLine = 1;
        this.units = new ArrayList<>();
    }

    //teamId 10 -> zombies 20 -> humanos(starting team)
    public void loadGame(File ficheiro) throws InvalidFileException,FileNotFoundException
    {
        int numCreatures,numEquipments,numSafeHavens;
        int line,column,teamId,currentTeamId;

        //reset para nao vir com informacao de jogos anteriores
        resetGame();

        try(Scanner scanner = new Scanner(ficheiro)){
            line = scanner.nextInt();
            column = scanner.nextInt();
            scanner.nextLine();
            currentLine++;

            teamId = scanner.nextInt();
            currentTeamId = teamId;
            scanner.nextLine();
            currentLine++;

            numCreatures = scanner.nextInt();
            scanner.nextLine();
            currentLine++;

            loadCreatures(scanner, line, column, numCreatures);

            //verifica se tem linha seguinte
            if(scanner.hasNextLine()){
                numEquipments = scanner.nextInt();

                if (scanner.hasNextLine()){
                    scanner.nextLine();
                    currentLine++;
                    loadEquipments(scanner, line, column, numEquipments);
                }

                //so pode existir safeHeaven se existirem equipamentos
                if (scanner.hasNextLine() && numEquipments > 0){
                    numSafeHavens = scanner.nextInt();
                    currentLine++;

                    if (scanner.hasNextLine()){
                        scanner.nextLine();
                        loadSafeHavens(scanner, line, column, numSafeHavens);
                    }
                }
            }

            this.game = new Game(line, column, currentTeamId ,teamId ,units);

        } catch (NumberFormatException e) {
            System.out.println("Error data format");
            throw new InvalidFileException("Error data format",currentLine);
        }
    }

    public void loadCreatures(Scanner scanner, int line,int column,int numCreatures) throws InvalidFileException {
        for(int i = 0; i < numCreatures; i++){
            String[] creatureData = scanner.nextLine().split(":");

            if (creatureData.length != 6){
                throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(loadCreatures) " + currentLine,currentLine);
            }

            int id = Integer.parseInt(creatureData[0].trim());
            int team = Integer.parseInt(creatureData[1].trim());
            int type = Integer.parseInt(creatureData[2].trim());
            String name = creatureData[3].trim();
            int y = Integer.parseInt(creatureData[4].trim());
            int x = Integer.parseInt(creatureData[5].trim());

            validateCreatureTeamAndType(team, type);
            validateOutOfBounds(line,column,x, y);

            Creature creature = CreatureFactory.createCreature(id, team, type, name, y, x,currentLine);

            units.add(creature);
            currentLine++;
        }
    }

    public void validateCreatureTeamAndType(int team, int type) throws InvalidFileException {
        if (getCreatureTeam(team) == CreatureTeam.ZOMBIE && getCreatureType(type) == CreatureType.DOG){
            throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(caoZombie) " + currentLine,currentLine);
        }else if (getCreatureTeam(team) == CreatureTeam.HUMAN && getCreatureType(type) == CreatureType.VAMPIRE){
            throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(VampiroHumano) " + currentLine,currentLine);
        }
        if ( team > 20 || team < 10){
            throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(team) " + currentLine,currentLine);
        }
    }

    public void loadEquipments(Scanner scanner, int line,int column,int numEquipments) throws InvalidFileException {
        for(int i = 0; i < numEquipments; i++){
            String[] equipmentData = scanner.nextLine().split(":");

            if (equipmentData.length != 4){
                throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(loadEquipments) " + currentLine,currentLine);
            }

            int id = Integer.parseInt(equipmentData[0].trim());
            int type = Integer.parseInt(equipmentData[1].trim());
            int y = Integer.parseInt(equipmentData[2].trim());
            int x = Integer.parseInt(equipmentData[3].trim());

            Equipment equipment = EquipmentFactory.createEquipment(id, type, y, x,currentLine);

            validateOutOfBounds(line,column,x, y);
            units.add(equipment);
            currentLine++;
        }
    }

    public void validateOutOfBounds(int column, int line , int x, int y) throws InvalidFileException {
        //comentado para o DP passar
        if (x < 0 || x >= column || y < 0 || y >= line){
            throw new InvalidFileException( "Atenção: Ficheiro tem um erro na linha(out of bonds) " + currentLine,currentLine);
        }
    }

    public void loadSafeHavens(Scanner scanner, int line,int column ,int numSafeHavens) throws InvalidFileException {
        for(int i = 0; i < numSafeHavens; i++){
            String[] safeHavenData = scanner.nextLine().split(":");

            if (safeHavenData.length != 2){
                throw new InvalidFileException( "SafeHaven data is invalid",currentLine-1);
            }

            int y = Integer.parseInt(safeHavenData[0].trim());
            int x = Integer.parseInt(safeHavenData[1].trim());

            validateOutOfBounds(line,column,x, y);
            SafeHaven safeHaven = new SafeHaven(y, x);
            units.add(safeHaven);
            currentLine++;
        }
    }

    //TODO:COLOCAR NO ENUM
    public CreatureTeam getCreatureTeam(int team) {
        switch (team) {
            case 10:
                return CreatureTeam.ZOMBIE;
            case 20:
                return CreatureTeam.HUMAN;
            default:
                return null;
        }
    }
    //TODO:COLOCAR NO ENUM
    public CreatureType getCreatureType(int type) {
        switch (type) {
            case 0:
                return CreatureType.KID;
            case 1:
                return CreatureType.ADULT;
            case 2:
                return CreatureType.SENIOR;
            case 3:
                return CreatureType.DOG;
            case 4:
                return CreatureType.VAMPIRE;
            default:
                return null;
        }
    }
    //TODO:COLOCAR NO ENUM
    public EquipmentType getEquipmentType(int equipmentTypeId) {
        switch (equipmentTypeId) {
            case 0:
                return EquipmentType.SHIELD;
            case 1:
                return EquipmentType.SAMURAI_SWORD;
            case 2:
                return EquipmentType.PISTOL;
            case 3:
                return EquipmentType.BLEACH;
            default:
                return null;
        }
    }

    public Game getGame() {
        return game;
    }

    public int[] getWorldSize() {
        return game.getWorldSize();
    }
    public int getInitialTeamId() {
        return game.getInitialTeamId();
    }
    public int getCurrentTeamId() {
        return game.getCurrentTeamId();
    }

    public void resetGame() {
        this.currentLine = 1;
        this.units = new ArrayList<>();
    }

    //a cada dois turno troca o dia pela noite
    //se for dia -> true
    public boolean isDay() {
        if (getGame() == null){
            return true;
        }
        return game.isDay();
    }

    public String getSquareInfo(int x, int y) {
        return this.game.getSquareInfo(x, y);
    }

    public String[] getCreatureInfo(int id) {
        return this.game.getCreatureInfo(id);
    }

    public String getCreatureInfoAsString(int id) {
        return this.game.getCreatureInfoAsString(id);
    }

    public String[] getEquipmentInfo(int id) {
        return this.game.getEquipmentInfo(id);
    }

    public String getEquipmentInfoAsString(int id) {
        return this.game.getEquipmentInfoAsString(id);
    }

    public boolean hasEquipment(int creatureId, int equipmentTypeId){
        return this.game.hasEquipment(creatureId, equipmentTypeId);
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        return game.move(xO, yO, xD, yD);
    }

    //termina 8 turnos sem transformacao de humanos em zombies ou so existe elementos de uma das equipas
    public boolean gameIsOver() {
        return game.gameIsOver();
    }

    public ArrayList<String> getSurvivors() {
        return game.getSurvivors();
    }

    public JPanel getCreditsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Desenvolvido por:"));
        panel.add(new JLabel("Pedro Cardoso"));
        panel.add(new JLabel("Rodrigo Dias"));
        return panel;
    }

    public HashMap<String,String> customizeBoard() {
        //possiveis alteraçoes
        /*
        HashMap<String, String> boardSettings = new HashMap<>();
        boardSettings.put("theme", "dark"); // Pode alterar o tema para "light", "classic", etc.
        boardSettings.put("rows", String.valueOf(game.getLine()));
        boardSettings.put("columns", String.valueOf(game.getColumn()));
        return boardSettings;*/
        return new HashMap<>();
    }

    public void saveGame(File file) throws IOException {
        //Guardar o estado do jogo num ficheiro guarda:
        //1. O tamanho do tabuleiro
        //2. A equipa que joga
        //3. O número de criaturas
        //4. A informação de cada criatura
        //5. O número de equipamentos
        //6. A informação de cada equipamento
        //7. O número de safe havens
        //8. A informação de cada safe haven

        try(FileWriter writer = new FileWriter(file)) {
            writer.write(game.getLine() + " " + game.getColumn() + "\n");
            writer.write(getInitialTeamId() + "\n");//testar no dp
            writer.write(game.getCreatures().size() + "\n");

            for (Creature currentCreature : game.getCreatures()) {
                writer.write(currentCreature.getId() + " : ");
                writer.write(currentCreature.getTeamAsInt() + " : ");
                writer.write(currentCreature.getTypeAsInt() + " : ");
                writer.write(currentCreature.getName() + " : ");
                writer.write(currentCreature.gety() + " : " +currentCreature.getx() + "\n");
            }

            writer.write(game.getEquipments().size() + "\n");

            for (Equipment currentEquipment : game.getEquipments()) {
                writer.write(currentEquipment.getId() + " : ");
                writer.write( currentEquipment.getTypeId() + " : ");
                writer.write(currentEquipment.gety() + " : " + currentEquipment.getx() + "\n");
            }

            writer.write(game.getSafeHavens().size() + "\n");

            for (SafeHaven currentSafeHaven : game.getSafeHavens()) {
                writer.write(currentSafeHaven.gety() + ":" + currentSafeHaven.getx() + "\n");
            }

        }

    }
    public List<Integer> getIdsInSafeHaven() {
        return game.getIdsInSafeHaven();
    }
}