import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FrameMainRoomInfo extends JFrame {

	private JPanel contentPane;

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
                e.getWindow().dispose();
            }
        });
		
		JButton btnInsertRoomInfo = new JButton("Insert Room Info");
		btnInsertRoomInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameRoomInfo ri=new FrameRoomInfo();
				ri.setVisible(true);
				dispose();
			}
		});
		btnInsertRoomInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInsertRoomInfo.setBounds(131, 42, 165, 31);
		contentPane.add(btnInsertRoomInfo);
		
		JButton btnEditRoomInfo = new JButton("Edit Room Info");
		btnEditRoomInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditRoomInfo.setBounds(131, 147, 165, 31);
		contentPane.add(btnEditRoomInfo);
	}

}
