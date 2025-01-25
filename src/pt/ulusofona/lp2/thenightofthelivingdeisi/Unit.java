package pt.ulusofona.lp2.thenightofthelivingdeisi;

//Creature
// id,type,team,name,y,x,equipmentCounter
//Equipment
// id,type,y,x,captured
public abstract class Unit {
    protected int id,y,x;
    protected boolean inSafeHaven;
    public Unit(int id, int y, int x) {
        this.id = id;
        this.y = y;
        this.x = x;
        this.inSafeHaven = false;
    }
    //construtor safeHeaven
    public Unit(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getId() {
        return id;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public void setx(int x){
        this.x = x;
    }

    public void sety(int y){
        this.y = y;
    }

    public abstract boolean isEquipment();

    public abstract boolean isCreature();

    public boolean isInSafeHaven(){
        return inSafeHaven;
    }

    public void enterSafeHaven(){
        inSafeHaven = true;
        setx(-1);
        sety(-1);
    }

    public abstract String toString();
}