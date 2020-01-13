package myTests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gui.GraphCreator;

class Graph_AlgoTest {

	private static Graph_Algo Creator() {
		GraphCreator gc = new GraphCreator();
		graph test = gc.RandomGra();
		Graph_Algo Graanswer = new Graph_Algo(test);
		return Graanswer;
	}

	@Test
	void testInitSave() {
	Graph_Algo ga = Graph_AlgoTest.Creator();
	ga.save("Test");
	Graph_Algo n_ga = new Graph_Algo();
	n_ga.init("Test");
	if(!ga.equals(n_ga)) 
		fail(); 
	}

	@Test
	void testIsConnected() {
		Graph_Algo ga = Graph_AlgoTest.Creator();
		if (!(ga.isConnected())) 
			fail(); 
	}

	@Test
	void testShortestPathDist() {
		GraphCreator x = new GraphCreator();
		node_data a1 = x.RandomNode();
		node_data a2 = x.RandomNode();
		node_data a3 = x.RandomNode();
		node_data a4 = x.RandomNode();
		
		DGraph ng = new DGraph();
		ng.addNode(a1);
		ng.addNode(a2);
		ng.addNode(a3);
		ng.addNode(a4);
		
		ng.connect(a1.getKey(), a2.getKey(), 3);
		ng.connect(a2.getKey(), a3.getKey(), 5);
		ng.connect(a4.getKey(), a1.getKey(), 8);
		ng.connect(a3.getKey(), a4.getKey(), 1);
		Graph_Algo n=new Graph_Algo(ng);
		assertEquals(n.shortestPathDist(a1.getKey(), a4.getKey()),9);	
	}


}
