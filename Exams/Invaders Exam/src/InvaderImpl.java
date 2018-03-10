public class InvaderImpl implements Invader, Comparable<Invader>{

    private int damage;
    private int distance;

    public InvaderImpl(int damage, int distance) {
        this.damage = damage;
        this.distance = distance;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int compareTo(Invader o) {
        int comp = Integer.compare(this.distance, o.getDistance());
        if (comp == 0){
            comp = Integer.compare(this.damage, o.getDamage());
        }
        return comp;
    }

}
