import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sort {
    private static int sortMoves = 0;
    private static int sortComparisons = 0;

    public static void main(String[] args) {
        try {
            UserInterface.main(new String[0]);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    // način delovanja: "trace", "count"
    static String mode = "trace";
    // smer urejanja: "up", "down"
    static String direction = "up";

    // zamenjava elementov v tabeli
    private static void swap(int[] a, int i1, int i2){
        int tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
        sortMoves += 3;
    }

    // izpis tabele
    private static void print(int [] a, int line){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i == line) {
                System.out.print(" |");
            }
            if (i != a.length - 1){
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // izbira sorta
    public static int[] sort(int[] a, String which) {
        sortMoves = 0;
        sortComparisons = 0;

        // izpis začetnega stanja
        if (mode.equals("trace")) {
            print(a, -1);
        }

        // izpis končnega stanja
        switch (which) {
            case "insert":
                insertion(a);
                break;
            case "select":
                selection(a);
                break;
            case "bubble":
                bubble(a);
                break;
            case "heap":
                heap(a);
                break;
            case "merge":
                merge(a);
                break;
            case "quick":
                quick(a, 0, a.length - 1);
                if (mode.equals("trace")) {
                    print(a, -1);
                }
                break;
            case "radix":
                a = radix(a);
                break;
            case "bucket":
                bucket(a);
                break;
            default:
                System.out.println("ERROR: unknown sort method");
        }
        // izpis števila premikov in primerjav
        if (mode.equals("count")) {
            System.out.print(sortMoves + " " + sortComparisons);
        }

        return a;
    }


    // navadno vstavljanje - insertion sort
    public static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int num = a[i];
            sortMoves++;
            int j = i;

            if (direction.equals("up")) {
                sortComparisons++;
                while (j > 0 && num < a[j - 1]) {
                    a[j] = a[j - 1];
                    sortMoves++;
                    sortComparisons++;
                    j--;
                }

                a[j] = num;
                sortMoves++;

                if (j == 0) {
                    sortComparisons--;
                }
            } else {
                sortComparisons++;

                while (j > 0 && num > a[j - 1]) {
                    a[j] = a[j - 1];
                    sortMoves++;
                    sortComparisons++;
                    j--;
                }

                a[j] = num;
                sortMoves++;

                if (j == 0) {
                    sortComparisons--;
                }
            }

            if (mode.equals("trace")) {
                print(a, i);
            }
        }
    }


    // navadno izbiranje - selection sort
    public static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int m = i;
            for (int j = i + 1; j < a.length; j++){
                // urejanje naraščajoče
                if (direction.equals("up")) {
                    if (a[j] < a[m]) {
                        m = j;
                    }
                // urejanje padajoče
                } else if (direction.equals("down")) {
                    if (a[j] > a[m]){
                        m = j;
                    }
                }
            }
            swap(a, i, m);

            // izpis sledi
            if (mode.equals("trace")){
                print(a, i);
            }
        }

        // izračunaj premike in primerjanja
        sortMoves = (a.length - 1) * 3;                     // premiki
        sortComparisons = (a.length * (a.length-1)) / 2;    // primerjave
    }


    // navadna zamenjava - bubble sort
    public static void bubble(int[] a) {
        int nextLastSwitch = 0;  // naslednja zadnja zamenjava
        int lastSwitch = 1;      // trenutna zadnja zamenjava

        while (lastSwitch < a.length - 1) {
            lastSwitch = nextLastSwitch;
            nextLastSwitch = a.length-1;

            // urejaj
            for (int i = a.length - 1; i > lastSwitch; i--) {

                // urejanje naraščajoče
                if (direction.equals("up")) {
                    sortComparisons++;
                    if (a[i] < a[i - 1]) {
                        swap(a, i, i - 1);
                        sortMoves += 3;
                        nextLastSwitch = i;
                    }
                    // urejanje padajoče
                } else if (direction.equals("down")) {
                    sortComparisons++;
                    if (a[i] > a[i - 1]) {
                        swap(a, i, i - 1);
                        sortMoves += 3;
                        nextLastSwitch = i;
                    }
                }
            }

            // izpiši sled
            if (mode.equals("trace") && nextLastSwitch != lastSwitch) {
                print(a, nextLastSwitch - 1);
            }
        }
    }


    // ugrezanje elementa v kopici
    private static void siftDown(int[] a, int last, int p) {
        int c = 2*p +1;
        while (c <= last) {
            // uredi naraščajoče - max kopica
            if (direction.equals("up")) {
                sortComparisons++;
                if (c + 1 <= last && a[c + 1] > a[c]) {
                    c++;
                }
                sortComparisons++;
                if (a[p] >= a[c]) {
                    break;
                }
            // uredi padajoče - min kopica
            } else {
                sortComparisons++;
                if (c + 1 <= last && a[c + 1] < a[c]) {
                    c++;
                }
                sortComparisons++;
                if (a[p] <= a[c]) {
                    break;
                }
            }

            swap(a, p, c);
            p = c;
            c = 2*p +1;
        }
    }

    // urejanje s kopico - heap sort
    public static void heap(int[] a) {
        // zadnji element v kopici:
        int last = a.length - 1;

        // zgradi kopico
        for (int i = a.length/2-1; i >= 0; i--) {
            siftDown(a, last, i);
        }

        // izpis vmesnega stanja
        if (mode.equals("trace")) {
            print(a, last);
        }

        // uredi
        while (last >= 1) {
            swap(a, 0, last);
            last--;
            siftDown(a, last, 0);
            // izpis sledi
            if (mode.equals("trace")) {
                print(a, last);
            }
        }
    }


    // urejanje z zlivanjem - merge sort
    public static int[] merge(int[] a) {
        if (a.length <= 1) {
            return a;
        }

        // dobi elemente leve in desne strani
        int middle = (a.length - 1) / 2;
        int[] leftElements = new int[middle + 1];
        int[] rightElements = new int[a.length - middle - 1];
        sortMoves += 2;

        for (int i = 0; i < leftElements.length; i++) {
            leftElements[i] = a[i];
        }
        for (int i = 0; i < rightElements.length; i++) {
            rightElements[i] = a[i + middle + 1];
        }

        // izpiši elemente leve in desne strani
        if (mode.equals("trace")) {
            for (int i = 0; i < leftElements.length; i++) {
                System.out.print(leftElements[i] + " ");
            }
            System.out.print("| ");
            for (int i = 0; i < rightElements.length; i++) {
                System.out.print(rightElements[i]);
                if (i < rightElements.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // rekurzivno deli
        int[] left = merge(leftElements);
        int[] right = merge(rightElements);

        // zlivaj
        int[] merged = new int[left.length + right.length];
        int i, j, t;
        i = j = t = 0;
        sortMoves ++;

        while (i < left.length && j < right.length) {
            // urejanje naraščajoče
            if (direction.equals("up")) {
                if (left[i] < right[j]) {
                    merged[t++] = left[i++];
                } else {
                    merged[t++] = right[j++];
                }
            // urejanje padajoče
            } else {
                if (left[i] > right[j]) {
                    merged[t++] = left[i++];
                } else {
                    merged[t++] = right[j++];
                }
            }
            sortComparisons ++;
            sortMoves ++;
        }
        // izprazni levo
        while (i < left.length) {
            sortMoves ++;
            merged[t++] = left[i++];
        }
        // izprazni desno
        while (j < right.length) {
            sortMoves ++;
            merged[t++] = right[j++];
        }

        // izpiši zlito tabelo
        if (mode.equals("trace")) {
            print(merged, -1);
        }

        return merged;
    }


    // particiranje za quicksort
    private static int partition(int[] a, int left, int right) {
        int p = a[left];
        int l = left;
        int r = right + 1;

        while (true) {
            // urejanje naraščajoče
            if (direction.equals("up")) {
                do {
                    l++;
                    sortComparisons++;
                } while (a[l] < p && l < right);
                do {
                    r--;
                    sortComparisons++;
                } while (a[r] > p);
            // urejanje padajoče
            } else {
                do {
                    l++;
                    sortComparisons++;
                } while (a[l] > p && l < right);
                do {
                    r--;
                    sortComparisons++;
                } while (a[r] < p);
            }
            // kazalca se prekrižata
            //sortComparisons++;
            if (l >= r) {
                break;
            }
            // zamenjaj kazalca
            swap(a, l, r);
        }
        // zamenjaj pivot
        swap(a, left, r);

        return r;
    }

    // hitro urejanje - quicksort
    public static void quick(int[] a, int left, int right) {
        //sortComparisons++;
        if (left >= right) {
            return;
        }

        int r = partition(a, left, right);
        sortMoves++;

        // izpiši sled
        if (mode.equals("trace")) {
            for (int i = left; i <= right; i++) {
                // pivot
                if (i == r) {
                    System.out.print("| ");
                }
                System.out.print(a[i]);
                // pivot
                if (i == r) {
                    System.out.print(" |");
                }
                // zadnji
                if (i != right) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // rekurzija
        quick(a, left, r-1);
        quick(a, r+1, right);
    }


    // korensko urejanje - radix
    public static int[] radix(int[] a) {
        // bodoče urejena tabela
        int[] aSorted = new int[a.length];

        // najdi največji element
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        // eksponent 10^x od max
        int exp = (int) Math.ceil(Math.log10(max));

        // loop skozi potence števila 10
        int counter = 0;
        for (int i = 1; i < Math.pow(10, exp); i *= 10) {
            // števec cifer
            int[] c = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            // preštej cifre
            for (int j = a.length - 1; j >= 0; j--) {
                int digit = (a[j] / i) % 10;

                // naraščajoče urejanje
                if (direction.equals("up")) {
                    c[digit]++;
                // padajoče urejanje
                } else {
                    c[(digit - 9) * -1]++;
                }
            }

            // kumulativa
            for (int j = 1; j < c.length; j++) {
                c[j] += c[j-1];
            }

            // uredi
            for (int j = a.length - 1; j >= 0; j--) {
                int digit = (a[j] / i) % 10;
                int index;

                // naraščajoče urejanje
                if (direction.equals("up")) {
                    index = digit;
                // padajoče urejanje
                } else {
                    index = (digit - 9) * -1;
                }

                c[index]--;
                aSorted[c[index]] = a[j];
            }

            // copy a -> aSorted
            for (int j = 0; j < a.length; j++) {
                a[j] = aSorted[j];
            }

            // izpiši sled
            if (mode.equals("trace")) {
                print(aSorted, -1);
            }
            counter++;
        }

        // izračunaj število premikov in primerjav po formuli
        sortMoves = counter * 2 * a.length;
        sortComparisons = counter * 2 * a.length;

        return aSorted;
    }


    // urejanje s koši - bucket sort
    // k = n / 2
    public static void bucket(int[] a) {
        // bodoče urejena tabela
        int[] aSorted = new int[a.length];

        // poišči najmanjši in največji element
        int min, max;
        min = max = a[0];

        for (int i = 1; i < a.length; i++) {
            sortComparisons++;
            if (a[i] < min) {
                min = a[i];
            } else {
                sortComparisons++;
                if (a[i] > max) {
                    max = a[i];
                }
            }
        }


        // število košev
        int k = a.length / 2;
        // v = (max - min + 1) / k
        double v = (max - min + 1) / (double) k;
        int[] buckets = new int[k];

        // nastavi števce košev na 0
        for (int i = 0; i < k; i++) {
            buckets[i] = 0;
        }

        // prištej elemente, ki spadajo v določen koš
        for (int i = 0; i < a.length; i++) {
            int xi = (int) Math.floor((a[i] - min)/ v);
            // urejanje naraščajoče
            if (direction.equals("up")) {
                buckets[xi]++;
            // urejanje padajoče
            } else {
                buckets[(xi - k + 1) * -1]++;
            }
        }

        // kumulativa
        for (int i = 1; i < k; i++) {
            buckets[i] += buckets[i - 1];
        }

        // urejanje po koših
        for (int i = a.length - 1; i >= 0; i--) {
            int xi = (int) Math.floor((a[i] - min)/ v);
            int index = xi;

            // urejanje naraščajoče
            if (direction.equals("down")) {
                index = (xi - k + 1) * -1;
            }

            aSorted[--buckets[index]] = a[i];
        }

        // povečaj števca premikov in primerjav
        sortMoves += 2 * a.length;
        sortComparisons += 2 * a.length;

        // izpiši sled, koši ločeni z "|"
        if (mode.equals("trace")) {
            for (int i = 0; i < aSorted.length; i++) {
                // izpiši "|" če konec koša
                for (int j = 1; j < k; j++) {
                    if (i == buckets[j]) {
                        System.out.print("| ");
                    }
                }
                System.out.print(aSorted[i]);
                // če ni konec izpiši presledek
                if (i < aSorted.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        insertion(aSorted);

        a = aSorted;
    }
}


class UserInterface {
    public static void main(String[] args) throws IOException {
        BufferedReader bf =
                new BufferedReader(new InputStreamReader(System.in));

        // preberi nastavitve urejanja
        String[] parameters = bf.readLine()
                .replaceAll("\\s", ",").split(",");
        // nastavi nastavitve
        Sort.mode = parameters[0];
        String sort = parameters[1];
        Sort.direction = parameters[2];

        // preberi zaporedje
        String[] input = bf.readLine()
                .replaceAll("\\s", ",").split(",");

        // spremeni zaporedje Stringov v int
        int[] sequence = new int[input.length];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = Integer.parseInt(input[i]);
        }

        // kliči metodo urejanja
        int[] sorted = Sort.sort(sequence, sort);

        // dodatna sortiranja za mode count
        if (parameters[0].equals("count")) {
            System.out.print(" | ");
            // urejanje že urejenega seznama
            Sort.sort(sorted, sort);

            System.out.print(" | ");
            // urejanje že urejenega seznama v nasprotni smeri
            if (parameters[2].equals("down")) {
                Sort.direction = "up";
            } else {
                Sort.direction = "down";
            }
            Sort.sort(sorted, sort);
        }
    }
}
