package catalog.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class CatalogFrame {
	public JFrame CatalogTheRealFrame = new JFrame("Catalog");
	private ClassroomsFromSchoolPanel ClassroomsPanel = new ClassroomsFromSchoolPanel();
	public StudentsFromEachGradePanel SFEGP = new StudentsFromEachGradePanel();
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
		// terminate adjusting window size
		// adding classroom panel
		ClassroomsPanel.setVisible(true);
		CatalogTheRealFrame.add(ClassroomsPanel);
		CatalogTheRealFrame.add(ClassroomsPanel.StudentsFromClassrooms);
		//adding marks panel
		CatalogTheRealFrame.add(SFEGP.marksPanel);
	}
}
