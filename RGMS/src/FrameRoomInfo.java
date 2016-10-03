import java.awt.BorderLayout;
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
import java.text.NumberFormat;

import javax.swing.JCheckBox;

import com.sun.corba.se.spi.orbutil.fsm.Input;


public class FrameRoomInfo extends JFrame {
	private static Connection connect =null;
	private JPanel contentPane;
	private JTextField tfRoomId;
	private JTextField tfNmbrStdnts;
	private JRadioButton rdbtnRegular;
	private JRadioButton rdbtnLab;
	private final ButtonGroup buttonGroupRoomInfo = new ButtonGroup();
	private JButton btnsubmit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRoomInfo frame = new FrameRoomInfo();
					frame.setVisible(true);
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

			for(int i=1;i<=7;i++){//Day
				for(int j=1;j<=6;j++){//Slot
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
	public FrameRoomInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		connect = DB.connectdb();
		setBounds(100, 100, 651, 377);
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
		tfRoomId.setBounds(228, 51, 155, 25);
		contentPane.add(tfRoomId);
		tfRoomId.setColumns(10);
		
		JLabel lblRoomId = new JLabel("Room ID");
		lblRoomId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRoomId.setBounds(20, 57, 86, 25);
		contentPane.add(lblRoomId);
		
		
		tfNmbrStdnts = new JTextField();
		tfNmbrStdnts.setToolTipText("Only Number is valid");
		tfNmbrStdnts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfNmbrStdnts.setBounds(228, 121, 148, 32);
		contentPane.add(tfNmbrStdnts);
		tfNmbrStdnts.setColumns(10);
		
		JLabel lblNumberOfStudents = new JLabel("Number of Students");
		lblNumberOfStudents.setToolTipText("How many student can sit on the classs");
		lblNumberOfStudents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfStudents.setBounds(20, 124, 139, 23);
		contentPane.add(lblNumberOfStudents);
		
		rdbtnRegular = new JRadioButton();
		rdbtnRegular.setText("Regular");
		rdbtnRegular.setSelected(true);
		buttonGroupRoomInfo.add(rdbtnRegular);
		rdbtnRegular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnRegular.setBounds(51, 201, 200, 50);
		contentPane.add(rdbtnRegular);
		
		rdbtnLab = new JRadioButton();
		rdbtnLab.setText("Lab");
		buttonGroupRoomInfo.add(rdbtnLab);
		rdbtnLab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLab.setBounds(292, 201, 200, 50);
		contentPane.add(rdbtnLab);
		
		
		btnsubmit = new JButton("Submit");
		btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type="";
				int nmbr=0;
				try{
					nmbr = Integer.parseInt(tfNmbrStdnts.getText());
					if(rdbtnRegular.isSelected()==true) type="Regular";
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
			
			}
		});
		btnsubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnsubmit.setBounds(225, 288, 89, 23);
		contentPane.add(btnsubmit);
	}
}
