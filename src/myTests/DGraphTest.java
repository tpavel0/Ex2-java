package myTests;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import utils.Point3D;

class DGraphTest {
	
	@Test
	void testAddNode() {
		graph gra = new DGraph();
		gra.addNode(new node(new Point3D(5, 5, 5)));
		gra.addNode(new node(new Point3D(1, 1, 1)));
		gra.addNode(new node(new Point3D(2, 2, 12)));
		if (gra.nodeSize()!=3)
			fail(); 
	}
	@Test
	void testRemoveNode() {
		graph gra = new DGraph();
		node n1 = new node(new Point3D(1, 5, 0));
		node n2 = new node(new Point3D(4, 4, 0));
		node n3 = new node(new Point3D(2, 2, 0));
		gra.addNode(n1);
		gra.addNode(n2);
		gra.addNode(n3);
		gra.connect(n1.getKey(), n2.getKey(), 2);
		gra.connect(n2.getKey(), n3.getKey(), 3);
		
		gra.removeNode(n2.getKey());
		if (gra.edgeSize()!=0)
			fail(); 
		try {
			if(gra.getEdge(n2.getKey(), n3.getKey())!=null)
				fail();
		}catch (Exception e) {;}
		try {
			if(gra.getEdge(n1.getKey(), n2.getKey())!=null)
				fail();
		}catch (Exception e) {;}
	}
	
	@Test
	void testRemoveEdge() {
		graph g = new DGraph();
		node n1 = new node(new Point3D(1, 5, 0));
		node n2 = new node(new Point3D(4, 4, 0));
		node n3 = new node(new Point3D(2, 2, 0));
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.connect(n1.getKey(), n2.getKey(), 2);
		g.connect(n2.getKey(), n3.getKey(), 3);
		g.removeEdge(n2.getKey(), n3.getKey());
		if (g.edgeSize()!=1)
		  fail(); 
		if (g.getEdge(n2.getKey(), n3.getKey()) != null)
		  fail();
		if (g.getEdge(n1.getKey(), n2.getKey()) == null)
		  fail();
	}
	@Test
	void testConnect() {
		graph gra = new DGraph();
		node nd1 = new node(new Point3D(1, 1, 1));
		node nd2 = new node(new Point3D(2, 2, 2));
		node nd3 = new node(new Point3D(3, 3, 3));
		gra.addNode(nd1);
		gra.addNode(nd2);
		gra.addNode(nd3);
		gra.connect(nd1.getKey(), nd2.getKey(), 2);
		gra.connect(nd2.getKey(), nd3.getKey(), 3);
		if (gra.edgeSize()!=2)
			fail(); 
		if (gra.getEdge(nd2.getKey(), nd3.getKey()).getWeight()!=3) 
			fail();	
	}
}