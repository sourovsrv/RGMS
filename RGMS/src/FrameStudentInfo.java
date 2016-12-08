import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.JScrollPane;


public class FrameStudentInfo extends JFrame {

	private JPanel contentPane=new JPanel();
	//private static Connection connect=null;
	private JTextField tfStudentID=new JTextField();
	private JLabel lblSID = new JLabel();
	private JTextField tfSIDCID=new JTextField();;
	private JTextField tfCID=new JTextField();
	private JLabel lblCID = new JLabel();
	private JTextField tfCIDSID=new JTextField();;
	private JTable tableAll=new JTable();
	private JTable tableSID=new JTable();
	private JTable tableCID = new JTable();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameStudentInfo frame = new FrameStudentInfo();
					frame.setVisible(true);
					frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameStudentInfo() {
		//connect = DB.connectdb();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Override to Call previous frame
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Home hm=new Home();
                hm.frame.setVisible(true);
                e.getWindow().dispose();
                hm.frame.setExtendedState(hm.frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
            }
        });
		JLabel lblStudentInfo = new JLabel("Student Info");
		lblStudentInfo.setForeground(Color.RED);
		lblStudentInfo.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblStudentInfo.setBounds(600, 11, 150, 24);
		contentPane.add(lblStudentInfo);
	
		TxtFields();
		Buttons();
		
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(389, 117, 466, 500);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(tableAll);
		
		JScrollPane scrollPaneSID = new JScrollPane();
		scrollPaneSID.setBounds(26, 184, 200, 277);
		contentPane.add(scrollPaneSID);
		
		scrollPaneSID.setViewportView(tableSID);
		
		JScrollPane scrollPaneCID = new JScrollPane();
		scrollPaneCID.setBounds(950, 165, 350, 350);
		contentPane.add(scrollPaneCID);
		
		scrollPaneCID.setViewportView(tableCID);
		
		refreshTableAll();
	
	}
	private void Buttons(){
		JButton btnImport = new JButton("Import From Excel");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					readfile();
					refreshTableAll();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnImport.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnImport.setBounds(475, 52, 200, 30);
		contentPane.add(btnImport);
		
		
		JButton btnDeleteAll = new JButton("Delete All");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteAll();
				refreshTableAll();
			}
		});
		btnDeleteAll.setBounds(746, 77, 89, 23);
		contentPane.add(btnDeleteAll);
		

		JButton btnSIDSearch = new JButton("Search");
		btnSIDSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String s="";
				s=tfStudentID.getText();
				lblSID.setText(s);
				refreshTableSID(s);
			}
		});
		btnSIDSearch.setBounds(150, 99, 89, 23);
		contentPane.add(btnSIDSearch);
		
		
		JButton btnSIDInsert = new JButton("Add");
		btnSIDInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String sid="",cid="";
				sid=lblSID.getText();
				cid=tfSIDCID.getText();
				tfSIDCID.setText("");
				if(sid!=null&&cid!=null) insert(sid,cid);
				refreshTableAll();
				refreshTableSID(sid);
				
			}
		});
		btnSIDInsert.setBounds(8, 600, 89, 23);
		contentPane.add(btnSIDInsert);
		
		JButton btnSIDDelete = new JButton("Delete");
		btnSIDDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String sid="",cid="";
				sid=lblSID.getText();
				cid=tfSIDCID.getText();
				tfSIDCID.setText("");
				if(sid!=null&&cid!=null) delete(sid,cid);
				refreshTableAll();
				refreshTableSID(sid);
				
			}
		});
		btnSIDDelete.setBounds(146, 600, 89, 23);
		contentPane.add(btnSIDDelete);
		
		//Courses
		JButton btnCIDSearch = new JButton("Search");
		btnCIDSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String s="";
				s=tfCID.getText();
				lblCID.setText(s);
				refreshTableCID(s);
			}
		});
		btnCIDSearch.setBounds(1200, 78, 86, 20);
		contentPane.add(btnCIDSearch);
	
		
		JButton btnCIDInsert = new JButton("Add");
		btnCIDInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String sid="",cid="";
				cid=lblCID.getText();
				sid=tfCIDSID.getText();
				tfCIDSID.setText("");
				if(sid!=null&&cid!=null) insert(sid,cid);
				refreshTableAll();
				refreshTableCID(cid);
				
			}
		});
		btnCIDInsert.setBounds(1000, 600, 89, 23);
		contentPane.add(btnCIDInsert);
		
		
		
		JButton btnCIDDelete = new JButton("Delete");
		btnCIDDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String sid="",cid="";
				cid=lblCID.getText();
				sid=tfCIDSID.getText();
				tfCIDSID.setText("");
				if(sid!=null&&cid!=null) delete(sid,cid);
				refreshTableAll();
				refreshTableCID(cid);
				
			}
		});
		btnCIDDelete.setBounds(1200, 600, 89, 23);
		contentPane.add(btnCIDDelete);
	}
	
	//Refresh the table after each Operation to show values from database
	private  void refreshTableAll(){
		try{
			String query = "Select * from StudentInfo";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs!=null)
			tableAll.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Refresh the table after each Operation to show values from database
	private  void refreshTableSID(String ID){
		try{
			String query = "Select CourseID from StudentInfo where StudentID = ?";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, ID);
			ResultSet rs=pst.executeQuery();
			if(rs!=null)
			tableSID.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//Refresh the table after each Operation to show values from database
	private  void refreshTableCID(String ID){
		try{
			String query = "Select StudentID from StudentInfo where CourseID = ?";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, ID);
			ResultSet rs=pst.executeQuery();
			if(rs!=null)
			tableCID.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void TxtFields(){

		JLabel lblStudentID = new JLabel("Student ID");
		lblStudentID.setBounds(75, 58, 72, 17);
		contentPane.add(lblStudentID);
		
		
		tfStudentID.setBounds(11, 100, 100, 30);
		tfStudentID.setText("");
		contentPane.add(tfStudentID);
		tfStudentID.setColumns(10);
		
		
		lblSID.setBounds(79, 150, 100, 14);
		lblSID.setText("Student");
		contentPane.add(lblSID);
		
		
		JLabel lblChangeCourse = new JLabel("Change Course");
		lblChangeCourse.setBounds(11, 550, 100, 14);
		contentPane.add(lblChangeCourse);
		
		tfSIDCID.setBounds(136, 550, 86, 30);
		contentPane.add(tfSIDCID);
		tfSIDCID.setColumns(10);
		
		
		
		//Courses 
		JLabel lblCourseId = new JLabel("Course ID");
		lblCourseId.setBounds(1100, 36, 72, 17);
		contentPane.add(lblCourseId);
		
		tfCID.setText("");
		tfCID.setColumns(10);
		tfCID.setBounds(1000, 78, 86, 30);
		contentPane.add(tfCID);
		
		lblCID.setBounds(1100, 133, 60, 14);
		lblCID.setText("Course");
		contentPane.add(lblCID);
		
		JLabel lblInsertStudent = new JLabel("Change Student");
		lblInsertStudent.setBounds(1000, 550, 90, 14);
		contentPane.add(lblInsertStudent);
		
		tfCIDSID.setColumns(10);
		tfCIDSID.setBounds(1200, 550, 86, 30);
		contentPane.add(tfCIDSID);
		
		
	}
	
	
	
	//Read From Excel File 2007
	public static void readfile() throws IOException {
		
        String excelFilePath = "D:\\Docs\\Study Docs\\12th Semester\\CSE400\\Works\\Database\\StudentInfo.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int flag=0;
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int c=0;
            String cid="",sid="";
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(c==0){//If it is first value
                	sid=cell.getStringCellValue();
                	c=1;
                }
                else{//If it is second value
                	cid=cell.getStringCellValue();
                }
            }
            if(insert_data(sid,cid)==false) flag=1;//Insert to MSaccess
            //System.out.println(sid+" "+cid);
        }
         
        workbook.close();
        inputStream.close();
        if(flag==0)JOptionPane.showMessageDialog(null, "All value imported successfully.");
        else JOptionPane.showMessageDialog(null, "Value importing was interrupted.");
    }
	
	//Write to Access 
	public static boolean insert_data(String sid, String cid){
		try{
			String query = "insert into StudentInfo (StudentID, CourseID ) values (?,?)";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, sid);
			pst.setString(2, cid);
			pst.execute();
			pst.close();
			
			return true;
		
		}catch(Exception e){
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}
	//Insert into Database
	public void insert(String sid,String cid){
		try{
			String query = "insert into StudentInfo (StudentID,CourseID) values(?,?)";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, sid);
			pst.setString(2, cid);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//Delete from Database
	public void delete(String sid, String cid){
		try{
			String query = "Delete from StudentInfo where CourseID = ? AND StudentID = ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, cid);
			pst.setString(2, sid);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Delete from Database
	public void DeleteAll(){
		try{
			String query = "Delete * from StudentInfo";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
