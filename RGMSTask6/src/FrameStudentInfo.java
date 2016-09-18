import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FrameStudentInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameStudentInfo frame = new FrameStudentInfo();
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
	public FrameStudentInfo() {
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
		
		JButton btnImportStudentInfo = new JButton("Import Student Info");
		btnImportStudentInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImportStudentInfo isi= new ImportStudentInfo();
				isi.setVisible(true);
				dispose();
			}
		});
		btnImportStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImportStudentInfo.setBounds(121, 59, 174, 47);
		contentPane.add(btnImportStudentInfo);
		
		JButton btnUpdateStudentInfo = new JButton("Update Student Info");
		btnUpdateStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdateStudentInfo.setBounds(121, 150, 174, 47);
		contentPane.add(btnUpdateStudentInfo);
	}

}
