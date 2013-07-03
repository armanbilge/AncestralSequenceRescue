package org.ithinktree.code.phylo.asr;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dr.evolution.io.Importer.ImportException;
import dr.evolution.io.NexusImporter;
import dr.evolution.tree.NodeRef;
import dr.evolution.tree.Tree;

public class AncestralSequenceRescue {

	/**
	 * @param args
	 * @throws ImportException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ImportException {
		
		FileReader fr = new FileReader(args[0]);
		NexusImporter importer = new NexusImporter(fr);
		Tree tree = importer.importNextTree();
		fr.close();
		Set<String> set = new HashSet<String>();
		for (int i = 1; i < args.length; i++) {
			set.add(args[i]);
		}
		NodeRef n = getCommonAncestorNode(tree, set);
		@SuppressWarnings("rawtypes")
		Iterator i = tree.getNodeAttributeNames(n);
		while (i.hasNext()) {
			String a = (String) i.next();
			Object o = tree.getNodeAttribute(n, a);
			String s;
			if (o.getClass().getName() == "[Ljava.lang.Object;") {
				s = Arrays.toString((Object[]) o);
			} else {
				s = o.toString();
			}
			System.out.println(a + ": " + s);
		}
	}
	
    /**
     * Gets the most recent common ancestor (MRCA) node of a set of leaf nodes.
     *
     * @param tree      the Tree
     * @param leafNodes a set of names
     * @return the NodeRef of the MRCA
     */
	private static NodeRef getCommonAncestorNode(Tree tree, Set<String> leafNodes) {

        int cardinality = leafNodes.size();

        if (cardinality == 0) {
            throw new IllegalArgumentException("No leaf nodes selected");
        }

        NodeRef[] mrca = {null};
        getCommonAncestorNode(tree, tree.getRoot(), leafNodes, cardinality, mrca);

        return mrca[0];
    }

    /*
     * Private recursive function used by getCommonAncestorNode.
     */
    private static int getCommonAncestorNode(Tree tree, NodeRef node,
                                             Set<String> leafNodes, int cardinality,
                                             NodeRef[] mrca) {

        if (tree.isExternal(node)) {

            if (leafNodes.contains(tree.getTaxonId(node.getNumber()))) {
                if (cardinality == 1) {
                    mrca[0] = node;
                }
                return 1;
            } else {
                return 0;
            }
        }

        int matches = 0;

        for (int i = 0; i < tree.getChildCount(node); i++) {

            NodeRef node1 = tree.getChild(node, i);

            matches += getCommonAncestorNode(tree, node1, leafNodes, cardinality, mrca);

            if (mrca[0] != null) {
                break;
            }
        }

        if (mrca[0] == null) {
            // If we haven't already found the MRCA, test this node
            if (matches == cardinality) {
                mrca[0] = node;
            }
        }

        return matches;
    }


}
