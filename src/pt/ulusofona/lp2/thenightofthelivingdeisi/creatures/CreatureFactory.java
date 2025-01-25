package pt.ulusofona.lp2.thenightofthelivingdeisi.creatures;

import pt.ulusofona.lp2.thenightofthelivingdeisi.InvalidFileException;

import static pt.ulusofona.lp2.thenightofthelivingdeisi.CreatureType.getCreatureType;

public class CreatureFactory {
    public static Creature createCreature(int id, int team ,int type, String name ,int y, int x, int currentLine) throws InvalidFileException {
        if (getCreatureType(type) == null) {
            throw new InvalidFileException("Tipo de criatura inválido na linha " + currentLine, currentLine);
        }

        switch (getCreatureType(type)) {
            case KID:
                return new Kid(id, team ,type, name, y, x);
            case ADULT:
                return new Adult(id, team ,type, name, y, x);
            case SENIOR:
                return new Senior(id, team ,type, name, y, x);
            case DOG:
                return new Dog(id, team ,type, name, y, x);
            case VAMPIRE:
                return new Vampire(id, team ,type, name, y, x);
            default:
                throw new InvalidFileException("Tipo de criatura inválido na linha " + currentLine, currentLine);
        }
    }
}
