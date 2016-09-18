import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;


public class NewRoutine extends JFrame {
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewRoutine frame = new NewRoutine();
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
	public NewRoutine() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(20, 10, 1300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		int btnleftpos = 10,btnuppos=20,btnlen=50,btnwid=50;
		
		JButton btn[] = new JButton[10];
		
		/*btn[0] = new JButton("X");
		btn[0].setBounds(btnleftpos,btnuppos,btnlen,btnwid);
		getContentPane().add(btn[0]);*/
		
		for(int i=0;i<10;i++){
			btn[i] = new JButton("X");
			btn[i].setBounds(btnleftpos+=100,btnuppos,btnlen,btnwid);
			getContentPane().add(btn[i]);
			
		}
		
		/*JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(btnleftpos, btnuppos, btnlen, btnwid);
		getContentPane().add(btnNewButton);*/
	}
}