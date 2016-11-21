import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FrameRoutine extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRoutine frame = new FrameRoutine();
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
	public FrameRoutine() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1400, 1400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Override to Call previous frame
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Home hm=new Home();
                hm.frame.setVisible(true);
                e.getWindow().dispose();
                hm.frame.setExtendedState(hm.frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
            }
        });
		
		
		//Adding List
		ListHandler lsthnd= new ListHandler();
		contentPane.add(lsthnd.scrollPaneList);
		//contentPane.add(lsthnd.list);
		
		
		
		
		//Adding ScrollPane to this panel
		ButtonHandler btnhnd=new ButtonHandler();
		btnhnd.SubPanelCreator();
		contentPane.add(btnhnd.scrollPane);
				
        contentPane.add(btnhnd.btnplus);
        contentPane.add(btnhnd.btnminus);
        
	}

}
