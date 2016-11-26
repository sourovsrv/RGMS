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
import javax.swing.JComboBox;


public class FrameUpdateRoomInfo extends JFrame {
	private static Connection connect =null;
	private JPanel contentPane;
	private final ButtonGroup buttonGroupRoomInfo = new ButtonGroup();
	private JButton btnUpdate;
	private JComboBox comboBox = new JComboBox();
	private int NumberOfSlot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameUpdateRoomInfo frame = new FrameUpdateRoomInfo();
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
			PreparedStatement pst = connect.prepareStatement(query);
			int slot=FrameMainRoomInfo.NumberOfSlotQuery();
			for(int i=1;i<=7;i++){//Day
				for(int j=1;j<=slot;j++){//Slot
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

	/**
	 * Create the frame.
	 */
	public FrameUpdateRoomInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		connect = DB.connectdb();
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
		
		JLabel lblRoomId = new JLabel("Room ID");
		lblRoomId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRoomId.setBounds(241, 46, 86, 25);
		contentPane.add(lblRoomId);
		
		NumberOfSlot=FrameMainRoomInfo.NumberOfSlotQuery();
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(342, 621, 89, 23);
		contentPane.add(btnUpdate);
		
		JLabel lblUpdateRoomInfo = new JLabel("Update Room Information");
		lblUpdateRoomInfo.setBounds(243, 11, 300, 24);
		lblUpdateRoomInfo.setForeground(Color.BLACK);
		lblUpdateRoomInfo.setFont(new Font("Tahoma",Font.BOLD,20));
		contentPane.add(lblUpdateRoomInfo);
		
		
		comboBox.setBounds(358, 50, 103, 21);
		contentPane.add(comboBox);
		
		ShowDaySlot();
		FillComboBox();
	}
	private void ShowDaySlot(){
		
		JLabel lblSlot[] = new JLabel[12];
		JLabel lblDay[] = new JLabel[10];
		int dayi,sloti,slotpos,daypos;
		for(sloti=1,slotpos=200;sloti<=NumberOfSlot;sloti++,slotpos+=100){
			lblSlot[sloti] = new JLabel("Slot "+sloti);
			lblSlot[sloti].setBounds(slotpos,128, 46, 14);
			contentPane.add(lblSlot[sloti]);
		}
		JCheckBox chckbxSlot[][] = new JCheckBox[10][12];
		for(dayi=1,daypos=180;dayi<=7;dayi++,daypos+=50){
			lblDay[dayi] = new JLabel("Day "+dayi);
			lblDay[dayi].setBounds(50,daypos, 46, 14);
			contentPane.add(lblDay[dayi]);
			
			for(sloti=1,slotpos=200;sloti<=NumberOfSlot;sloti++,slotpos+=100){
				chckbxSlot[dayi][sloti]=new JCheckBox("Status");
				chckbxSlot[dayi][sloti].setBounds(slotpos, daypos, 97, 23);
				contentPane.add(chckbxSlot[dayi][sloti]);
			}
		}
		
		
		
	}
	//	Fill ComboBox with Room
	private void FillComboBox(){
		try{
			comboBox.removeAllItems();
			String query = "select Distinct RoomID from RoomInfo";
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
