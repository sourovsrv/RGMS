import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
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


public class FrameMainRoomInfo extends JFrame {

	private JPanel contentPane;
	private static Connection connect =null;
	private JButton btnNumberOfSlot;
	public static int TotalNumberOfSlotPerDay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMainRoomInfo frame = new FrameMainRoomInfo();
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
	
	public FrameMainRoomInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		connect = DB.connectdb();
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
		
		JButton btnInsertRoomInfo = new JButton("Insert Room Info");
		btnInsertRoomInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameInsertRoomInfo ri=new FrameInsertRoomInfo();
				ri.setVisible(true);
				ri.setExtendedState(ri.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				dispose();
			}
		});
		btnInsertRoomInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInsertRoomInfo.setBounds(500, 200, 165, 31);
		contentPane.add(btnInsertRoomInfo);
		
		JButton btnUpdateRoomInfo = new JButton("Edit Room Info");
		btnUpdateRoomInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameUpdateRoomInfo uri=new FrameUpdateRoomInfo();
				uri.setVisible(true);
				uri.setExtendedState(uri.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				dispose();
			}
		});
		btnUpdateRoomInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdateRoomInfo.setBounds(500, 400, 165, 31);
		contentPane.add(btnUpdateRoomInfo);
		
		TotalNumberOfSlotPerDay=QuerySlot();
		btnNumberOfSlot = new JButton("Number of Slot Per Day: "+TotalNumberOfSlotPerDay);
		btnNumberOfSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangeSlot();
			}
		});
		btnNumberOfSlot.setBounds(1000, 300, 250, 50);
		contentPane.add(btnNumberOfSlot);
		
		
	}
	private void ChangeSlot(){
		String options[] ={"5","6","7","8","9","10"};
		String s=(String) JOptionPane.showInputDialog(null, "Choose from Below","Number of Slot Per Day",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(s==null) return;
		int slot=Integer.parseInt(s);
		TotalNumberOfSlotPerDay=slot;
		try{
			String query="Delete * from RoomInfo";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.execute();
			pst.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		btnNumberOfSlot.setText("Number of Slot Per Day: "+ TotalNumberOfSlotPerDay);
		
	}
	public static int NumberOfSlotQuery(){
		int slot=TotalNumberOfSlotPerDay;
		if(slot<=5) slot=5;
		slot=QuerySlot();
		return slot;
	}
	private static int QuerySlot(){
		int slot=5;
		try{
			connect = DB.connectdb();
			String query="Select slot from RoomInfo";
			PreparedStatement pst=connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs!=null){
				while(rs.next()){
					String s=rs.getString("slot");
					int nw=Integer.parseInt(s);
					if(slot<nw)
					slot=nw;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return slot;
	}

}
