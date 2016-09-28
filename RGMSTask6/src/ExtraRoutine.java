import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;

import com.sun.xml.internal.ws.api.server.Container;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import java.awt.Scrollbar;






public class ExtraRoutine extends JFrame {
	private JPanel contentPane;
	private JPanel acontentPane;
	private int btnflag=4;
	int btnleftpos = 40,btnuppos=50,btnhgt=25,btnwid=85;
	public static int totalday=1;
	public static int totalslot=6;
	public static int totalpos=3;
	static DefaultListModel<String> model = new DefaultListModel<>();
	static JList<String> list = new JList<>( model );
	
	static JButton btn[][][] = new JButton[100][100][10];
	JLabel lblTeacher[][][] = new JLabel[100][100][10];
	JComboBox boxRoom[][][] = new JComboBox[100][100][10];

	static Connection connect;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtraRoutine frame = new ExtraRoutine();
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
	public ExtraRoutine() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(20, 10, 1000, 700);
		contentPane = new JPanel();
		acontentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//getContentPane().setLayout(null);
		contentPane.setLayout(null);
		acontentPane.setLayout(null);
		
		
		list.setBounds(10, 37, 118, 518);
		InsertintoList();
		
		list.setTransferHandler(new ValueExportTransferHandler());
		
		 list.addMouseMotionListener(new MouseAdapter() {
             @Override
             public void mouseDragged(MouseEvent e) {
                 JList lst = (JList) e.getSource();
                 TransferHandler handle = lst.getTransferHandler();
                 handle.exportAsDrag(lst, e, TransferHandler.COPY);
             }
         });
		 contentPane.add(list);
		 
		
		
		
		int lblleftpos=95,lbluppos=0,lblhgt=10,lblwid=50;
		int boxleftpos=75,boxuppos=0,boxhgt = 60,boxwid= 60;
		
		
	
		for(int j=1;j<=totalday;j++){
			for(int k=1;k<=totalpos;k++){
				for(int i=1;i<=totalslot;i++){
					btn[j][i][k] = new JButton("CSE-XXX");
					btn[j][i][k].setBounds(btnleftpos+=200,btnuppos,btnwid,btnhgt);
					btn[j][i][k].setTransferHandler(new ValueImportTransferHandler());
					btn[j][i][k].setBackground(Color.GRAY);
					acontentPane.add(btn[j][i][k]);
					
					lblTeacher[j][i][k] = new JLabel("TTT");
					lblTeacher[j][i][k].setFont(new Font("Tahoma",Font.PLAIN,14));
					lblTeacher[j][i][k].setBounds(btnleftpos+lblleftpos,btnuppos+lbluppos,lblwid,lblhgt);
					acontentPane.add(lblTeacher[j][i][k]);
					
					boxRoom[j][i][k] = new JComboBox();
					boxRoom[j][i][k].addItem("RRR-RRR");
					boxRoom[j][i][k].setBounds(btnleftpos-boxleftpos, btnuppos+boxuppos, 70, 20);
					acontentPane.add(boxRoom[j][i][k]);
				}
				btnleftpos= 40; btnuppos+=50;
				}
				
		}
		//int btnflag=1;
		btn[1][1][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addbutton(1,4,btnleftpos,btnuppos,btnwid,btnhgt);
				//btnflag++;

			}
		});
		acontentPane.setPreferredSize(new Dimension(1400,1400));
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(acontentPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150, 30, 1200, 650);
        contentPane.setPreferredSize(new Dimension(1000,800));
        contentPane.add(scrollPane);
		
		//SetCourses();
	}
	
	public void deletebutton(int i,int k){
		for(int j=1;j<=totalslot;j++)
		{
			btn[i][j][k].setVisible(false);
		}
	}
	public void addbutton(int i,int k,int btnleftpos,int btnuppos,int btnwid,int btnhgt){

		for(int j=1;j<=totalslot;j++){
			btn[i][j][k] = new JButton("CSE-XXX");
			btn[i][j][k].setBounds(btnleftpos+=200,btnuppos,btnwid,btnhgt);
			btn[i][j][k].setTransferHandler(new ValueImportTransferHandler());
			btn[i][j][k].setBackground(Color.GRAY);
			getContentPane().add(btn[i][j][k]);
		}
		
	}
	public void InsertintoList(){
		try{
			String s="NULL";
			model.addElement(s);
			connect = DB.connectdb();
			String query = "Select CourseID from CourseInfo";
			PreparedStatement pst = connect.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				s=rs.getString("CourseID");
				model.addElement(s);
			}
			rs.close();
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
    
	
	
	//Insert into Database
	public static void InsertRoutine(String di, String si, String pi, String course){
		try{
			String query = "insert into RoutineInfo (Day,Slot,Pos,CourseID) values(?,?,?,?)";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, di);
			pst.setString(2, si);
			pst.setString(3, pi);
			pst.setString(4, course);
			
			pst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, e);
		}
	}
	//Update into Database
	public static void UpdateRoutine(String di, String si, String pi, String course){
		try{
			String query = "Update RoutineInfo SET CourseID = ? where Day= ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, course);
			pst.setString(2, di);
			pst.setString(3, si);
			pst.setString(4, pi);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public static void SetCourses(){
		
		ConflictCheck objconf = new ConflictCheck();
	
		for(int di=1;di<=totalday;di++){
			for(int pi=1;pi<=totalpos;pi++){
				for(int si=1;si<=2;si++){
					String s=btn[di][si][pi].getText();
					ResultSet rs=objconf.FindCourses(di,si,pi);
					try {
						String s1="";
						while(rs.next()){
							s1=rs.getString("CourseID");
						}
						if(s1.equals(s)) continue;
						//JOptionPane.showMessageDialog(null, s1);
						if(s1.equals("")){
							if(s.equals("CSE-XXX")) continue;
							InsertRoutine(di+"",si+"",pi+"",s);
						}
						else{
							if(s.equals("CSE-XXX")) btn[di][si][pi].setText(s1);
							else UpdateRoutine(di+"",si+"",pi+"",s);
						}
						
						rs.close();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	
	public static void CheckAndChange(){
		ConflictCheck objconf = new ConflictCheck();
		 
		for(int di=1;di<=totalday;di++){
			for(int pi=1;pi<=totalpos;pi++){
				for(int si=1;si<=2;si++){
					
					if(objconf.BatchConflict(di, si, pi)==1) btn[di][si][pi].setBackground(Color.RED);
					else btn[di][si][pi].setBackground(Color.GRAY);
					//else if(objconf.StudentConflict(di, si, pi,pi+1)==1) btn[di][si][pi].setBackground(Color.RED);
					//else if(objconf.TeacherConflict(di, si, pi)==1) btn[di][si][pi].setBackground(Color.RED);
				}
			}
		}
	}
	
	public static class ValueImportTransferHandler extends TransferHandler {

        public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;

        public ValueImportTransferHandler() {
        }

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
        	
        	return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
            
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            boolean accept = false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                    if (value instanceof String) {
                        Component component = support.getComponent();
                        if (component instanceof JButton) {
                            ((JButton) component).setText(value.toString());
                            //System.out.println("S");
                            SetCourses();
                            CheckAndChange();
                            accept = true;
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            return accept;
        }
    }
	public static class ValueExportTransferHandler extends TransferHandler {

        public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
        private String value;

        public ValueExportTransferHandler() {
            //this.value = value;
        }

        public String getValue() {
        	value= list.getSelectedValue();
            return value;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable t = new StringSelection(getValue());
           
            return t;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            super.exportDone(source, data, action);
            // Decide what to do after the drop has been accepted
        }

    }
}
