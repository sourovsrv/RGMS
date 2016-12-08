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


public class FrameChangeDaySlotLabel extends JFrame {
	//private static Connection connect =null;
	private JPanel contentPane;
	private JButton btnUpdateDay;
	private JButton btnUpdateSlot;
	private JTextField tfDay[] = new JTextField[10];
	private JTextField tfslot[] = new JTextField[10];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameChangeDaySlotLabel frame = new FrameChangeDaySlotLabel();
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
	public FrameChangeDaySlotLabel() {
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
                FrameSettings stng=new FrameSettings();
                stng.setVisible(true);
                stng.setExtendedState(stng.getExtendedState()| JFrame.MAXIMIZED_BOTH);
                e.getWindow().dispose();
            }
        });
		
		
		JLabel lblDaySlot = new JLabel("Change Day And Slot Label");
		lblDaySlot.setBounds(243, 11, 300, 24);
		lblDaySlot.setForeground(Color.BLACK);
		lblDaySlot.setFont(new Font("Tahoma",Font.BOLD,20));
		contentPane.add(lblDaySlot);
		
		JLabel lblDay[] = new JLabel[10];
		int lblstart=100;
		for(int dayi=1;dayi<=7;dayi++){
			lblDay[dayi]=new JLabel("Day "+dayi);
			lblDay[dayi].setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDay[dayi].setBounds(20, lblstart+dayi*60, 86, 25);
			contentPane.add(lblDay[dayi]);
			
			tfDay[dayi] = new JTextField("");
			tfDay[dayi].setFont(new Font("Tahoma", Font.PLAIN, 14));
			tfDay[dayi].setBounds(200, lblstart+dayi*60, 80, 30);
			contentPane.add(tfDay[dayi]);
			tfDay[dayi].setColumns(10);
			
		}
		
						
		btnUpdateDay = new JButton("Update");
		btnUpdateDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateday();
			
			}
		});
		btnUpdateDay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdateDay.setBounds(80, lblstart+9*60, 89, 23);
		contentPane.add(btnUpdateDay);
		
		JLabel lblSlot[] = new JLabel[10];
		lblstart=100;
		for(int sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
			lblSlot[sloti]=new JLabel("Slot "+sloti);
			lblSlot[sloti].setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSlot[sloti].setBounds(800, lblstart+sloti*60, 86, 25);
			contentPane.add(lblSlot[sloti]);
			
			tfslot[sloti] = new JTextField("");
			tfslot[sloti].setFont(new Font("Tahoma", Font.PLAIN, 14));
			tfslot[sloti].setBounds(1000, lblstart+sloti*60, 120, 30);
			contentPane.add(tfslot[sloti]);
			tfslot[sloti].setColumns(10);
		}
		
		btnUpdateSlot = new JButton("Update");
		btnUpdateSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateslot();
			
			}
		});
		btnUpdateSlot.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdateSlot.setBounds(900, lblstart+9*60, 89, 23);
		contentPane.add(btnUpdateSlot);
	}
	
	//Update into Database
	public void updateday(){
		try{
			String query = "Update SettingsInfo SET ValueOfInfo = ? where TypeOfInfo= ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			for(int dayi=1;dayi<=7;dayi++){
				pst.setString(1, tfDay[dayi].getText());
				pst.setString(2, "Day"+dayi);
				pst.execute();
			}
			pst.close();
			JOptionPane.showMessageDialog(null, "Value Updated");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Update Slot into Database
	public void updateslot(){
		try{
			String query = "Update SettingsInfo SET ValueOfInfo = ? where TypeOfInfo= ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			for(int sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
				pst.setString(1, tfslot[sloti].getText());
				pst.setString(2, "Slot"+sloti);
				pst.execute();
			}
			pst.close();
			JOptionPane.showMessageDialog(null, "Value Updated");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
