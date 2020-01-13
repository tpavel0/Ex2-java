package gui;


import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import utils.Point3D;

public class GraphCreator {

	public GraphCreator (){;}
	
	public graph RandomGra() {
		graph g = new DGraph();
		node n1 = RandomNode();
		node n2 = RandomNode();
		node n3 = RandomNode();
		node n4 = RandomNode();
		node n5 = RandomNode();
		node n6 = RandomNode();
		node n7 = RandomNode();
		node n8 = RandomNode();
		node n9 = RandomNode();
		node n10= RandomNode();
		
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);
		g.addNode(n5);
		g.addNode(n6);
		g.addNode(n7);
		g.addNode(n8);
		g.addNode(n9);
		g.addNode(n10);
		
		g.connect(n1.getKey(), n7.getKey(), 1.2);
		g.connect(n1.getKey(), n8.getKey(), 1.7);
		
		g.connect(n2.getKey(), n10.getKey(), 5.4);
		g.connect(n2.getKey(), n4.getKey(), 3.1);
		
		g.connect(n3.getKey(), n2.getKey(), 32);
		g.connect(n3.getKey(), n6.getKey(), 2.5);
		
		g.connect(n4.getKey(), n3.getKey(), 6.22);
		g.connect(n4.getKey(), n1.getKey(), 3.23);
		
		g.connect(n5.getKey(), n3.getKey(), 21.1);
		g.connect(n5.getKey(), n9.getKey(), 1.3);
		
		g.connect(n6.getKey(), n8.getKey(), 14.28);
		g.connect(n6.getKey(), n7.getKey(), 8.3);
		
		g.connect(n7.getKey(), n5.getKey(), 1);
		g.connect(n7.getKey(), n2.getKey(), 2);
		
		g.connect(n8.getKey(), n1.getKey(), 1.22);
		g.connect(n8.getKey(), n10.getKey(), 5.98);
		
		g.connect(n9.getKey(), n4.getKey(), 7);
		g.connect(n9.getKey(), n10.getKey(), 8.5);
		
		g.connect(n10.getKey(), n8.getKey(), 2.2);
		g.connect(n10.getKey(), n7.getKey(), 3.32);

		return g;
	}

	public node RandomNode() {
		Point3D p = new Point3D((int)(Math.random()*1240)+40, (int)(Math.random()*600)+50);
		return (new node(p));
	}


}