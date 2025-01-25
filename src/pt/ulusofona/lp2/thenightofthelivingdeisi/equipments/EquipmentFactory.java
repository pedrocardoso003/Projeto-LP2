package pt.ulusofona.lp2.thenightofthelivingdeisi.equipments;

import pt.ulusofona.lp2.thenightofthelivingdeisi.InvalidFileException;

import static pt.ulusofona.lp2.thenightofthelivingdeisi.EquipmentType.getEquipmentType;

public class EquipmentFactory {
    public static Equipment createEquipment(int id, int type, int y, int x, int currentLine) throws InvalidFileException {

        if (getEquipmentType(type) == null) {
            throw new InvalidFileException("Tipo de equipamento inválido na linha " + currentLine, currentLine);
        }

        switch (getEquipmentType(type)) {
            case SHIELD:
                return new Shield(id, type, y, x);
            case SAMURAI_SWORD:
                return new SamuraiSword(id, type, y, x);
            case PISTOL:
                return new Pistol(id, type, y, x);
            case BLEACH:
                return new Bleach(id, type, y, x);
            default:
                throw new InvalidFileException("Tipo de equipamento inválido na linha " + currentLine, currentLine);}
    }
}
