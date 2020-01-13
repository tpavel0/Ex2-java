package gui;


import algorithms.*;
import dataStructure.*;
import utils.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


/**
 * This Class is a Gui with options on presented graph
 * @author Pavel
 */

public class GraphGui extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;

	graph graph,origin;
	int Zero=0;

	public GraphGui(graph g){
		this.graph=new DGraph((DGraph)g);
		this.origin=new DGraph((DGraph)g);
		startGui(g);
	}
	private void startGui(graph g) {
		this.graph=g;
		this.setSize(1280, 720);
		this.setTitle("The Maze Of Waze");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		Menu file = new Menu("File options");
		menuBar.add(file);
		Menu alg  = new Menu("Algorithms options");
		menuBar.add(alg);
		MenuItem it1 = new MenuItem("Init Origin Graph");
		it1.addActionListener(this);
		file.add(it1);
		MenuItem it2 = new MenuItem("Init from File");
		it2.addActionListener(this);
		file.add(it2);
		MenuItem it3 = new MenuItem("Save File");
		it3.addActionListener(this);
		file.add(it3);
		MenuItem it4 = new MenuItem("Save format png");
		it4.addActionListener(this);
		file.add(it4);
		MenuItem it5 = new MenuItem("Is Conncected");
		it5.addActionListener(this);
		alg.add(it5);
		MenuItem it6 = new MenuItem("ShortestPath Distance");
		it6.addActionListener(this);
		alg.add(it6);
		MenuItem it7 = new MenuItem("ShowShortest Path");
		it7.addActionListener(this);
		alg.add(it7);
		MenuItem it8 = new MenuItem("SalesMan Problem");
		it8.addActionListener(this);
		alg.add(it8);
	}

	public void paint(Graphics d) {
		super.paint(d);
		if ((graph != null) && (graph.nodeSize()>1)) {
			/* get the nodes*/
			Collection<node_data> nodes = graph.getV();

			for (node_data nd : nodes) {
				/* draw the nodes */
				Point3D p = nd.getLocation();
				d.setColor(Color.BLACK);
				d.fillOval(p.ix(), p.iy(), 8, 8);
				/* draw the nodes key's */
				d.setColor(Color.BLUE);
				d.drawString(""+nd.getKey(), p.ix()-4, p.iy()-4);
				
				if (graph.edgeSize()==0)
					continue;
				
				/*search for edges */
				if ((graph.getE(nd.getKey())!=null)) {
					//get edges
					Collection<edge_data> edges = graph.getE(nd.getKey());
					for (edge_data e : edges) {
						/*draw the edges */
						d.setColor(Color.ORANGE);
						((Graphics2D) d).setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
						Point3D p2 = graph.getNode(e.getDest()).getLocation();
						d.drawLine(p.ix()+5, p.iy()+5, p2.ix()+5, p2.iy()+5);
						
						/* draw direction */
						d.setColor(Color.darkGray);
						d.fillOval((int)((p.ix()*0.8)+(0.2*p2.ix()))+2,
								(int)((p.iy()*0.8)+(0.2*p2.iy())), 10, 10);
						
						/*draw weight */
						String str = ""+String.valueOf(e.getWeight());
						d.drawString(str, 1+(int)((p.ix()*0.8)+(0.2*p2.ix())),
								(int)((p.iy()*0.7)+(0.3*p2.iy()))-2);
					}
				}	
			}
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent Command) {
		String s = Command.getActionCommand();		
		Graph_Algo tempG=new Graph_Algo();
		JFileChooser jfc;

		switch(s) {

		case "Init Origin Graph": 
			startGui(this.origin);
			break;

		case "Init from File":
			tempG=new Graph_Algo();

			jfc = new JFileChooser(FileSystemView.getFileSystemView());
			jfc.setDialogTitle("Init from file"); 

			int userOption = jfc.showOpenDialog(null);
			if(userOption == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chosed : " + jfc.getSelectedFile().getName());
				tempG.init(jfc.getSelectedFile().getAbsolutePath());
				graph NewG=tempG.copy();
				startGui(NewG);	
			}			
			break;

		case "Save File":
			tempG=new Graph_Algo((DGraph)this.graph);		

			jfc = new JFileChooser(FileSystemView.getFileSystemView());
			jfc.setDialogTitle("Save graph to file..");

			int userOption1 = jfc.showSaveDialog(null);
			if (userOption1 == JFileChooser.APPROVE_OPTION) {
				System.out.println("Saved as file - " + jfc.getSelectedFile().getAbsolutePath());
				tempG.save(jfc.getSelectedFile().getAbsolutePath());
			}
			break;

		case "Save format png":
			jfc = new JFileChooser(FileSystemView.getFileSystemView());
			jfc.setDialogTitle("Save as png..");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(" .png","png");
			jfc.setFileFilter(filter);

			int userOption2 = jfc.showSaveDialog(null);
			if (userOption2 == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedImage i = new BufferedImage(this.getWidth(), this.getHeight()+45, BufferedImage.TYPE_INT_RGB);
					Graphics g = i.getGraphics();
					paint(g);
					if (jfc.getSelectedFile().getName().endsWith(".png")) {
						ImageIO.write(i, "png", new File(jfc.getSelectedFile().getAbsolutePath()));
					}
					else {
						ImageIO.write(i, "png", new File(jfc.getSelectedFile().getAbsolutePath()+".png"));
					}
					System.out.println("Saved as png - " + jfc.getSelectedFile().getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;

		case "ShowShortest Path":
			try {
				System.out.println("Show Shortest Path: ");
				JFrame jf = new JFrame();

				int srcShowShortestPath=Zero,destShowShortestPath=Zero;
				
				try {
					String srcNode = JOptionPane.showInputDialog(jf,"Enter Src-Node:");
					String DestNode = JOptionPane.showInputDialog(jf,"Enter Dest-Node:");
					srcShowShortestPath = Integer.parseInt(srcNode);
					destShowShortestPath = Integer.parseInt(DestNode);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(jf, "You have entered illegal nodes key");
					System.out.println("You have entered illegal node key");
					break;
				}
				if (this.graph.getNode(srcShowShortestPath)==null) {
					JOptionPane.showMessageDialog(jf, "You have entered illegal node key");
					System.out.println("You have entered illegal node's-key");
					break;
				}
				if (this.graph.getNode(destShowShortestPath)==null) {
					JOptionPane.showMessageDialog(jf, "You have entered illegal node key");
					System.out.println("You have entered illegal node key");
					break;
				}

				Graph_Algo newGrShowShortestPath = new Graph_Algo();
				newGrShowShortestPath.init(graph);

				List<node_data> ShowShortestPathdist = newGrShowShortestPath.shortestPath(srcShowShortestPath,destShowShortestPath);
				graph newGr=new DGraph();
				if(ShowShortestPathdist.size()==1) {
					System.out.println("There is no path between nodes");
					JOptionPane.showMessageDialog(jf, "There is no path between nodes");
					break;
				}
				newGr.addNode(ShowShortestPathdist.get(Zero));
				newGr.getNode(ShowShortestPathdist.get(Zero).getKey()).setInfo("");
				newGr.getNode(ShowShortestPathdist.get(Zero).getKey()).setTag(Zero);
				System.out.print(ShowShortestPathdist.get(Zero).getKey());
				for (int i=1; i<ShowShortestPathdist.size(); i++) {
					System.out.print(" --> "+ShowShortestPathdist.get(i).getKey());
					newGr.addNode(ShowShortestPathdist.get(i));
					newGr.getNode(ShowShortestPathdist.get(i).getKey()).setInfo("");
					newGr.getNode(ShowShortestPathdist.get(i).getKey()).setTag(Zero);
					newGr.connect(ShowShortestPathdist.get(i-1).getKey(),
							ShowShortestPathdist.get(i).getKey(),
							this.graph.getEdge(ShowShortestPathdist.get(i-1).getKey(),
							ShowShortestPathdist.get(i).getKey()).getWeight());
				}
				this.startGui(newGr);
			}	
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println();
			break;

		case "ShortestPath Distance":
			try
			{
				JFrame ShortestPathDistanceInput = new JFrame();

				int srcShortestPathDistance=Zero,destShortestPathDistance=Zero;
				try {
					String SourceNodeShortestPathDistance = JOptionPane.showInputDialog(ShortestPathDistanceInput,"Enter Source-Node:");
					String DestNodeShortestPathDistance = JOptionPane.showInputDialog(ShortestPathDistanceInput,"Enter Destination-Node:");

					srcShortestPathDistance = Integer.parseInt(SourceNodeShortestPathDistance);
					destShortestPathDistance = Integer.parseInt(DestNodeShortestPathDistance);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(ShortestPathDistanceInput, "You have entered illegal node key");
					System.out.println("You have entered illegal node key");
					System.out.println();
					break;
				}

				Graph_Algo newg = new Graph_Algo();			
				newg.init(this.graph);

				double a = newg.shortestPathDist(srcShortestPathDistance, destShortestPathDistance);
				if (a==-1) {
					JOptionPane.showMessageDialog(ShortestPathDistanceInput, "You have entered illegal node key");
					System.out.println("You have entered illegal node key");
				}
				else if(a==Double.MAX_VALUE) {
					JOptionPane.showMessageDialog(ShortestPathDistanceInput, "There is no path / distance = infi ");
					System.out.println("There is no path / distance = infi");
				}
				else {
					JOptionPane.showMessageDialog(ShortestPathDistanceInput, "TheShortestPath Distance is: " + a);
					System.out.println("ShortestPath Distance is: " + a);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			break;

		case "SalesMan Problem": 
			JFrame SalesManProblemInput = new JFrame();
			System.out.println("The SalesMan Problem: ");
			String SourceNodeSalesManProblem = JOptionPane.showInputDialog(SalesManProblemInput,"How many nodes ?");
			int numSourceNodeSalesManProblem=1;
			try {
				numSourceNodeSalesManProblem = Integer.parseInt(SourceNodeSalesManProblem);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal number of nodes.");
				break;
			}
			if ((numSourceNodeSalesManProblem<1) || (numSourceNodeSalesManProblem>this.graph.nodeSize())) {
				JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal number of nodes.");
				break;
			}

			int b=0;
			if (numSourceNodeSalesManProblem==1) {
				SourceNodeSalesManProblem = JOptionPane.showInputDialog(SalesManProblemInput,"Enter node-key");
				try {
					b = Integer.parseInt(SourceNodeSalesManProblem);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal key.");
					break;
				}
				graph newGrap=new DGraph();

				newGrap.addNode(this.graph.getNode(b));
				this.startGui(newGrap);	
				break;
			}

			if (numSourceNodeSalesManProblem==2) {
				int c;
				graph newGraph=new DGraph();
				SourceNodeSalesManProblem = JOptionPane.showInputDialog(SalesManProblemInput,"Enter node-key 1 out of 2:");
				try {
					b = Integer.parseInt(SourceNodeSalesManProblem);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal key.");
					break;
				}
				newGraph.addNode(this.graph.getNode(b));

				String DestNodeSalesManProblem = JOptionPane.showInputDialog(SalesManProblemInput,"Enter node-key 2 out of 2:");
				try {
					c = Integer.parseInt(DestNodeSalesManProblem);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal key.");
					break;
				}
				newGraph.addNode(this.graph.getNode(c));

				if (graph.getEdge(b, c)!=null) {
					newGraph.connect(newGraph.getNode(b).getKey(), newGraph.getNode(c).getKey(),
							this.graph.getEdge(b, c).getWeight());
					this.startGui(newGraph);
					System.out.println(b+" --> "+ c);
					break;
				}
				else if(graph.getEdge(c, b)!=null){
					newGraph.connect(newGraph.getNode(c).getKey(), newGraph.getNode(b).getKey(),
							this.graph.getEdge(c, b).getWeight());
					this.startGui(newGraph);	
					System.out.println(c+" --> "+ b);
					break;
				}
				else {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Cant find path");
					System.out.println("Cant find path");
					break;	
				}
			}

			List<Integer> SalesManProblemNodes = new ArrayList<Integer>();
			int SalesManProblemKey=Zero;
			for (int i=0; i<numSourceNodeSalesManProblem; i++) {
				SourceNodeSalesManProblem = JOptionPane.showInputDialog(SalesManProblemInput,"Enter node key num "+(i+1)+" out of "+numSourceNodeSalesManProblem);
				try {
					SalesManProblemKey = Integer.parseInt(SourceNodeSalesManProblem);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal key.");
					break;
				}
				if(this.graph.getNode(SalesManProblemKey)==null) {
					JOptionPane.showMessageDialog(SalesManProblemInput, "Ilegal key.");
					break;
				}	
				SalesManProblemNodes.add(SalesManProblemKey);
			}
			if (SalesManProblemNodes.size()!=numSourceNodeSalesManProblem) { 
				JOptionPane.showMessageDialog(SalesManProblemInput, "You did not enter enough nodes.");
				break;
			}

			Graph_Algo newGSalesManProblem = new Graph_Algo();
			newGSalesManProblem.init(graph);

			List<node_data> TheSalesManProblem = newGSalesManProblem.TSP(SalesManProblemNodes);
			graph newGrap=new DGraph();

			String message="";
			if(TheSalesManProblem.isEmpty() || TheSalesManProblem==null) {
				JOptionPane.showMessageDialog(SalesManProblemInput, "cant find path.");
				break;
			}
			newGrap.addNode(TheSalesManProblem.get(Zero));
			message=""+message+TheSalesManProblem.get(Zero).getKey();
			System.out.print(TheSalesManProblem.get(Zero).getKey());

			
			for (int i=1; i<TheSalesManProblem.size(); i++) {
				message=""+message+"->"+TheSalesManProblem.get(i).getKey();
				System.out.print(" -> "+TheSalesManProblem.get(i).getKey());
				if (!((DGraph)newGrap).containNode(TheSalesManProblem.get(i).getKey())) {
					newGrap.addNode(TheSalesManProblem.get(i));	
				}
				if (!((DGraph)newGrap).containEdge(TheSalesManProblem.get(i-1).getKey(), TheSalesManProblem.get(i).getKey()))
				{
					newGrap.connect(TheSalesManProblem.get(i-1).getKey(), TheSalesManProblem.get(i).getKey(),
					this.origin.getEdge(TheSalesManProblem.get(i-1).getKey(), TheSalesManProblem.get(i).getKey()).getWeight());
				}

			}

			this.startGui(newGrap);
			JOptionPane.showMessageDialog(SalesManProblemInput, message);
			System.out.println();
			break;

		case "Is Conncected":
			JFrame isIt = new JFrame();			
			Graph_Algo isCGraph = new Graph_Algo();
			isCGraph.init(this.graph);
			if (isCGraph.isConnected()) { 
				System.out.println("The graph is Connected");
				JOptionPane.showMessageDialog(isIt, "The graph is Connected");
			}
			else { 
				System.out.println("The graph is not Connected");
				JOptionPane.showMessageDialog(isIt, "The graph is not Connected");				
			}
			break;
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {;}
	@Override
	public void mouseClicked(MouseEvent e) {;}
	@Override
	public void mousePressed(MouseEvent e) {;}
	@Override
	public void mouseReleased(MouseEvent e) {;}
	@Override
	public void mouseEntered(MouseEvent e) {;}
	
	
}