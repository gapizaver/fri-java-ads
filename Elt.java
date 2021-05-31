import java.util.Comparator;

public class Elt implements Comparator<Elt> {
    public int key;
    public Object data;


    public Elt(int key) {
        this.key=key;
        data=null;
    }

    @Override
    public String toString() {
        return Integer.toString(key);
    }


    @Override
    public int compare(Elt o1, Elt o2) {
        return Integer.compare(o1.key, o2.key);
    }
}
