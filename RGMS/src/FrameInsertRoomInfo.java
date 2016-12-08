import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;

import javax.swing.JCheckBox;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;


public class FrameInsertRoomInfo extends JFrame {
	//private static Connection connect =null;
	private JPanel contentPane;
	private JTextField tfRoomId;
	private JTextField tfNmbrStdnts;
	private JRadioButton rdbtnTheory;
	private JRadioButton rdbtnLab;
	private final ButtonGroup buttonGroupRoomInfo = new ButtonGroup();
	private JButton btnInsert;
	private JButton btnDelete;
	private JTable table=new JTable();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInsertRoomInfo frame = new FrameInsertRoomInfo();
					frame.setVisible(true);
					frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Insert Data into RoomInfo Table
	public static boolean insert_data(String rid, int nmb, String type){
		try{
			String nmbr=nmb+"";
			String query = "insert into RoomInfo (RoomID,Capacity,Type,Day,Slot) values (?,?,?,?,?)";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			//int slot=FrameMainRoomInfo.NumberOfSlotQuery();
			for(int i=1;i<=7;i++){//Day
				for(int j=1;j<=Home.TotalNumberOfSlot;j++){//Slot
					pst.setString(1, rid);
					pst.setString(2, nmbr);
					pst.setString(3, type);
					pst.setString(4, i+"");
					pst.setString(5,j+"");
					pst.execute();
						
				}
			}
			pst.close();
			return true;//If insertion is successful
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}
	//Delete Data From RoomInfo Table
	public static void Delete(String rid){
		try{
			String query = "delete from RoomInfo where RoomID = ?";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, rid);
			pst.execute();	
			pst.close();
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Create the frame.
	 */
	public FrameInsertRoomInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//connect = DB.connectdb();
		setBounds(100, 100, 833, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Override to Call previous frame
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                FrameMainRoomInfo mri=new FrameMainRoomInfo();
                mri.setVisible(true);
                mri.setExtendedState(mri.getExtendedState()| JFrame.MAXIMIZED_BOTH);
                e.getWindow().dispose();
            }
        });
		
		tfRoomId = new JTextField();
		tfRoomId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfRoomId.setBounds(228, 151, 155, 25);
		contentPane.add(tfRoomId);
		tfRoomId.setColumns(10);
		
		JLabel lblRoomId = new JLabel("Room ID");
		lblRoomId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRoomId.setBounds(20, 157, 86, 30);
		contentPane.add(lblRoomId);
		
		
		tfNmbrStdnts = new JTextField();
		tfNmbrStdnts.setToolTipText("Only Number is valid");
		tfNmbrStdnts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfNmbrStdnts.setBounds(228, 221, 148, 32);
		contentPane.add(tfNmbrStdnts);
		tfNmbrStdnts.setColumns(10);
		
		JLabel lblNumberOfStudents = new JLabel("Number of Students");
		lblNumberOfStudents.setToolTipText("How many student can sit on the classs");
		lblNumberOfStudents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfStudents.setBounds(20, 224, 139, 23);
		contentPane.add(lblNumberOfStudents);
		
		rdbtnTheory = new JRadioButton();
		rdbtnTheory.setText("Theory");
		rdbtnTheory.setSelected(true);
		buttonGroupRoomInfo.add(rdbtnTheory);
		rdbtnTheory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnTheory.setBounds(51, 301, 200, 50);
		contentPane.add(rdbtnTheory);
		
		rdbtnLab = new JRadioButton();
		rdbtnLab.setText("Lab");
		buttonGroupRoomInfo.add(rdbtnLab);
		rdbtnLab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLab.setBounds(292, 301, 101, 50);
		contentPane.add(rdbtnLab);
		
		
		btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type="";
				int nmbr=0;
				try{
					nmbr = Integer.parseInt(tfNmbrStdnts.getText());
					if(rdbtnTheory.isSelected()==true) type="Theory";
					else type="Lab";
					boolean nw=insert_data(tfRoomId.getText(),nmbr,type);
					if(nw==true){
						JOptionPane.showMessageDialog(null, "Value Inserted");
						tfRoomId.setText("");
						tfNmbrStdnts.setText("");
					}
				}catch(Exception Enmbr){
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
				refreshTable();
			
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInsert.setBounds(75, 388, 89, 23);
		contentPane.add(btnInsert);
		
		JLabel lblInsertRoomInfo = new JLabel("Insert Room Information");
		lblInsertRoomInfo.setBounds(243, 11, 300, 24);
		lblInsertRoomInfo.setForeground(Color.BLACK);
		lblInsertRoomInfo.setFont(new Font("Tahoma",Font.BOLD,20));
		contentPane.add(lblInsertRoomInfo);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s=tfRoomId.getText();
				if(s!=null) Delete(s);
				refreshTable();
				tfRoomId.setText("");
				tfNmbrStdnts.setText("");
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(260, 390, 89, 23);
		contentPane.add(btnDelete);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(664, 79, 453, 432);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table);
		refreshTable();
	}
	
	//Refresh the table after each Operation to show values from database
	private  void refreshTable(){
		try{
			String query = "Select Distinct RoomID, Capacity, Type from RoomInfo";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs!=null)
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
