package gui;

import dataStructure.*;

public class GuiMain {

	public static void main(String[] args) {

		GraphCreator r = new GraphCreator();
		graph g = r.RandomGra();
		GraphGui a = new GraphGui(g);
		a.setVisible(true);
	}
}
