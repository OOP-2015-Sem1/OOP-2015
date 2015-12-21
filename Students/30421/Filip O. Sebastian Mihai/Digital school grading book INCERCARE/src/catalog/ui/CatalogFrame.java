package catalog.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CatalogFrame {
	static JFrame CatalogTheRealFrame = new JFrame("Catalog");
	private ClassroomsFromSchoolPanel ClassroomsPanel = new ClassroomsFromSchoolPanel();
	private StudentsFromEachGradePanel MarksPanel = new StudentsFromEachGradePanel();
	public CatalogFrame() {
		CatalogTheRealFrame.setVisible(true);
		CatalogTheRealFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CatalogTheRealFrame.setLayout(new GridLayout(1, 3));

		// adjust window to screen resolution automatic
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int widthInt = (int) width;
		int hightInt = (int) height;
		CatalogTheRealFrame.setSize(widthInt, hightInt);
		// terminate adjustng window size

		// adding classroom panel
		ClassroomsPanel.setVisible(true);
		CatalogTheRealFrame.add(ClassroomsPanel);
		// terminate
		CatalogTheRealFrame.add(ClassroomsPanel.StudentsFromClassrooms);
		
		// aici o sa vina sa adaug inca un panel cu notele elevilorl fiecare in parte, si nu prea am idei multe la cum sa fac asta 
		// as vrea sa am ceva buton pentru adauga note la fiecare materie si notele sa apara pe ceva label
		CatalogTheRealFrame.add(MarksPanel.fau);
	}
}
