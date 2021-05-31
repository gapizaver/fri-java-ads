public class Seznam {
    private Elt head;
    private Seznam tail;
    private static int comparisons = 0;

    public Seznam (Elt elt, Seznam rep) {
        this.head = elt;
        this.tail = rep;
    }


    private static Seznam changeValue(Seznam s, Elt e) {
        if (s == null) {
            return new Seznam(e, null);
        }

        if (e.key == s.head.key){
            return new Seznam(e, s.tail);
        } else {
            return new Seznam(s.head, changeValue(s.tail, e));
        }
    }


    public static Seznam insert(Seznam s, Elt e) {
        if (s == null) {
            return new Seznam(e, null);
        } else {
            if (find(s, e.key) != null && find(s, e.key).key == e.key) {
                return changeValue(s, e);
            } else {
                return new Seznam(e, s);
            }
        }
    }

    public static Elt find(Seznam s, int key){
        if (s == null) {
            return null;
        }

        comparisons++;
        if (s.head.key == key) {
            return s.head;
        } else {
            return find(s.tail, key);
        }
    }

    public static Seznam delete(Seznam s, int key){
        if (s == null) {
            return null;
        }

        comparisons++;
        if (s.head.key == key){
            return s.tail;
        } else {
            return new Seznam(s.head, delete(s.tail, key));
        }
    }

    public static void printElementKeys(Seznam s){
        Seznam s1 = s;

        while (s1 != null) {
            System.out.println(s1.head.key);
            s1 = s1.tail;
        }
    }

    public static void printElementKeyComparisons(Seznam s) {
        System.out.println(comparisons);
    }
}
