package net.shnee.mlparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Node. All common code goes here.
 */
public abstract class AbstractNode implements Node {

    /** The parentNode of this Node. */
    protected Node parentNode = null;

    /** The children of this Node. */
    protected List<Node> childrenNodes = new ArrayList<>();

    /**
     * @see Node#css(java.lang.String)
     */
    @Override
    public abstract List<Node> css(String selection);

    /**
     * @see Node#xpath(java.lang.String)
     */
    @Override
    public abstract List<Node> xpath(String path);

    /**
     * @see Node#parent()
     */
    @Override
    public abstract Node parent();

    /**
     * @see Node#children()
     */
    @Override
    public abstract List<Node> children();

    /**
     * @see Node#isRootNode()
     */
    @Override
    public boolean isRootNode() { return (this.parentNode == null); }

}
