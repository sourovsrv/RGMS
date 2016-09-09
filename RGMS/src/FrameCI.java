import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FrameCI extends JFrame {

	private JPanel contentpane = new JPanel();;
	private JTextField tfTID;
	private JTextField tfSPW;
	private JTextField tfBatch;
	private JTextField tfCID;
	private JTable table = new JTable(); ;
	private JComboBox comboBox = new JComboBox();;
	private Connection connect;
	JRadioButton rdbtnTheory = new JRadioButton("Theory");
	JRadioButton rdbtnLab = new JRadioButton("Lab");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCI frame = new FrameCI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	/**
	 * Create the frame.
	 */
	public FrameCI() {
		connect = DB.connectdb();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 471);
		
		contentpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentpane);
		contentpane.setLayout(null);
		
		JLabel lblCourseInfo = new JLabel("Course Info");
		lblCourseInfo.setForeground(Color.BLUE);
		lblCourseInfo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblCourseInfo.setBounds(269, 11, 82, 24);
		contentpane.add(lblCourseInfo);
		
		TxtFields();
		
		
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cid="",tid="",spw="",type="",batch="";
				cid=tfCID.getText();
				tid=tfTID.getText(); 
				spw=tfSPW.getText(); 
				if(rdbtnTheory.isSelected()==true) type="Theory";
				else type="Lab";
				batch=tfBatch.getText();
				
				insert(cid,tid,spw,type,batch);
				refreshTable();
				FillComboBox();

			}
		});
		btnInsert.setBounds(10, 361, 89, 23);
		contentpane.add(btnInsert);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(254, 68, 540, 353);
		contentpane.add(scrollPane);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int row= table.getSelectedRow();
					String CID_T = (table.getModel().getValueAt(row, 0).toString());
					String query = "Select * from CourseInfo where CourseID = ?";
					PreparedStatement pst = connect.prepareStatement(query);
					pst.setString(1, CID_T);
					ResultSet rs= pst.executeQuery();
					while(rs.next()){
						String s="";
						tfCID.setText(rs.getString("CourseID"));
						s=rs.getString("TeacherID");
						if(s==null) tfTID.setText(""); 
						else tfTID.setText(s);
						s=rs.getString("SlotPerWeek");
						if(s==null) tfSPW.setText("");
						else tfSPW.setText(s);
						s=rs.getString("Batch");
						if(s==null) tfBatch.setText("");
						else tfBatch.setText(s);
						//s="Theory";
						s=rs.getString("Type");
						if(s!=null&&s.matches("Lab")) rdbtnLab.setSelected(true);
						else rdbtnTheory.setSelected(true);
					}	
					pst.close();
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnImportFromStudentinfo = new JButton("Import From StudentInfo");
		btnImportFromStudentinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertfromstudent();
				refreshTable();
			}
		});
		btnImportFromStudentinfo.setBounds(468, 36, 223, 23);
		contentpane.add(btnImportFromStudentinfo);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "Select * from CourseInfo where CourseID = ?";
					String s="";
					PreparedStatement pst = connect.prepareStatement(query);
					pst.setString(1,(String) comboBox.getSelectedItem());
					ResultSet rs= pst.executeQuery();
					while(rs.next()){
						tfCID.setText(rs.getString("CourseID"));
						s=rs.getString("TeacherID");
						if(s==null) tfTID.setText(""); 
						else tfTID.setText(s);
						s=rs.getString("SlotPerWeek");
						if(s==null) tfSPW.setText("");
						else tfSPW.setText(s);
						s=rs.getString("Batch");
						if(s==null) tfBatch.setText("");
						else tfBatch.setText(s);
						//s="Theory";
						s=rs.getString("Type");
						if(s!=null&&s.matches("Lab")) rdbtnLab.setSelected(true);
						else rdbtnTheory.setSelected(true);
					}	
					pst.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}
			}
		});
		
		
		comboBox.setBounds(147, 66, 86, 20);
		contentpane.add(comboBox);
		
		
		buttonGroup.add(rdbtnTheory);
		rdbtnTheory.setBounds(71, 327, 64, 23);
		rdbtnTheory.setSelected(true);
		contentpane.add(rdbtnTheory);
		

		buttonGroup.add(rdbtnLab);
		rdbtnLab.setBounds(166, 327, 53, 23);
		contentpane.add(rdbtnLab);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cid="",tid="",spw="",type="",batch="";
				cid=tfCID.getText();
				tid=tfTID.getText(); 
				spw=tfSPW.getText(); 
				if(rdbtnTheory.isSelected()==true) type="Theory";
				else type="Lab";
				batch=tfBatch.getText();
				
				update(cid,tid,spw,type,batch);
				refreshTable();
				FillComboBox();
				
			}
		});
		btnUpdate.setBounds(130, 361, 89, 23);
		contentpane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("Delete Using CourseID");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cid="";
				cid=tfCID.getText();
				delete(cid);
				refreshTable();
				FillComboBox();
			}
		});
		btnDelete.setBounds(58, 395, 89, 23);
		contentpane.add(btnDelete);
		
		
		
		refreshTable();
		FillComboBox();
	}
	//Refresh the table after each Operation to show values from database
	private  void refreshTable(){
		try{
			String query = "Select * from CourseInfo";
			PreparedStatement pst = connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs!=null)
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private boolean SearchCourse(String CID){
		try{
			
			String query = "select * from CourseInfo where CourseID = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, CID);
			ResultSet rs = pst.executeQuery();
			if(rs.next()==true) {
				pst.close();
				return true;
			}
			else{
				pst.close();
				return false;
			}
		}catch(Exception e1){
			return false;
		}
	}

	//Insert into Database
	public void insert(String cid, String tid, String spw, String type,String batch){
		try{
			String query = "insert into CourseInfo (CourseId,TeacherID,SlotPerWeek,Type,Batch) values(?,?,?,?,?)";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, cid);
			pst.setString(2, tid);
			pst.setString(3, spw);
			pst.setString(4, type);
			pst.setString(5, batch);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Update into Database
	public void update(String cid, String tid, String spw, String type,String batch){
		try{
			String query = "Update CourseInfo SET TeacherID = ?, SlotPerWeek = ?, Type= ?, Batch = ? where CourseId= ?";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, tid);
			pst.setString(2, spw);
			pst.setString(3, type);
			pst.setString(4, batch);
			pst.setString(5, cid);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//Delete from Database
	public void delete(String cid){
		try{
			String query = "Delete from CourseInfo where CourseID = ? ";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, cid);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Import Courses from Student info database
	private void insertfromstudent()
	{
		try {
			//String query = "select DISTINCT CourseID from StudentInfo where NOT EXISTS( SELECT * FROM CourseInfo WHERE StudentInfo.CourseID = CourseInfo.CourseID )";
			String query = "select DISTINCT CourseID from StudentInfo";
			String query2 = "insert into CourseInfo (CourseID) values(?)";
			PreparedStatement pst = connect.prepareStatement(query);
			PreparedStatement pst2 = connect.prepareStatement(query2);
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				String s=rs.getString(1);
				if(SearchCourse(s)==true) continue;
				pst2.setString(1, s);
				pst2.execute();
				System.out.println(s);
			}
			pst.close();
			pst2.close();
			JOptionPane.showMessageDialog(null, "Inserted Sucesssfully");
		} catch (Exception e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	private void TxtFields(){

		JLabel lblCID = new JLabel("Course ID");
		lblCID.setBounds(26, 109, 72, 17);
		contentpane.add(lblCID);
		
		tfCID = new JTextField();
		tfCID.setBounds(147, 107, 86, 20);
		tfCID.setText("");
		contentpane.add(tfCID);
		tfCID.setColumns(10);
		
		
		JLabel lblTeacherID = new JLabel("Teacher ID");
		lblTeacherID.setBounds(26, 157, 72, 17);
		contentpane.add(lblTeacherID);
		
		tfTID = new JTextField();
		tfTID.setColumns(10);
		tfTID.setBounds(147, 155, 86, 20);
		tfTID.setText("");
		contentpane.add(tfTID);
		
		JLabel lblSlotPerWeek = new JLabel("Slot Per Week");
		lblSlotPerWeek.setBounds(26, 221, 82, 24);
		contentpane.add(lblSlotPerWeek);
		
		tfSPW = new JTextField();
		tfSPW.setBounds(147, 223, 86, 20);
		tfSPW.setText("");
		contentpane.add(tfSPW);
		tfSPW.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(26, 331, 46, 14);
		contentpane.add(lblType);
		
		tfBatch = new JTextField();
		tfBatch.setToolTipText("Theory/Lab");
		tfBatch.setBounds(147, 269, 86, 20);
		tfBatch.setText("");
		contentpane.add(tfBatch);
		tfBatch.setColumns(10);
		
		JLabel lblBatch = new JLabel("Batch");
		lblBatch.setBounds(26, 267, 53, 24);
		contentpane.add(lblBatch);
	}

	//	Fill ComboBox with Course
	private void FillComboBox(){
		try{
			comboBox.removeAllItems();
			String query = "select CourseID from CourseInfo";
			PreparedStatement pst = connect.prepareStatement(query);
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				comboBox.addItem(rs.getString(1));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
