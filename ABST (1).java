import tester.*;

// To represent a Binary Search Tree
abstract class ABST {
    IBookComparator order;

    ABST(IBookComparator order) {
        this.order = order;
    }

    // Inserts the Book into the BST.
    abstract public ABST insert(IBookComparator order, Book b);

    // Checks if ABST is a leaf.
    abstract public boolean isLeaf();

    // Checks is ABST is a node.
    abstract public boolean isNode();

    // Gets the First element of ABST.
    abstract public Book getFirst();

    // Gets the Rest of the elements of ABST.
    abstract public ABST getRest();

    // Checks if the given ABST has the same structure as this ABST.
    abstract public boolean sameTree(ABST bst);

    // Turns ABST into a Node.
    abstract public Node toNode();

    // Turns ABST into a Leaf.
    abstract public Leaf toLeaf();

    // Checks if the given ABST has the exact same data as this ABST.
    abstract public boolean sameData(ABST bst);

    // Checks if the given book is the same as this data.
    abstract public boolean sameBook(Book b);

    // Checks if this ABST is the same as the given list.
    abstract public boolean sameAsList(IBookComparator i, ILoBook lob);

    // Updates the given list by inserting the elements of this ABST.
    abstract public ILoBook buildList(ILoBook lob);

    // Gets the greatest element of this ABST.
    abstract public Book getLast();

    // Gets the rest of the tree with the greatest element removed.
    abstract public ABST getRestLast();

}

// To represent the empty node of a Binary Search Tree
class Leaf extends ABST {
    Leaf(IBookComparator order) {
        super(order);
    }

    // Creates a new BST by adding the given book.
    public ABST insert(IBookComparator i, Book b) {
        return new Node(i, b, this, this);
    }

    // There is no book in a Leaf
    public Book getFirst() {
        throw new RuntimeException("No first in an empty tree");
    }

    // There is no Last in a Leaf.
    public Book getLast() {
        throw new RuntimeException("No Last in an empty tree");
    }

    // There is no rest of ABST in a leaf to return.
    public ABST getRest() {
        throw new RuntimeException("No rest of an empty tree");
    }

    // There is no Rest of Last in a Leaf.
    public ABST getRestLast() {
        throw new RuntimeException("No rest of an empty tree");
    }

    // Checks to see if this is a Leaf.
    public boolean isLeaf() {
        return true;
    }

    // This is not a node
    public boolean isNode() {
        return false;
    }

    // Returns true if the given BST is a leaf like this one.
    public boolean sameTree(ABST bst) {
        return bst.isLeaf();
    }

    // If it's a node, it'll return an error.
    public Node toNode() {
        throw new IllegalArgumentException("Invalid");
    }

    // Converts ABST into a leaf.
    public Leaf toLeaf() {
        return this;
    }

    // Checks to see if the given BST is a leaf,
    // then compares it's data to this Leaf's data.
    public boolean sameData(ABST bst) {
        return true;
    }

    // Returns false because a Leaf is not the same as a Book.
    public boolean sameBook(Book b) {
        return false;
    }

    // Returns true only if the list is empty.
    public boolean sameAsList(IBookComparator i, ILoBook lob) {
        return lob.isEmpty();
    }

    // returns the given list because there is nothing to add to it.
    public ILoBook buildList(ILoBook lob) {
        return lob;
    }
}

// To represent a node of a Binary Search Tree
class Node extends ABST {
    Book data;
    ABST left;
    ABST right;

    Node(IBookComparator order, Book data, ABST left, ABST right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }

    // inserts b into the correct spot of a tree.
    public ABST insert(IBookComparator i, Book b) {
        if (i.compare(b, this.data) < 0) {
            return new Node(i, this.data, this.left.insert(i, b), this.right);
        } 
        else {
            return new Node(i, this.data, this.left, this.right.insert(i, b));
        }
    }

    // return the first book in this BST with regards to the Comparator.
    public Book getFirst() {
        if (this.left.isLeaf()) {
            return this.data;
        } 
        else {
            return this.left.getFirst();
        }
    }

    // return the last book in this BST with regards to the Comparator.
    public Book getLast() {
        if (this.right.isLeaf()) {
            return this.data;
        } 
        else {
            return this.right.getLast();
        }
    }

    // Returns the rest of this Node with the first book removed.
    public ABST getRest() {
        if (this.order.compare(this.data, this.getFirst()) == 0) {
            return this.right;
        } 
        else {
            return new Node(this.order, this.data, this.left.getRest(),
                    this.right);
        }
    }

    // Returns the rest of this Node with the greatest value removed.
    public ABST getRestLast() {
        if (this.order.compare(this.data, this.getLast()) == 0) {
            return this.left;
        } 
        else {
            return new Node(this.order, this.data, this.left,
                    this.right.getRestLast());
        }
    }

    // returns false because it is not a leaf.
    public boolean isLeaf() {
        return false;
    }

    // returns true because this is a node.
    public boolean isNode() {
        return true;
    }

    // does the given ABST have the same structure as this ABST?
    public boolean sameTree(ABST bst) {
        return (bst.isNode() && (this.left.sameTree(bst.toNode().left)) && 
                (this.right.sameTree(bst.toNode().right)));
    }

    // compares this node's data with the given node's data.
    public boolean sameData(ABST bst) {
        if (this.left.isLeaf() && this.right.isLeaf()) {
            return (bst.sameBook(this.data));
        }
        if (this.left.isLeaf()) {
            return this.right.sameData(bst);
        }
        if (this.right.isLeaf()) {
            return this.left.sameData(bst);
        } 
        else {
            return this.left.sameData(bst) || this.right.sameData(bst);
        }
    }

    // Helper to see if the given book is in this bst.
    public boolean sameBook(Book b) {
        if (this.data == b) {
            return true;
        } 
        else {
            return (this.left.sameBook(b) || (this.right.sameBook(b)));
        }
    }

    // Converts ABST into a node
    public Node toNode() {
        return this;
    }

    // If it is a leaf, it will return an error.
    public Leaf toLeaf() {
        throw new IllegalArgumentException("Invalid");
    }

    // Checks if this has the same data as the given list.
    public boolean sameAsList(IBookComparator i, ILoBook lob) {
        return (!(lob.isEmpty()))
                && (i.compare(lob.getFirst(), this.getFirst()) == 0)
                && (this.getRest().sameAsList(i, lob.getRest()));
    }

    // Insert the ABST's books into the given list of books.
    public ILoBook buildList(ILoBook lob) {
        if (lob.isEmpty()) {
            return new ConsLoBook(this.getLast(), this.getRestLast().buildList(
                    lob));
        } 
        else {
            return new ConsLoBook(this.getFirst(), this.getRest()
                    .buildList(lob));
        }
    }

}

// To represent an interface of a comparator for different book fields.
interface IBookComparator {
    public int compare(Book b1, Book b2);
}

// To represent an interface of a List of Books.
interface ILoBook {

    // Returns the first of this ILoB.
    Book getFirst();

    // Returns the Rest of this ILoB.
    ILoBook getRest();

    // Checks to see if this is empty.
    boolean isEmpty();

    // inserts the books of this into the given ABST.
    ABST buildTree(ABST bst);

}

// To represent an empty List of Books.
class MtLoBook implements ILoBook {

    MtLoBook() { 
        // This has no fields because it is empty.
    }

    // Throws an error because there is no first.
    public Book getFirst() {
        throw new RuntimeException("No first in an empty list");
    }

    // Throws an error because there is no rest.
    public ILoBook getRest() {
        throw new RuntimeException("No rest in an empty list");
    }

    // Returns true because this class is an empty list.
    public boolean isEmpty() {
        return true;
    }

    // Returns the given bst because there's nothing
    // in the empty list to add to it.
    public ABST buildTree(ABST bst) {
        return bst;
    }

}

// To represent a non-empty List of Books.
class ConsLoBook implements ILoBook {
    Book first;
    ILoBook rest;

    ConsLoBook(Book first, ILoBook rest) {
        this.first = first;
        this.rest = rest;
    }

    // returns the first of this list.
    public Book getFirst() {
        return this.first;
    }

    // returns the rest of this list.
    public ILoBook getRest() {
        return this.rest;
    }

    // This is not empty.
    public boolean isEmpty() {
        return false;
    }

    // Returns the given Tree, but inserts the elements in the list into it.
    public ABST buildTree(ABST bst) {
        bst.insert(bst.order, this.first);
        return this.rest.buildTree(bst.insert(bst.order, this.getFirst()));
    }

}

// to represent a book as part of a node
class Book {
    String title;
    String author;
    int price;

    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

}

// Class that compares books by their titles.
class BooksByTitle implements IBookComparator {
    public int compare(Book b1, Book b2) {
        return (b1.title.compareTo(b2.title));
    }
}

// Class that compares books by their authors.
class BooksByAuthor implements IBookComparator {
    public int compare(Book b1, Book b2) {
        return (b1.author.compareTo(b2.author));
    }
}

// Class that compares books by their prices.
class BooksByPrice implements IBookComparator {
    public int compare(Book b1, Book b2) {
        return (b1.price - b2.price);
    }
}

// Examples and Tests for BSTs.
class ExamplesABST {

    // Example of each comparator
    IBookComparator byTitle = new BooksByTitle();
    IBookComparator byAuthor = new BooksByAuthor();
    IBookComparator byPrice = new BooksByPrice();

    // Leaf examples of ABST
    ABST leafTitle = new Leaf(byTitle);
    ABST leafAuthor = new Leaf(byAuthor);
    ABST leafPrice = new Leaf(byPrice);

    // Examples of Books
    Book b1 = new Book("Harry Potter", "JKR", 10);
    Book b2 = new Book("LotR", "JRRT", 20);
    Book b3 = new Book("Lone Survivor", "Luttrell", 30);
    Book b4 = new Book("HtDP", "MF", 40);
    Book b5 = new Book("Baseball", "JL", 50);
    Book b6 = new Book("Winner", "Z", 60);

    // Examples of Lists of Books
    ILoBook empty = new MtLoBook();
    ILoBook lob1 = new ConsLoBook(this.b1, this.empty);
    ILoBook lob2 = new ConsLoBook(this.b1, new ConsLoBook(this.b2, 
            this.empty));
    ILoBook lob3 = new ConsLoBook(this.b4, this.empty);
    ILoBook lob4 = new ConsLoBook(this.b1, new ConsLoBook(this.b2,
            new ConsLoBook(this.b3, new ConsLoBook(this.b4, this.empty))));
    ILoBook lob6 = new ConsLoBook(this.b5, new ConsLoBook(this.b1,
            new ConsLoBook(this.b2, this.empty)));

    // results for insert testing.
    ABST result1 = new Node(this.byPrice, this.b1, this.leafPrice, new Node(
            this.byPrice, this.b2, this.leafPrice, this.leafPrice));

    ABST result2 = new Node(this.byPrice, this.b2, new Node(this.byPrice,
            this.b1, this.leafPrice, this.leafPrice), new Node(this.byPrice,
                    this.b6, this.leafPrice, this.leafPrice));

    ABST result3 = new Node(this.byPrice, this.b2, this.leafPrice,
            this.leafPrice);

    // Node examples of ABST
    Node node1 = new Node(this.byPrice, this.b1, this.leafPrice, 
            this.leafPrice);

    ABST bst1 = new Node(this.byPrice, this.b1, this.leafPrice, 
            this.leafPrice);

    ABST bst2 = new Node(this.byPrice, this.b2, this.bst1, this.leafPrice);

    ABST bst3 = new Node(this.byPrice, this.b4, this.leafPrice, 
            this.leafPrice);

    ABST bst4 = new Node(this.byPrice, this.b3, this.bst2, this.bst3);

    ABST bst5 = new Node(this.byTitle, this.b1, this.leafTitle, 
            this.leafTitle);

    ABST bst6 = new Node(this.byTitle, this.b1, new Node(this.byTitle, this.b5,
            this.leafTitle, this.leafTitle), new Node(this.byTitle, this.b2,
                    this.leafTitle, this.leafTitle));

    ABST result5 = new Node(this.byTitle, this.b1, new Node(this.byTitle,
            this.b5, this.leafTitle, this.leafTitle), new Node(this.byTitle,
                    this.b2, new Node(this.byTitle, this.b4, this.leafTitle,
                            this.leafTitle), this.leafTitle));

    ABST bst7 = new Node(this.byPrice, this.b2, this.leafPrice, 
            this.leafPrice);

    ABST bst8 = new Node(this.byTitle, this.b1, new Leaf(this.byTitle),
            new Node(this.byTitle, this.b2, this.leafTitle, this.leafTitle));

    ABST bst9 = new Node(this.byPrice, this.b3, new Node(this.byPrice, this.b2,
            this.leafPrice, this.leafPrice), new Node(this.byPrice, this.b4,
                    this.leafPrice, this.leafPrice));

    ABST bst0 = new Node(this.byPrice, this.b5, this.bst1, this.leafPrice);

    ABST bst00 = new Node(this.byPrice, this.b1, this.leafPrice, new Node(
            this.byPrice, this.b5, this.leafPrice, this.leafPrice));

    ABST result4 = new Node(this.bst7.order, this.b2, this.bst1, 
            this.leafPrice);

    ILoBook result6 = new ConsLoBook(this.b1, new ConsLoBook(this.b4,
            this.empty));

    ILoBook result7 = new ConsLoBook(this.b2, new ConsLoBook(this.b1,
            new ConsLoBook(this.b5, this.empty)));

    // Tests the insert method on both a node and a leaf.
    void testinsert(Tester t) {
        t.checkExpect(this.bst1.insert(this.byPrice, this.b2),
                this.result1);
        t.checkExpect(this.bst2.insert(this.byPrice, this.b6),
                this.result2);
        t.checkExpect(this.leafPrice.insert(this.byPrice, this.b2),
                this.result3);
    }

    // Tests the getFirst method on BST examples.
    boolean testgetFirst(Tester t) {
        return t.checkExpect(this.bst1.getFirst(), this.b1)
                && t.checkExpect(this.bst2.getFirst(), this.b1)
                && t.checkExpect(this.bst3.getFirst(), this.b4)
                && t.checkExpect(this.bst4.getFirst(), this.b1)
                && t.checkExpect(this.bst6.getFirst(), this.b5);
    }

    // Tests the getRest method on BST examples.
    boolean testgetRest(Tester t) {
        return t.checkExpect(this.bst1.getRest(), this.leafPrice)
                && t.checkExpect(this.bst2.getRest(), this.bst7)
                && t.checkExpect(this.bst6.getRest(), this.bst8)
                && t.checkExpect(this.bst4.getRest(), this.bst9);
    }

    // Tests the sameTree method to see if two Example
    // BSTs have the same structure.
    boolean testsameTree(Tester t) {
        return t.checkExpect(this.bst1.sameTree(this.bst1), true)
                && t.checkExpect(this.bst1.sameTree(this.bst2), false)
                && t.checkExpect(this.bst4.sameTree(this.bst4), true)
                && t.checkExpect(this.bst6.sameTree(this.bst3), false)
                && t.checkExpect(this.bst6.sameTree(this.bst9), true)
                && t.checkExpect(this.leafTitle.sameTree(this.leafAuthor), 
                        true)
                        && t.checkExpect(this.bst4.sameTree(this.leafAuthor), false)
                        && t.checkExpect(this.bst1.sameTree(this.bst5), true)
                        && t.checkExpect(this.bst00.sameTree(this.bst0), false);
    }

    // Tests the helper method for sameData to see if the given book is
    // located in the BST.
    boolean testsameBook(Tester t) {
        return t.checkExpect(this.bst1.sameBook(this.b1), true)
                && t.checkExpect(this.bst2.sameBook(this.b6), false)
                && t.checkExpect(this.bst6.sameBook(this.b5), true)
                && t.checkExpect(this.bst6.sameBook(this.b6), false);
    }

    // Tests the sameData method to see if two example BSTs are exactly alike.
    boolean testsameData(Tester t) {
        return t.checkExpect(this.bst1.sameData(this.bst1), true)
                && t.checkExpect(this.bst1.sameData(this.bst5), true)
                && t.checkExpect(this.bst6.sameData(this.bst6), true)
                && t.checkExpect(this.bst00.sameData(this.bst0), true)
                && t.checkExpect(this.bst6.sameData(this.bst1), false)
                && t.checkExpect(this.bst00.sameData(this.bst1), false)
                && t.checkExpect(this.bst2.sameData(this.bst3), false);
    }

    // Tests for the getFirst method in lob
    boolean testlobGetFirst(Tester t) {
        return t.checkExpect(this.lob1.getFirst(), this.b1)
                && t.checkExpect(this.lob2.getFirst(), this.b1);
    }

    // Tests for the getRest method in lob
    boolean testlobGetRest(Tester t) {
        return t.checkExpect(this.lob1.getRest(), this.empty)
                && t.checkExpect(this.lob2.getRest(), new ConsLoBook(this.b2,
                        this.empty));
    }

    // Tests for sameAsList to see if a bst has the same exact books as a list.
    void testsameAsList(Tester t) {
        t.checkExpect(this.bst1.sameAsList(this.byPrice, this.lob1),
                true);
        t.checkExpect(this.bst6.sameAsList(this.byTitle, this.lob6),
                true);
        t.checkExpect(this.bst3.sameAsList(this.byPrice, this.lob3),
                true);
        t.checkExpect(this.bst0.sameAsList(this.byPrice, this.lob1),
                false);
        t.checkExpect(this.leafPrice.sameAsList(this.byPrice, lob1),
                false);
    }

    // Tests for builTree to see if the list's books are added to the Tree.
    boolean testbuildTree(Tester t) {
        return t.checkExpect(this.lob1.buildTree(this.bst7), result4)
                && t.checkExpect(this.lob3.buildTree(this.bst6), result5);
    }
    // Tests for buildList to see if the tree's books are added to the List.
    boolean testbuildList(Tester t) {
        return t.checkExpect(this.bst1.buildList(this.lob3), result6)
                && t.checkExpect(this.bst6.buildList(this.empty), result7);
    }
}