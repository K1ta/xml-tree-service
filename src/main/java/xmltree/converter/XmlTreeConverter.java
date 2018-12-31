package xmltree.converter;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.StringTokenizer;

/**
 * Singleton class for converting xml to tree
 * Uses Bill Pugh's singleton pattern
 */
public class XmlTreeConverter {

    private final String baseIndent = "  |";

    /**
     * Holder of class instance
     */
    private static class XmlTreeConverterHolder {
        private static final XmlTreeConverter INSTANCE = new XmlTreeConverter();
    }

    /**
     * Method for getting instance of class
     * @return instance of class
     */
    public static XmlTreeConverter getInstance() {
        return XmlTreeConverterHolder.INSTANCE;
    }

    /**
     * Method for converting xml to tree
     *
     * @param doc root node of document with xml to convert
     * @return tree view of xml as string
     */
    public String convert(Document doc) {
        return convert(doc, baseIndent);
    }

    /**
     * Recursive method for processing single node of document
     * @param node node for processing
     * @param indent indent for this node
     * @return string that consist of all child nodes
     */
    private String convert(Node node, String indent) {
        StringBuilder res = new StringBuilder();
        res.append(indent);
        switch (node.getNodeType()) {
            case Node.TEXT_NODE:
                String text = node.getNodeValue();
                StringTokenizer st = new StringTokenizer(text, "\n\r ");
                if (st.countTokens() == 0) return "";
                res.append("\"");
                while (st.hasMoreTokens()) {
                    res.append(st.nextToken());
                    res.append(" ");
                }
                res.deleteCharAt(res.length() - 1);
                res.append("\"\n");
                return res.toString();
            case Node.ATTRIBUTE_NODE:
                res.append(node.getNodeName());
                res.append(": ");
                res.append(node.getNodeValue());
                return res.toString();
            default:
                res.append(node.getNodeName());
                if (node.hasAttributes()) {
                    NamedNodeMap map = node.getAttributes();
                    for (int i = 0; i < map.getLength(); i++) {
                        res.append("\n");
                        res.append(convert(map.item(i), indent + baseIndent + "@"));
                    }
                }
                res.append("\n");
                NodeList list = node.getChildNodes();
                for (int i = 0; i < list.getLength(); i++) {
                    res.append(convert(list.item(i), indent + baseIndent));
                }
                return res.toString();
        }

    }
}
