public class Player {
    public String name;
    public Battlefield battlefield;
    public Player(String name) {
        this.battlefield = new Battlefield();
        this.name = name;
    }
}
