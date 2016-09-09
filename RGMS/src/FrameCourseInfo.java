import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;


public class FrameCourseInfo extends JFrame {

	private JPanel contentPane;
	private JTextField tfTeacherID;
	private JTextField tfSlot;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCourseInfo frame = new FrameCourseInfo();
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
	public FrameCourseInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 419, 427);
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
                FrameMainCourseInfo mci=new FrameMainCourseInfo();
                mci.setVisible(true);
                e.getWindow().dispose();
            }
        });
		
		tfTeacherID = new JTextField();
		tfTeacherID.setBounds(166, 199, 86, 20);
		contentPane.add(tfTeacherID);
		tfTeacherID.setColumns(10);
		
		tfSlot = new JTextField();
		tfSlot.setBounds(166, 251, 86, 20);
		contentPane.add(tfSlot);
		tfSlot.setColumns(10);
		
		JRadioButton rdbtnTheory = new JRadioButton();
		rdbtnTheory.setText("Theory");
		buttonGroup.add(rdbtnTheory);
		rdbtnTheory.setSelected(true);
		rdbtnTheory.setBounds(34, 293, 109, 23);
		contentPane.add(rdbtnTheory);
		
		JRadioButton rdbtnLab = new JRadioButton("Lab");
		buttonGroup.add(rdbtnLab);
		rdbtnLab.setBounds(217, 293, 109, 23);
		contentPane.add(rdbtnLab);
		
		JButton btnSubmit = new JButton();
		btnSubmit.setText("Submit");
		btnSubmit.setBounds(163, 340, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel lblTeachersId = new JLabel();
		lblTeachersId.setText("Teacher's ID");
		lblTeachersId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTeachersId.setBounds(29, 202, 86, 20);
		contentPane.add(lblTeachersId);
		
		JLabel lblSlotPerWeek = new JLabel("Slot Per Week");
		lblSlotPerWeek.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSlotPerWeek.setBounds(29, 254, 86, 20);
		contentPane.add(lblSlotPerWeek);
		
		JLabel lblCourseInfo = new JLabel("Course Info");
		lblCourseInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCourseInfo.setBounds(166, 11, 109, 32);
		contentPane.add(lblCourseInfo);
		
		
		String[] str= {"Course1", "Course2","Course3"};
		JComboBox comboBox = new JComboBox(str);
		comboBox.setBounds(163, 97, 89, 20);
		contentPane.add(comboBox);
		
		JLabel lblCourseId = new JLabel("Course ID");
		lblCourseId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCourseId.setBounds(34, 94, 81, 23);
		contentPane.add(lblCourseId);
	}
}
