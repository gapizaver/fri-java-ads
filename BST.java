public class BST {
    private static int comparisons = 0;
    private static boolean useLeftSubtreeOnDelete = true;
    private int el;
    private int duplicates = 0;
    private BST leftSubTree;
    private BST rightSubTree;

    public void printNodeComparisons() {
        System.out.println(comparisons);
    }

    // vstavi element
    public void insert(int el) {
        if (this.duplicates == 0) {
            this.el = el;
            this.duplicates = 1;
        } else {
            comparisons++;
            if (el == this.el){
                this.duplicates++;
            } else if (el < this.el) {
                if (leftSubTree == null)
                    leftSubTree = new BST();
                leftSubTree.insert(el);
            } else {
                if (rightSubTree == null)
                    rightSubTree = new BST();
                rightSubTree.insert(el);
            }
        }
    }

    // poisci, ali obstaja element
    public boolean find(int key) {
        if (this.duplicates == 0) {
            return false;
        }

        comparisons += 1;
        // element najden
        if (this.el == key) {
            return true;
        }

        // ce element manjsi, pojdi v levo poddrevo
        else if (key < this.el){
            if (this.leftSubTree == null)
                return false;
            return leftSubTree.find(key);
        }

        // element je vecji, pojdi v desno poddrevo
        else {
            if (this.rightSubTree == null)
                return false;
            return rightSubTree.find(key);
        }
    }

    // vmesni obhod
    public void printInorder() {
        if (this.duplicates == 0)
            return;

        if (leftSubTree != null)
            leftSubTree.printInorder();

        System.out.println(this.el);

        if (rightSubTree != null) {
            rightSubTree.printInorder();
        }
    }

    // vmesni obhod
    public void printPreorder() {
        if (this.duplicates == 0)
            return;

        System.out.println(this.el);

        if (leftSubTree != null) {
            leftSubTree.printPreorder();
        }

        if (rightSubTree != null) {
            rightSubTree.printPreorder();
        }
    }

    // premi obhod
    public void printPostorder() {
        if (this.duplicates == 0)
            return;

        if (leftSubTree != null)
            leftSubTree.printPostorder();

        if (rightSubTree != null)
            rightSubTree.printPostorder();

        System.out.println(this.el);
    }

    // izbrisi element
    public void delete(int key) {
        // poisci, ali obstaja element
        if (this.duplicates == 0) {
            return;
        }

        comparisons++;
        // element najden
        if (this.el == key){
            // duplicirana vrednost
            if (this.duplicates > 1){
                this.duplicates--;
                return;
            }

            // odstrani element
            boolean imaLevoPoddrevo = this.leftSubTree != null && this.leftSubTree.duplicates > 0;
            boolean imaDesnoPoddrevo = this.rightSubTree != null && this.rightSubTree.duplicates > 0;
            // vozlisce nima poddreves
            if (!imaLevoPoddrevo && !imaDesnoPoddrevo){
                this.duplicates = 0;
            }
            // vozlisce ima samo levo poddrevo
            else if (imaLevoPoddrevo && !imaDesnoPoddrevo) {
                this.el = this.leftSubTree.el;
                this.duplicates = this.leftSubTree.duplicates;
                this.rightSubTree = this.leftSubTree.rightSubTree;
                this.leftSubTree = this.leftSubTree.leftSubTree;
            }
            // vozlisce ima samo desno poddrevo
            else if (!imaLevoPoddrevo && imaDesnoPoddrevo) {
                this.el = this.rightSubTree.el;
                this.duplicates = this.rightSubTree.duplicates;
                this.leftSubTree = this.rightSubTree.leftSubTree;
                this.rightSubTree = this.rightSubTree.rightSubTree;
            }
            // vozlisce ima levo in desno poddrevo
            else {
                if (useLeftSubtreeOnDelete) {
                    // poišči največji element v levem poddrevesu ki bo zamenjal izbrisani element
                    BST zamenjava = this.leftSubTree;
                    while (zamenjava.rightSubTree != null) {
                        zamenjava = zamenjava.rightSubTree;
                    }

                    this.el = zamenjava.el;
                    this.duplicates = zamenjava.duplicates;

                    // rekurzijsko odstrani zamenjavo;
                    zamenjava.duplicates = 1;
                    zamenjava.delete(zamenjava.el);
                } else {
                    // poišči najmanjši element v desnem poddrevesu ki bo zamenjal izbrisani element
                    BST zamenjava = this.rightSubTree;
                    while (zamenjava.leftSubTree != null) {
                        zamenjava = zamenjava.leftSubTree;
                    }

                    this.el = zamenjava.el;
                    this.duplicates = zamenjava.duplicates;

                    // rekurzijsko odstrani zamenjavo;
                    zamenjava.duplicates = 1;
                    zamenjava.delete(zamenjava.el);
                }

                useLeftSubtreeOnDelete = !useLeftSubtreeOnDelete;
            }
        }

        // ce element manjsi, pojdi v levo poddrevo
        else if (key < this.el){
            if (this.leftSubTree == null)
                return;
            leftSubTree.delete(key);
        }

        // element je vecji, pojdi v desno poddrevo
        else {
            if (this.rightSubTree == null)
                return ;
            rightSubTree.delete(key);
        }
    }
}
