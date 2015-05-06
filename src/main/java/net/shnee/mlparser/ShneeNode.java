package net.shnee.mlparser;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 */
public class ShneeNode extends AbstractNode {

    /** Internal data structure of ShneeNode. */
    protected Element node = null;

    /**
     * Constructor for ShneeNode, takes org.jsoup.nodes.Element that acts as the
     * underlying data structure.
     * @param node org.jsoup.nodes.Element that holds the data for this node.
     */
    public ShneeNode(Element node) { this.node = node; }

    /**
     * @see Node#css(java.lang.String)
     */
    @Override
    public List<Node> css(String selection) {
        List<Node> nodes = new ArrayList<>();
        Elements elements = this.node.select(selection);
        for(Element element : elements) {
            nodes.add(new ShneeNode(element));
        }
        return nodes;
    }

    /**
     * @see Node#xpath(java.lang.String)
     */
    @Override
    public List<Node> xpath(String path) {
        // TODO create algorithm that converts a xpath query to a css selection
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @see Node#parent() 
     */
    @Override
    public Node parent() {
        return new ShneeNode(this.node.parent());
    }

    /**
     * @see Node#children() 
     */
    @Override
    public List<Node> children() {
        List<Node> nodes = new ArrayList<>();

        for(Element element : this.node.children()) {
            nodes.add(new ShneeNode(element));
        }

        return nodes;
    }

    /**
     * @see Node#text() 
     */
    @Override
    public String text() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
