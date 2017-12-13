package main;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 12 Dec.
 */
public class Node {

    private final int id;
    private int currentWeight;

    private Node prev;

    /**
     * Default construct to build a node.
     *
     * @param id Node id.
     */
    public Node(final int id) {
        this.id = id;
    }

    /**
     * @return The current weight.
     */
    public int getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Sets a current weight.
     *
     * @param currentWeight Weight to set.
     */
    public void setCurrentWeight(final int currentWeight) {
        this.currentWeight = currentWeight;
    }

    /**
     * @return The node id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Previous node.
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Sets an previous node.
     *
     * @param prev Previous node to set.
     */
    public void setPrev(final Node prev) {
        this.prev = prev;
    }

}
