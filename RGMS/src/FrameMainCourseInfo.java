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


public class FrameMainCourseInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMainCourseInfo frame = new FrameMainCourseInfo();
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
	
	public FrameMainCourseInfo() {
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
		
		JButton btnInsertCourseInfo = new JButton("Insert Course Info");
		btnInsertCourseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameCourseInfo ci=new FrameCourseInfo();
				ci.setVisible(true);
				dispose();
			}
		});
		btnInsertCourseInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInsertCourseInfo.setBounds(131, 42, 165, 31);
		contentPane.add(btnInsertCourseInfo);
		
		JButton btnEditCourseInfo = new JButton("Edit Course Info");
		btnEditCourseInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditCourseInfo.setBounds(131, 147, 165, 31);
		contentPane.add(btnEditCourseInfo);
	}

}
