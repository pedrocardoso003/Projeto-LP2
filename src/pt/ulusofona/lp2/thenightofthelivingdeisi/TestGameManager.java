package pt.ulusofona.lp2.thenightofthelivingdeisi;


import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.thenightofthelivingdeisi.equipments.Equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

   @Test
    public void testLoadGame() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));


       int[] result = gameManager.getWorldSize();
       int[] expected = {7, 7};

       assertArrayEquals(expected, result);

       int result2 = gameManager.getInitialTeamId();
       assertEquals(10, result2);

       String result3 = gameManager.getSquareInfo(2,2);
       assertEquals("H:10", result3);

    }
    @Test
    public void testLoadGameNoEquipments() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game-2H-2Z-0E.txt"));
        assertEquals(new ArrayList<Equipment>(), gameManager.game.getEquipments());
    }

    @Test
    public void testLoadGameNoSafeHavens() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game-2H-2Z-0E.txt"));

        assertEquals(new ArrayList<Unit>(), gameManager.game.getSafeHavens());
    }

    @Test
    public void testLoadGameFileDontExist() {
        GameManager gameManager = new GameManager();
        assertThrows(FileNotFoundException.class, () -> gameManager.loadGame(new File("test-files/dontExist.txt")));
    }

    @Test
    public void testLoadGameInvalidFile() {
        GameManager gameManager = new GameManager();
        assertThrows(InvalidFileException.class, () -> gameManager.loadGame(new File("test-files/invalidFileWrongDigit.txt")));
    }

    @Test
    public void testLoadGameLineWithError() {
        GameManager gameManager = new GameManager();
        assertThrows(InvalidFileException.class, () -> gameManager.loadGame(new File("test-files/lineWithError.txt")));
    }

    @Test
    public void testLoadGameVampireAsHuman() {
        GameManager gameManager = new GameManager();
        assertThrows(InvalidFileException.class, () -> gameManager.loadGame(new File("test-files/invalidVampireAsHuman.txt")));
    }

    @Test
    public void testLoadGameDogAsZombie() {
        GameManager gameManager = new GameManager();
        assertThrows(InvalidFileException.class, () -> gameManager.loadGame(new File("test-files/invalidDogAsZombie.txt")));
    }

    @Test
    public void testGetWorldSize() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        int[] result = gameManager.getWorldSize();
        int[] expected = {6, 6};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetInitialTeamId() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        int result = gameManager.getInitialTeamId();

        assertEquals(10, result);
    }

    @Test
    public void testGetCurrentTeamId() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        int result = gameManager.getCurrentTeamId();

        assertEquals(10, result);
    }
    @Test
    public void testIsDay() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        boolean result = gameManager.isDay();

        assertTrue(result);
    }
    @Test
    public void testGetSquareInfo() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        String result = gameManager.getSquareInfo(0, 0);

        assertEquals("H:1", result);

        result = gameManager.getSquareInfo(1, 0);
        assertEquals("E:-1", result);

        result = gameManager.getSquareInfo(5, 4);
        assertEquals("Z:4", result);
    }


    @Test
    public void testGetSquareInfoSafeHaven() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        String result = gameManager.getSquareInfo(6, 0);

        assertEquals("SH", result);
    }

    @Test
    public void testGetCreatureInfo() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        String[] result = gameManager.getCreatureInfo(1);
        String[] expected = {"1",  "Criança", "Humano", "Freddy M.", "0", "0",null};

        assertArrayEquals(expected, result);

        result = gameManager.getCreatureInfo(4);
        String[] expected2 = {"4", "Criança", "Zombie", "Dr.Tongue", "5", "4",null};

        assertArrayEquals(expected2, result);

    }
    @Test
    public void testGetCreatureInfoAsString() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        String result = gameManager.getCreatureInfoAsString(1);
        String expected = "1 | Criança | Humano | Freddy M. | +0 @ (0, 0)";

        assertEquals(expected, result);

        result = gameManager.getCreatureInfoAsString(4);
        expected = "4 | Criança | Zombie | Dr.Tongue | -0 @ (5, 4)";
        assertEquals(expected, result);
    }

    @Test
    public void testGetCreatureInfoAsStringWithEquipment() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(3, 3, 2, 3);
        assertTrue(result);

        result = gameManager.move(4, 3, 4, 4);
        assertTrue(result);
        //FALTA o move para a possicao 4,4

        String resultString = gameManager.getCreatureInfoAsString(7);
        String expected = "7 | Adulto | Humano | Freddie M. | +1 @ (4, 4) | -4 | Lixívia @ (4, 4) | 1.0 litros";

        assertEquals(expected, resultString);
    }
    @Test
    public void testGetEquipmentInfo() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        String[] result = gameManager.getEquipmentInfo(-1);
        String[] expected = {"-1", "0","1","0",null};

        assertArrayEquals(expected, result);
    }
    @Test
    public void testGetEquipmentInfoAsString() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        String result = gameManager.getEquipmentInfoAsString(-1);
        String expected = "-1 | Escudo de madeira @ (1, 0)";

        assertEquals(expected, result);
    }
    @Test
    public void testHasEquipment() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        boolean result = gameManager.hasEquipment(3,0);
        assertFalse(result);
    }

    @Test
    public void testMove() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.move(0, 0, 1, 0);
        assertTrue(result);
        result = gameManager.move(4, 4, 5, 5);
        assertTrue(result);
        result = gameManager.move(1, 0, 1, 2);
        assertTrue(result);

         String squareInfo = gameManager.getSquareInfo(0, 0);
        assertEquals("", squareInfo);

        squareInfo = gameManager.getSquareInfo(1, 1);
        assertEquals("H:5", squareInfo);
    }

    @Test
    public void testMoveKid() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.move(6,6,6,4);
        assertFalse(result);

        result = gameManager.move(6,6,4,6);
        assertFalse(result);

        result = gameManager.move(6,6,3,1);
        assertFalse(result);

        result = gameManager.move(6,6,1,3);
        assertFalse(result);

        result = gameManager.move(5,5,5,4);
        assertTrue(result);

        result = gameManager.move(4,4,3,3);
        assertTrue(result);

        result = gameManager.move(5,4,4,4);
        assertTrue(result);

        result = gameManager.move(3,3,2,3);
        assertTrue(result);

        result = gameManager.move(4,4,4,5);
        assertTrue(result);
    }

    @Test
    public void testMoveAdult() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.move(0,0,3,3);
        assertFalse(result);

        result = gameManager.move(0,0,3,0);
        assertFalse(result);

        result = gameManager.move(0,0,0,3);
        assertFalse(result);

    }

    @Test
    public void testMoveSenior() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.move(1,1,0,1);
        assertFalse(result);

        result = gameManager.move(1,1, 1,0);
        assertFalse(result);

        result = gameManager.move(1,1,3,3);
        assertFalse(result);

        result = gameManager.move(1,1,2,2);
        assertTrue(result);
    }

    @Test
    public void testMoveHumanSeniorNight() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/testHumanSeniorNight.txt"));

        boolean result = gameManager.move(0,0,0,1);
        assertFalse(result);

        result = gameManager.move(0,0,1,0);
        assertFalse(result);

        result = gameManager.move(0,0,2,2);
        assertFalse(result);

        result = gameManager.move(0,0,1,1);
        assertTrue(result);

        result = gameManager.move(4,4,3,3);
        assertTrue(result);

        assertFalse(gameManager.isDay());

        result = gameManager.move(1,1,0,0);
        assertFalse(result);
    }

    @Test
    public void testMoveDog() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/testHumanSeniorNight.txt"));

        boolean result = gameManager.move(5,5,4,4);
        assertFalse(result);

        result = gameManager.move(5,5,5,2);
        assertFalse(result);

        result = gameManager.move(5,5,2,5);
        assertFalse(result);

        result = gameManager.move(5,5,3,5);
        assertTrue(result);

        result = gameManager.move(4,4,3,3);
        assertTrue(result);

        result = gameManager.move(3,5,3,4);
        assertTrue(result);
    }

    @Test
    public void testMoveVampire() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/testHumanSeniorNight.txt"));

        boolean result = gameManager.move(5,5,5,4);
        assertTrue(result);

        result = gameManager.move(4,4,3,3);
        assertTrue(result);

        result = gameManager.move(5,4,5,5);
        assertTrue(result);

        result = gameManager.move(2,2,4,2);
        assertFalse(result);

        result = gameManager.move(2,2,2,4);
        assertFalse(result);

        result = gameManager.move(2,2,5,5);
        assertFalse(result);

        result = gameManager.move(2,2,2,1);
        assertTrue(result);

        result = gameManager.move(5,5,5,4);
        assertTrue(result);

        result = gameManager.move(2,1,2,0);
        assertFalse(result);
    }

    @Test
    public void testHasEquipmentTrue() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game-3H-1Z-2E.txt"));

        boolean result = gameManager.move(0, 0, 1, 0);
        assertTrue(result);

        result = gameManager.hasEquipment(1, 0);
        assertTrue(result);

        result = gameManager.move(5, 4, 5, 3);
        assertTrue(result);

        result = gameManager.move(4,4,4,3);
        assertTrue(result);

        result = gameManager.hasEquipment(3, 1);
        assertTrue(result);
    }

    @Test
    public void testPickAndDropEquipment() throws InvalidFileException, FileNotFoundException {
       GameManager gameManager = new GameManager();
       gameManager.loadGame(new File("test-files/gameTest.txt"));

       boolean result = gameManager.move(0, 0, 1, 1);
        assertTrue(result);
        assertTrue(gameManager.hasEquipment(1, 0));

        result = gameManager.move(0, 4, 1, 3);
        assertTrue(result);

        result = gameManager.move(5, 5, 4, 4);
        assertTrue(result);

        result = gameManager.move(1, 3, 2, 2);
        assertTrue(result);

        result = gameManager.move(1, 1, 0, 0);
        assertTrue(result);

        assertFalse(gameManager.hasEquipment(1, 0));
        assertEquals("E:-1", gameManager.getSquareInfo(1, 1));
    }

    @Test
    public void testDestroyEquipment() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.move(0, 4, 1, 4);//avançar turno
        assertTrue(result);
        result = gameManager.move(4, 4, 4, 3);
        assertTrue(result);
        int equipmentCount = gameManager.getGame().getEquipmentCount(3);
        assertEquals(-1, equipmentCount);

        result = gameManager.move(1, 4, 0, 4);//avançar turno
        assertTrue(result);
        result = gameManager.move(4, 3, 4, 4);
        assertTrue(result);

        assertEquals("", gameManager.getSquareInfo(3, 4));

        assertNull(gameManager.getEquipmentInfo(-2));
    }

    @Test
    public void testGameIsOver() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
            gameManager.loadGame(new File("test-files/gameVideo.txt"));

        boolean result = gameManager.gameIsOver();
        assertFalse(result);

        //jogo termina dps de 12 turnos
        for (int i = 0; i < 6; i++) {
            gameManager.move(0, 0, 1, 0);
            gameManager.move(1, 0, 0, 0);
            gameManager.move(4, 4, 3, 4);
            gameManager.move(3, 4, 4, 4);
        }

        result = gameManager.gameIsOver();
        assertTrue(result);
    }

    @Test
    public void testGetSurvivors() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/game.txt"));

        ArrayList<String> actual = gameManager.getSurvivors();

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Nr. de turnos terminados:");
        expected.add("0");
        expected.add("");
        expected.add("OS VIVOS");
        expected.add("1 Freddy M.");
        expected.add("2 Jackie Chan");
        expected.add("");
        expected.add("OS OUTROS");
        expected.add("3 (antigamente conhecido como Paciente Zero)");
        expected.add("4 (antigamente conhecido como Dr.Tongue)");
        expected.add("-----");

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testHumanEnteredSafeHaven() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(3, 3, 3, 2);
        assertTrue(result);

        result = gameManager.move(4, 3, 6, 3);
        assertTrue(result);

        result = gameManager.move(5, 3, 5, 2);
        assertTrue(result);

        result = gameManager.move(6, 3, 6, 1);
        assertTrue(result);

        result = gameManager.move(5, 2, 4, 1);
        assertTrue(result);

        result = gameManager.move(6, 1, 6, 0);
        assertTrue(result);

        result = gameManager.getSquareInfo(6, 0).equals("SH");
        assertTrue(result);

        //Quando entra fica a null getCreatureInfoAsString devolve "Safe Haven" nas coordenadas
        String[] resultCreatureInfo = gameManager.getCreatureInfo(7);
        String[] expectedArray = {"7", "Adulto", "Humano", "Freddie M.", null, null,null};
        assertArrayEquals(expectedArray, resultCreatureInfo);

        //getCreatureInfoAsString devolve "Safe Haven" nas coordenadas
        Equipment test = gameManager.getGame().getEquipments().get(0);//debug
        boolean isInSafeHaven = gameManager.game.getEquipments().get(0).isInSafeHaven();//debug
        String resultCreatureInfoStr = gameManager.getCreatureInfoAsString(7);
        String expected = "7 | Adulto | Humano | Freddie M. | +1 @ Safe Haven | -1 | Escudo de madeira @ Safe Haven";
        assertEquals(expected, resultCreatureInfoStr);

        List<Integer> resultIds = gameManager.getIdsInSafeHaven();
        assertEquals(7, resultIds.get(0));
   }

    @Test
    public void testZombieEnteredSafeHaven() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(5, 3, 5, 1);
        assertTrue(result);

        result = gameManager.move(4, 3, 4, 1);
        assertTrue(result);

        result = gameManager.move(5, 1, 6, 0);
        assertFalse(result);
    }

    @Test
    public void testAttackAndDefend() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(5, 3, 5, 4);
        assertTrue(result);

        result = gameManager.move(4, 3, 4, 4);
        assertTrue(result);

        result = gameManager.move(5, 4, 4, 4);
        assertTrue(result);

        result = gameManager.hasEquipment(7,3);
        assertTrue(result);

        //verificar capacidade da lixivia
        float resultCapacaity = gameManager.game.getEquipments().get(3).getCapacity();
        assertEquals(0.7F, resultCapacaity);

        assertEquals(CreatureTeam.HUMAN, gameManager.getGame().getCurrentTeam());

        result = gameManager.move(3, 4, 2, 4);
        assertTrue(result);

        result = gameManager.move(5, 4, 4, 4);
        assertTrue(result);

        resultCapacaity = gameManager.game.getEquipments().get(3).getCapacity();
        assertEquals(0.4F, resultCapacaity);

        result = gameManager.move(2, 4, 1, 4);
        assertTrue(result);

        result = gameManager.move(5, 4, 4, 4);
        assertTrue(result);

        resultCapacaity = gameManager.game.getEquipments().get(3).getCapacity();
        assertEquals(0.1F, resultCapacaity);

        result = gameManager.move(1, 4, 0, 4);
        assertTrue(result);

        result = gameManager.move(5, 4, 4, 4);
        assertTrue(result);

        resultCapacaity = gameManager.game.getEquipments().get(3).getCapacity();
        assertEquals(0.0F, resultCapacaity);

        result = gameManager.move(0,4,1,4);
        assertTrue(result);

        result = gameManager.move(5, 4, 4, 4);
        assertTrue(result);

        assertEquals("Z:2", gameManager.getSquareInfo(5, 4));
        assertEquals("Z:7", gameManager.getSquareInfo(4, 4));

        String[] expected = {"7", "Adulto", "Zombie (Transformado)","Freddie M.", "4", "4",null};
        String[] resultCreatureInfo = gameManager.getCreatureInfo(7);
        assertArrayEquals(expected, resultCreatureInfo);

    }

    @Test
    public void testHumanTransformed() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(5, 3, 4, 3);
        assertTrue(result);

        assertEquals("Z:2", gameManager.getSquareInfo(5, 3));
        assertEquals("Z:7", gameManager.getSquareInfo(4, 3));

        String[] expected = {"7", "Adulto", "Zombie (Transformado)","Freddie M.", "4", "3",null};
        String[] resultCreatureInfo = gameManager.getCreatureInfo(7);
        assertArrayEquals(expected, resultCreatureInfo);
    }

    @Test
    public void testHumanDestroyZombie() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameTest2.txt"));

        assertEquals(3,gameManager.game.getCreatures().size());
        boolean result = gameManager.move(0, 1, 1, 1);
        assertTrue(result);

        result = gameManager.move(0, 0, 0, 1);
        assertTrue(result);

        result = gameManager.move(1, 1, 0, 1);
        assertTrue(result);

        assertEquals("H:2", gameManager.getSquareInfo(0, 1));
        assertEquals(2,gameManager.game.getCreatures().size());
    }

    @Test
    public void testHumanAttackZombieWithPistol() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(3, 3, 2, 3);
        assertTrue(result);

        result = gameManager.move(4, 3, 2, 1);
        assertTrue(result);
        assertTrue(gameManager.hasEquipment(7, 2));

        result = gameManager.move(5, 3, 3, 3);
        assertTrue(result);

        result = gameManager.move(2, 1, 1, 1);
        assertTrue(result);
        assertEquals("H:7", gameManager.getSquareInfo(1, 1));
        assertEquals("",gameManager.getSquareInfo(2, 1));

        result = gameManager.move(2, 3, 1, 3);
        assertTrue(result);

        result = gameManager.move(1, 1, 1, 3);
        assertTrue(result);
        assertEquals("H:7", gameManager.getSquareInfo(1, 3));
        assertEquals("",gameManager.getSquareInfo(1, 1));

        result = gameManager.move(3, 3, 1, 3);
        assertTrue(result);
        assertEquals("Z:2", gameManager.getSquareInfo(3, 3));
        assertEquals("H:7", gameManager.getSquareInfo(1, 3));

        result = gameManager.move(1, 3, 3, 3);
        assertFalse(result);

        result = gameManager.move(1, 3, 2, 3);
        assertTrue(result);

        result = gameManager.move(3, 3, 2, 3);
        assertTrue(result);
        assertEquals("Z:7", gameManager.getSquareInfo(2, 3));
        assertEquals("Z:2",gameManager.getSquareInfo(3, 3));
        String[] expected = {"7", "Adulto", "Zombie (Transformado)","Freddie M.", "2", "3",null};
        String[] resultCreatureInfo = gameManager.getCreatureInfo(7);
        assertArrayEquals(expected, resultCreatureInfo);
    }

    @Test
    public void testGameOverWithNoHumans() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(5, 3, 4, 3);
        assertTrue(result);

        result = gameManager.move(2, 2, 2, 4);
        assertTrue(result);

        result = gameManager.move(4, 3, 6, 5);
        assertTrue(result);

        result = gameManager.move(2, 4, 2, 6);
        assertTrue(result);

        result = gameManager.move(4, 5, 3, 4);
        assertTrue(result);

        result = gameManager.move(2, 6, 1, 6);
        assertTrue(result);

        result = gameManager.move(6, 5, 5, 6);
        assertTrue(result);

        result = gameManager.move(1, 6, 0, 6);
        assertTrue(result);
        assertTrue(gameManager.gameIsOver());
    }

    @Test
    public void testGameOverNoTransformations()throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        for (int i = 0; i < 4; i++) {
            gameManager.move(1, 1, 1, 0);
            gameManager.move(1, 0, 1, 1);
            gameManager.move(2, 2, 3, 2);
            gameManager.move(3, 2, 2, 2);
        }
        assertTrue(gameManager.gameIsOver());
    }

    @Test
    public void testSaveGame() throws InvalidFileException, IOException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(1, 1, 1, 2);
        assertTrue(result);

        gameManager.saveGame(new File("test-files/saveGame.txt"));

        GameManager gameManager2 = new GameManager();
            gameManager2.loadGame(new File("test-files/saveGame.txt"));

        assertEquals("Z:5", gameManager2.getSquareInfo(1, 2));
        assertEquals("", gameManager2.getSquareInfo(1, 1));

    }

    @Test
    public void testGetEquipmentInfoCaptured() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(3,3,3,2);
        assertTrue(result);

        result = gameManager.move(4,3,6,3);
        assertTrue(result);
        assertTrue(gameManager.hasEquipment(7,0));

        String[] resultEquipInfo = gameManager.getEquipmentInfo(-1);

        assertNull(resultEquipInfo);

        String resultEquipInfoStr = gameManager.getEquipmentInfoAsString(-1);
        assertNull(resultEquipInfoStr);

    }

    @Test
    public void testgetSquareInfoOutOfBoard() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        String result = gameManager.getSquareInfo(9, 9);
        assertNull(result);
    }

    @Test
    public void testEnteredSafeHavenWithShield() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        boolean result = gameManager.move(5, 3, 4, 3);
        assertTrue(result);

        result = gameManager.move(6, 5, 6, 3);
        assertTrue(result);

        result = gameManager.move(4, 5, 5, 6);
        assertTrue(result);

        result = gameManager.move(6, 3, 6, 1);
        assertTrue(result);

        result = gameManager.move(5, 3, 5, 2);
        assertTrue(result);

        result = gameManager.move(6, 1, 6, 0);
        assertTrue(result);

        //assertEquals("teste",gameManager.getEquipmentInfo(-1));
    }

    @Test
    public void testInvalidMoveAdult() throws InvalidFileException, FileNotFoundException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameTest3.txt"));

        boolean result = gameManager.move(4, 0, 5, 2);
        assertFalse(result);

        result = gameManager.move(3, 3, 2, 5);
        assertFalse(result);

        result = gameManager.move(5, 2, 3, 3);
        assertFalse(result);

        result = gameManager.move(2, 5, 0, 4);
        assertFalse(result);
    }

    @Test
    public void testGameOver() throws InvalidFileException, FileNotFoundException{
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/gameVideo2.txt"));

        assertEquals(0,gameManager.getGame().getTurnCountWithoutAttack());

        boolean result = gameManager.move(5, 3, 4, 4);
        assertTrue(result);

        result = gameManager.move(3, 4, 3, 5);
        assertTrue(result);

        assertEquals(2,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(3, 3, 4, 3);
        assertTrue(result);

        assertEquals(0,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(2, 2, 3, 2);
        assertTrue(result);

        assertEquals(1,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(4, 5, 5, 6);
        assertTrue(result);

        assertEquals(0,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(3, 2, 4, 2);
        assertTrue(result);

        assertEquals(1,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(4, 4, 3, 5);
        assertTrue(result);

        assertEquals(0,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(4, 2, 5, 2);
        assertTrue(result);

        assertEquals(1,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(5, 6, 6, 5);
        assertTrue(result);

        assertEquals(0,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(5, 2, 5, 0);
        assertTrue(result);

        result = gameManager.move(6, 5, 6, 3);
        assertTrue(result);

        assertEquals(2,gameManager.getGame().getTurnCountWithoutAttack());

        result = gameManager.move(5, 0, 6, 0);
        assertTrue(result);
        assertTrue(gameManager.gameIsOver());
    }

}