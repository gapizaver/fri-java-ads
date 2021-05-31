public class HTB {
    private Integer[] table;
    private final int p;        // množitelj
    private int m;              // velikost tabele
    private final int c1;       // konstanta za računanje novih indexov pri preusmerjanju
    private final int c2;       // konstanta za računanje novih indexov pri preusmerjanju
    private int collisions;     // primeri, ko želimo na mesto i v tabeli vstaviti element, pa je to mesto že zasedeno

    public HTB(int p, int m, int c1, int c2){
        table = new Integer[m];
        this.p = p;
        this.m = m;
        this.c1 = c1;
        this.c2 = c2;
    }

    public void insert(int key) {
        for (int i = 0; i < m; i++) {
            // izračunaj index za i-ti poizkus
            int index = (((key * p) % m) + c1 * i + c2 * i * i) % m;

            // prostor, vpiši vrednost
            if (table[index] == null){
                table[index] = key;
                return;
            }

            if (table[index] == key)
                return;

            collisions++;
        }

        resize(key);
    }

    private void resize(int key) {
        // povečaj tabelo
        Integer[] table_temp = new Integer[m];
        for (int i = 0; i < m; i++) {
            table_temp[i] = table[i];
        }

        // insertaj stare elemente v novo tabelo
        int m_new = 2*m +1;
        table = new Integer[m_new];
        int m_old = m;
        m = m_new;
        for (int i = 0; i < m_old; i++) {
            if (table_temp[i] != null) {
                insert(table_temp[i]);
            }
        }

        // dodaj še element, ki je vse skupaj zakuhal
        insert(key);
    }

    public void printKeys() {
        for (int i = 0; i < m; i++) {
            if (table[i] != null)
                System.out.printf("%d: %d\n", i, table[i]);
        }
    }

    public boolean find(int key) {
        for (int i = 0; i < m; i++) {
            // izračunaj index za i-ti poizkus
            int index = (((key * p) % m) + c1 * i + c2 * i * i) % m;

            // prostor, element ni bil najden
            if (table[index] == null){
                return false;
            }

            // element najden
            if (table[index] == key)
                return true;
        }

        return false;
    }

    public void delete(int key){
        for (int i = 0; i < m; i++) {
            // izračunaj index za i-ti poizkus
            int index = (((key * p) % m) + c1 * i + c2 * i * i) % m;

            // prostor, element ni bil najden
            if (table[index] == null){
                return;
            }

            // element najden
            if (table[index] == key)
                table[index] = null;
        }
    }

    public void printCollisions(){
        System.out.println(collisions);
    }
}
