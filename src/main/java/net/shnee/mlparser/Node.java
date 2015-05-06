package net.shnee.mlparser;

import java.util.List;

/**
 * Interface for a node of a XML or HTML document.
 */
public interface Node {

    /**
     * Returns a list of Nodes that match the CSS selection.
     * @param selection CSS style selection statement.
     * @return A list of Node that match the CSS selection.
     */
    public List<Node> css(String selection);

    /**
     * Returns a list of Nodes that match the xpath path.
     * @param path xpath selection statement.
     * @return A list of Nodes that match the xpath path.
     */
    public List<Node> xpath(String path);

    /**
     * Get the parent Node. Returns null if this Node is the root Node.
     * @return The parent Node. Returns null if this Node is the root Node.
     */
    public Node parent();

    /**
     * Get the children Nodes. Returns null if there are no children.
     * @return A list of the children Nodes. Returns null if there are no
     *         children.
     */
    public List<Node> children();

    /**
     * Returns true if this Node is the root Node, false otherwise.
     * @return true if this Node is the root Node, false otherwise.
     */
    public boolean isRootNode();
    
    /**
     * Retrieves the text of the Node.
     * @return Returns the text of the Node.
     */
    public String text();

}
