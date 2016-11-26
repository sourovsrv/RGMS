import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Home {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//Applying Look and Feel
				/*try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }*/
				
				try {
					Home window = new Home();
					window.frame.setVisible(true);
					window.frame.setExtendedState(window.frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 743, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblHome.setBounds(600, 100, 200, 50);
		frame.getContentPane().add(lblHome);
		
		
		JButton btnRoomInfo = new JButton("Room Info");
		btnRoomInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameMainRoomInfo mri=new FrameMainRoomInfo();
				mri.setVisible(true);
				frame.dispose();
				mri.setExtendedState(mri.getExtendedState()| JFrame.MAXIMIZED_BOTH);
			}
		});
		btnRoomInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRoomInfo.setBounds(400, 200, 200, 50);
		frame.getContentPane().add(btnRoomInfo);
		
		
		JButton btnStudentInfo = new JButton("Student Info");
		btnStudentInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			FrameStudentInfo si= new FrameStudentInfo();
			si.setVisible(true);
			frame.dispose();
			si.setExtendedState(si.getExtendedState()| JFrame.MAXIMIZED_BOTH);
			}
		});
		btnStudentInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStudentInfo.setBounds(800, 200, 200, 50);
		frame.getContentPane().add(btnStudentInfo);
		
		JButton btnCourseInfo = new JButton("Course Info");
		btnCourseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameCourseInfo ci = new FrameCourseInfo();
				ci.setVisible(true);
				frame.dispose();
				ci.setExtendedState(ci.getExtendedState()| JFrame.MAXIMIZED_BOTH);
			}
		});
		btnCourseInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCourseInfo.setBounds(400, 400, 200, 50);
		frame.getContentPane().add(btnCourseInfo);
		
		JButton btnRoutine = new JButton("Routine");
		btnRoutine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameRoutine rti = new FrameRoutine();
				rti.setVisible(true);
				frame.dispose();
				rti.setExtendedState(rti.getExtendedState()| JFrame.MAXIMIZED_BOTH);
			}
		});
		btnRoutine.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRoutine.setBounds(800, 400, 200, 50);
		frame.getContentPane().add(btnRoutine);
	}
}
