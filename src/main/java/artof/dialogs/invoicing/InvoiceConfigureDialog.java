package artof.dialogs.invoicing;

import artof.database.ArtofDB;
import artof.database.ClientDets;
import artof.database.DesignDets;
import artof.utils.sortablejtable.SortButtonRenderer;
import artof.utils.sortablejtable.SortableTableModel;
import artof.utils.UserSettings;
import artof.utils.Utils;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: cdavel
 * Date: Mar 10, 2005
 * Time: 1:21:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceConfigureDialog extends JDialog {
	private JPanel mainPanel;
	private JButton cancelButton;
	private JButton printButton;
	private JLabel dateValue;
	private JLabel numberValue;
	private JLabel dateLabel;
	private JLabel numberLabel;
	private JComboBox clientComboBox;
	private JRadioButton multipleDesignRadioButton;
	private JRadioButton currentDesignradioButton;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table1;
	ArtofDB artofDB;
	private ArrayList clientList;
	private ArrayList designList = new ArrayList();
	private ArrayList allDesignList = null;
	private String[] headerStr = {"Include in Invoice","Surname","Name","Design Name","Design Code","Date","Status"};
	HashMap selectedHashMap;
	static String SHOW_ALL_CLIENTS = "Show all clients...";
	DesignDets currentDesignDets;
	ClientDets clientDets = null;  // the clientDets of the currentDesign

	public InvoiceConfigureDialog(DesignDets currentDesignDets ) {
		numberValue.setText(Utils.getCurrentDate() + "-" + (UserSettings.INVOICE_NUMBER + 1));
		dateValue.setText(Utils.getDatumStr(Utils.getCurrentDate()));
		artofDB = ArtofDB.getCurrentDB();
    this.currentDesignDets = currentDesignDets;
		designList = artofDB.getAllDesigns();

		this.getContentPane().add(mainPanel);
		clientComboBox.setModel(getClientComboModel());

		setupRadioButtonStuff();
		doStartupRadioButtonSelection();

		table1.setModel(makeTableModel(getDesignList()));
		setupColumns();
		setActionListeners();
		setSortableStuff();

		setTitle("Invoice Configuration");
		setSize(700,530);
		setLocation(150,150);
		setVisible(true);
	}

	private void doStartupRadioButtonSelection() {
		selectedHashMap = new HashMap();
		((DefaultTableModel)table1.getModel()).fireTableDataChanged();
		if (clientDets == null)
			clientDets = artofDB.getClient(currentDesignDets.getClientID());

		clientComboBox.getModel().setSelectedItem(clientDets.getSurname() + ", " + clientDets.getName());
		selectedHashMap.put(new Integer(currentDesignDets.getDesignID()),Boolean.TRUE);
	}

	/**
	 * Add rb's to group and set default to currentDesign
	 */
	private void setupRadioButtonStuff() {
		buttonGroup.add(multipleDesignRadioButton);
		buttonGroup.add(currentDesignradioButton);
		currentDesignradioButton.setSelected(true);
	}

	/**
	 * Components include :
	 * 			ClientCombo
	 * 			OKButton
	 * 			CancelButton
	 */
	private void setActionListeners() {
		currentDesignradioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doStartupRadioButtonSelection();
			}
		});

		clientComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table1.setModel(makeTableModel(getDesignList()));
				setupColumns();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				get_this().hide();
			}
		});

		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList selectedList  = new ArrayList();
				TreeSet uniquSet = new TreeSet();
				for (Iterator iterator = selectedHashMap.keySet().iterator(); iterator.hasNext();) {
					Object o =  iterator.next();
					if (selectedHashMap.get(o).equals(Boolean.TRUE)) {
						DesignDets design = getDesignDetsFromAllDesignList(((Integer)o).intValue());
            uniquSet.add(new Integer(design.getClientID()));
						selectedList.add(design);
					}
				}

				if (uniquSet.size() > 1) {
					JOptionPane.showMessageDialog(get_this(), "An invoice can only be created for one client. \n" +
																										"Select designs belonging to the same client.",
																				"Selection Error",JOptionPane.ERROR_MESSAGE);
					return;
				}


				UserSettings.INVOICE_NUMBER = UserSettings.INVOICE_NUMBER + 1;
				currentDesignDets.setDesignList(selectedList);
				currentDesignDets.printDesignQuotation();
				get_this().hide();
			}
		});
	}

	JDialog get_this() {
		return this;
	}

	private void setSortableStuff() {
		table1.setShowVerticalLines(true);
		table1.setShowHorizontalLines(true);
		SortButtonRenderer renderer = new SortButtonRenderer();

		int n = headerStr.length;
		for (int i = 0; i < n; i++) {
			table1.getColumnModel().getColumn(i).setHeaderRenderer(renderer);
		}
		JTableHeader header = table1.getTableHeader();
		header.addMouseListener(new HeaderListener(header, renderer));
	}

	/**
	 * built a combomodel of all the clients in the db
	 * @return
	 */
	ComboBoxModel getClientComboModel() {
		clientList = artofDB.getClients(" order by SurName");
		DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
		comboBoxModel.addElement(SHOW_ALL_CLIENTS);
		for (Iterator iterator = clientList.iterator(); iterator.hasNext();) {
			ClientDets  clientDets = (ClientDets) iterator.next();
			comboBoxModel.addElement(clientDets.getSurname() + ", " + clientDets.getName());
		}

		clientComboBox.getModel().setSelectedItem(SHOW_ALL_CLIENTS);

		return comboBoxModel;
	}

	/**
	 *  gets the designs for a selection in the clientCombo
	 * @return
	 */
	protected ArrayList getDesignList() {
		try {
			if (clientComboBox.getModel().getSelectedItem().equals(SHOW_ALL_CLIENTS))
				return getDesigns(null,null);
			else {
				ClientDets client = (ClientDets) clientList.get(clientComboBox.getSelectedIndex() - 1);
				return getDesigns(client.getSurname(),client.getName());
			}
		} catch (Exception e) {
			return new ArrayList();
		}
	}

	/**
	 * get all the designs for a spesific surname and name.  As die name en surname null is return dit alle designs in die db
	 * @param surname
	 * @param name
	 * @return
	 */
	ArrayList getDesigns(String surname, String name) {
		ArrayList dList = new ArrayList();

		if (allDesignList == null)
			allDesignList = artofDB.getAllDesigns();

		if (name == null && surname == null)
			return allDesignList;

		for (Iterator iterator = allDesignList.iterator(); iterator.hasNext();) {
			DesignDets designDets = (DesignDets) iterator.next();
			if (((ClientDets)getClientDetsFromList(designDets.getClientID())).getSurname().equals(surname) &&
						((ClientDets)getClientDetsFromList(designDets.getClientID())).getName().equals(name)) {
				dList.add(designDets);
			}
		}

		return dList;
	}

	/**
	 * get clientDets for designID from list
	 * @param clientID
	 * @return
	 */
	ClientDets getClientDetsFromList(int clientID) {
		for (Iterator iterator = clientList.iterator(); iterator.hasNext();) {
			ClientDets clientDets =  (ClientDets) iterator.next();
			if (clientDets.getClientID() == clientID) {
				return clientDets;
			}
		}

		return null;
	}

	/**
	 * get designDets for designID from list
	 * @param designID
	 * @return
	 */
	DesignDets getDesignDetsFromAllDesignList(int designID) {
		for (Iterator iterator = allDesignList.iterator(); iterator.hasNext();) {
			DesignDets designDets =  (DesignDets) iterator.next();
			if (designDets.getDesignID() == designID) {
				return designDets;
			}
		}

		return null;
	}

	private TableModel makeTableModel(final ArrayList modelDesignList) {

		return new SortableTableModel() {

			public int getRowCount() {
				try {
					return modelDesignList.size();
				} catch (NullPointerException e) {
					return 0;
				}
			}

			public int getColumnCount() {
				return 7;
			}

			public Class getColumnClass(int col) {
				switch (col) {
					case 0:
						return Boolean.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return String.class;
					case 4:
						return String.class;
					case 5:
						return String.class;
					default:
						return String.class;
				}
			}

			public void setValueAt(Object aValue, int row, int column) {
				if (row >= getRowCount())
					return ;

				int[] indexes = getIndexes();
				int rowIndex = row;
				if (indexes != null) {
					rowIndex = indexes[row];
				}

				DesignDets designDets = (DesignDets) modelDesignList.get(rowIndex);

				if (column == 0) {
					selectedHashMap.put(new Integer(designDets.getDesignID()),(Boolean)table1.getCellEditor(rowIndex,column).getCellEditorValue());
					int i = 0;
					for (Iterator iterator = selectedHashMap.values().iterator(); iterator.hasNext();) {
						Boolean bb = (Boolean) iterator.next();
            if (bb.equals(Boolean.TRUE))
							i++;
					}

					if (i > 1 || designDets.getDesignID() != getCurrentDesignDets().getDesignID())
						multipleDesignRadioButton.setSelected(true);
					else
						currentDesignradioButton.setSelected(true);
				}


			}

			public Object getValueAt(int row, int column) {
				int[] indexes = getIndexes();
				int rowIndex = row;
				if (indexes != null) {
					rowIndex = indexes[row];
				}
				DesignDets designDets = (DesignDets) modelDesignList.get(rowIndex);
				ClientDets clientDets = artofDB.getClient(designDets.getClientID());

				switch (column) {
					case 0: // Selected CheckBox
						Boolean isSel = (Boolean) selectedHashMap.get(new Integer(designDets.getDesignID()));
						if (isSel == null)
							return new Boolean(false);
						else
							return (isSel);
					case 1: // Surname
						return clientDets.getSurname();
					case 2: // client name
						return clientDets.getName();
					case 3: // design name
						return designDets.getTitle();
					case 4: // design code
						return String.valueOf(designDets.getDesignID());
					case 5: //   design date
						return String.valueOf(designDets.getDate());
					case 6: // design status
						return designDets.getStatus();
					default:
						throw new IllegalArgumentException(
									"Column out of range: " + column);
				}
			}

			public boolean isCellEditable(int row,int column) {
				if (column == 0)
					return true;
				else
					return false;
			}
		};
	}

	public DesignDets getCurrentDesignDets() {
		return currentDesignDets;
	}

	void setupColumns() {
		TableColumn tableColumn;
		for (int columnIndex = 0; columnIndex < table1.getColumnCount(); columnIndex++) {
			tableColumn = table1.getColumnModel().getColumn(columnIndex);
			switch(columnIndex) {
				case 0: {
					tableColumn.setHeaderValue("Include in Invoice");
					JCheckBox jCheckBox = new JCheckBox();
					jCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
					TableCellEditor cellEditor = new DefaultCellEditor(jCheckBox);
					tableColumn.setCellEditor(cellEditor);
					break;
				}
				case 1: {
					tableColumn.setHeaderValue("Surname");
					break;
				}
				case 2: {
					tableColumn.setHeaderValue("Name");
					break;
				}
				case 3: {
					tableColumn.setHeaderValue("Design Name");
					break;
				}
				case 4: {
					tableColumn.setHeaderValue("Design Code");
					break;
				}
				case 5: {
					tableColumn.setHeaderValue("Date");
					break;
				}
				case 6: {
					tableColumn.setHeaderValue("Status");
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
	//	InvoiceConfigureDialog invoiceConfigureDialog = new InvoiceConfigureDialog();

	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// !!! IMPORTANT !!!
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * !!! IMPORTANT !!!
	 * DO NOT edit this method OR call it in your code!
	 */
	private void $$$setupUI$$$() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
		currentDesignradioButton = new JRadioButton();
		currentDesignradioButton.setText("Current Design");
		panel2.add(currentDesignradioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		multipleDesignRadioButton = new JRadioButton();
		multipleDesignRadioButton.setText("Multiple Design");
		panel2.add(multipleDesignRadioButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 20, -1));
		panel1.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
		final JLabel label1 = new JLabel();
		label1.setText("Client");
		panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		clientComboBox = new JComboBox();
		panel3.add(clientComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, -1), null, null));
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel1.add(panel4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
		numberLabel = new JLabel();
		numberLabel.setText("Invoice Number:");
		panel4.add(numberLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		dateLabel = new JLabel();
		dateLabel.setText("Date:");
		panel4.add(dateLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		numberValue = new JLabel();
		numberValue.setText("xxx");
		panel4.add(numberValue, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		dateValue = new JLabel();
		dateValue.setText("xxx");
		panel4.add(dateValue, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null));
		printButton = new JButton();
		printButton.setText("Print");
		panel5.add(printButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		panel5.add(cancelButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
		final Spacer spacer1 = new Spacer();
		panel5.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null));
		final Spacer spacer2 = new Spacer();
		panel5.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null));
		final JScrollPane scrollPane1 = new JScrollPane();
		mainPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null));
		table1 = new JTable();
		scrollPane1.setViewportView(table1);
	}

	class HeaderListener extends MouseAdapter {
		JTableHeader header;
		SortButtonRenderer renderer;
		HeaderListener(JTableHeader header, SortButtonRenderer renderer) {
			this.header = header;
			this.renderer = renderer;
		}

		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int col = header.columnAtPoint(e.getPoint());
				int sortCol = header.getTable().convertColumnIndexToModel(col);
				renderer.setPressedColumn(col);
				renderer.setSelectedColumn(col);
				header.repaint();
				if (header.getTable().isEditing()) {
					header.getTable().getCellEditor().stopCellEditing();
				}
				boolean isAscent;
				isAscent = SortButtonRenderer.DOWN == renderer.getState(col);
				( (SortableTableModel) header.getTable().getModel()).sortByColumn(sortCol,
																																					isAscent);
			}
		}

		public void mouseReleased(MouseEvent e) {
			int col = header.columnAtPoint(e.getPoint());
			renderer.setPressedColumn( -1); // clear
			header.repaint();
		}
	}
}
