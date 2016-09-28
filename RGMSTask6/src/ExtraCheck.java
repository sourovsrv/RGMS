import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;


public class ExtraCheck extends JFrame {

	private JPanel contentPane;;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtraCheck frame = new ExtraCheck();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExtraCheck() {
		JButton btn[] = new JButton[100]; 
		JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        //panel.setLayout(null);
        int btnpos=30;
        //panel.setPreferredSize(new Dimension(200, 200));
        //panel.setBounds(50, 30, 700, 700);
        for (int i = 0; i < 10; i++) {
        	btn[i] = new JButton("Hello-" + i);
        	//btn[i].setBounds(50, btnpos+=90, 500, 20);
            panel.add(btn[i]);
        }
        panel.setPreferredSize(new Dimension(800,800));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        //panel.setLayout(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 30, 600, 600);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(600, 600));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
       
        frame.setBounds(1, 1, 900, 700);
        //frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 598, 732);
		contentPane = new JPanel();
		
		
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel1 = new JPanel();

		//panel1.set[Preferred/Maximum/Minimum]Size()
		panel1.setPreferredSize(new Dimension(-400, 400));
		
		contentPane.add(panel1);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		JPanel panel2 = new JPanel();
		scrollPane.setViewportView(panel2);
		panel2.setLayout(null);
		
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 25, 89, 23);
		panel2.add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("New button");
		btnNewButton2.setBounds(33, 500, 89, 23);
		panel2.add(btnNewButton2);

		setContentPane(contentPane);*/
	}
}
