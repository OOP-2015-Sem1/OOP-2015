package services.teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import controller.DatabaseController;

public abstract class FieldsSetter implements ActionListener,DatabaseController{
	private JComboBox selectedBox;
	private JComboBox modifiedBox;
	public FieldsSetter(JComboBox selectedBox, JComboBox modifiedBox) {
		this.selectedBox = selectedBox;
		this.modifiedBox = modifiedBox;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String selectedField =  selectedBox.getSelectedItem().toString();
		String[] fields = getNewFields(selectedField);
		DefaultComboBoxModel model = new DefaultComboBoxModel(fields);
		
		modifiedBox.setModel( model );
	}
	public abstract String[] getNewFields(String selectedField);
}
