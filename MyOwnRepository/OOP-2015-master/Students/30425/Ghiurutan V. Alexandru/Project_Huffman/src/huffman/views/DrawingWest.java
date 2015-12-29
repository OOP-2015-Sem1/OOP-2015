package huffman.views;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.JPanel;
import huffman.models.Huffman;
import huffman.models.Node;

public class DrawingWest extends JPanel {
	private static final long serialVersionUID = 7730330191534577604L;
	private static final int PANEL_WIDTH = 700;
	private static final int PANEL_HEIGHT = 800;
	private static final int NODE_SIZE = 30;
	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private Object parent;
	@SuppressWarnings("unused")
	private Huffman huffman;
	private int treeDepth;
	private Node root;

	public DrawingWest() {
		this.graph = new mxGraph();
		this.parent = graph.getDefaultParent();
		this.setOpaque(false);
	}

	public void clearTheWest() {
		this.removeAll();
	}

	public void action(Huffman huffman) {
		this.removeAll();
		setHuffman(huffman);
		this.root = huffman.getRootOfHuffmanTree();
		treeDepth = huffman.getMaximumHeight();
		this.update(root);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false);
		this.add(graphComponent);
	}

	public void setHuffman(Huffman huffman) {
		this.huffman = huffman;
	}

	private Object drawTree(Node root, int depth, int index) {
		if (root == null) {
			return null;
		}
		int xCoord = (int) ((PANEL_WIDTH * (index)) / (Math.pow(2, depth) + 1));
		int yCoord = (int) (depth * PANEL_HEIGHT) / treeDepth;
		Object rootVertex = graph.insertVertex(parent, null, root.getCh() + ":" + root.getFreq(), xCoord, yCoord,
				NODE_SIZE, NODE_SIZE, "shape=ellipse;perimeter=30;fillColor=green");
		Object leftChildVertex = drawTree(root.getLeft(), depth + 1, (index * 2) - 1);
		if (leftChildVertex != null) {
			graph.insertEdge(parent, null, "0", rootVertex, leftChildVertex,
					"startArrow=none;endArrow=none;strokeWidth=1;strokeColor=yellow");
		}
		Object rightChildVertex = drawTree(root.getRight(), depth + 1, index * 2);
		if (rightChildVertex != null) {
			graph.insertEdge(parent, null, "1", rootVertex, rightChildVertex,
					"startArrow=none;endArrow=none;strokeWidth=1;strokeColor=yellow");
		}
		return rootVertex;
	}

	private void update(Node root) {
		graph.getModel().beginUpdate();
		try {
			Object[] nodes = graph.getChildCells(parent, true, false);// returns
																		// all
																		// the
																		// vertices
			graph.removeCells(nodes, true);// removes the vertices and the edges
			drawTree(root, 0, 1);
		} finally {
			graph.getModel().endUpdate();
		}
	}
}
