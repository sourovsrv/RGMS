import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class FrameSettings extends JFrame {

	private JPanel contentPane;
	//private static Connection connect =null;
	private JButton btnNumberOfSlot;
	//public static int TotalNumberOfSlotPerDay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameSettings frame = new FrameSettings();
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
	
	public FrameSettings() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		//connect = DB.connectdb();
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
                Home hm=new Home();
                hm.frame.setVisible(true);
                hm.frame.setExtendedState(hm.frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
                e.getWindow().dispose();
            }
        });
		
		JLabel lblSetttings = new JLabel("Settings");
		lblSetttings.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblSetttings.setBounds(550, 100, 200, 50);
		contentPane.add(lblSetttings);
		
		
		JButton btnManual = new JButton("Manual");
		btnManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FrameInsertRoomInfo ri=new FrameInsertRoomInfo();
				//ri.setVisible(true);
				//ri.setExtendedState(ri.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				//dispose();
			}
		});
		btnManual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnManual.setBounds(500, 200, 250, 30);
		contentPane.add(btnManual);
		
		btnNumberOfSlot = new JButton("Number of Slot Per Day: "+Home.TotalNumberOfSlot);
		btnNumberOfSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangeSlot();
			}
		});
		btnNumberOfSlot.setBounds(500, 300, 250, 30);
		contentPane.add(btnNumberOfSlot);
		
		
		JButton btnChangeDaylbl = new JButton("Change Day And Slot Label");
		btnChangeDaylbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameChangeDaySlotLabel dsl=new FrameChangeDaySlotLabel();
				dsl.setVisible(true);
				dsl.setExtendedState(dsl.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				dispose();
			}
		});
		btnChangeDaylbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChangeDaylbl.setBounds(500, 400, 250, 30);
		contentPane.add(btnChangeDaylbl);
		
		JButton btnClearRoutine = new JButton("Clear Routine");
		btnClearRoutine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClearRoutine();
			}
		});
		btnClearRoutine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClearRoutine.setBounds(500, 500, 250, 30);
		contentPane.add(btnClearRoutine);
				
	}
	//Clear All Routine
	private void ClearRoutine(){
		try{
			String query="Delete * from RoutineInfo";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.execute();
			pst.close();
			JOptionPane.showMessageDialog(null, "Routine has been Cleared");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void ChangeSlot(){
		String options[] ={"5","6","7","8","9"};
		String s=(String) JOptionPane.showInputDialog(null, "Choose from Below","Number of Slot Per Day",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(s==null) return;
		int slot=Integer.parseInt(s);
		Home.TotalNumberOfSlot=slot;
		try{
		String query="Update SettingsInfo SET ValueOfInfo = ? where TypeOfInfo= ?";
		PreparedStatement pst = Home.connect.prepareStatement(query);
		pst.setString(1, Home.TotalNumberOfSlot+"");
		pst.setString(2, "TotalNumberOfSlot");
		pst.execute();
		pst.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			String query="Delete * from RoutineInfo";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.execute();
			pst.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		btnNumberOfSlot.setText("Number of Slot Per Day: "+ Home.TotalNumberOfSlot);
		
	}
	

}
