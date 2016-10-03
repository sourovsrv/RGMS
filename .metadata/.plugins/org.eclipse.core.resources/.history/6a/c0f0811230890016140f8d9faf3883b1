import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ImportStudentInfo extends JFrame {
	private static Connection connect=null;
	//Read From Excel 2007
	public static void readfile() throws IOException {
		
        String excelFilePath = "D:\\Docs\\Study Docs\\12th Semester\\CSE400\\Works\\Database\\StudentInfo.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int flag=0;
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int c=0;
            String cid="",sid="";
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(c==0){//If it is first value
                	sid=cell.getStringCellValue();
                	c=1;
                }
                else{//If it is second value
                	cid=cell.getStringCellValue();
                }
            }
            if(insert_data(sid,cid)==false) flag=1;//Insert to MSaccess
            //System.out.println(sid+" "+cid);
        }
         
        workbook.close();
        inputStream.close();
        if(flag==0)JOptionPane.showMessageDialog(null, "All value imported successfully.");
        else JOptionPane.showMessageDialog(null, "Value importing was interrupted.");
    }
	//Write to Access 
	public static boolean insert_data(String sid, String cid){
		try{
			String query = "insert into StudentInfo (StudentID, CourseID ) values (?,?)";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, sid);
			pst.setString(2, cid);
			pst.execute();
			pst.close();
			
			return true;
		
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}


	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportStudentInfo frame = new ImportStudentInfo();
					frame.setVisible(true);
					//readfile();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImportStudentInfo() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		connect = DB.connectdb();
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
                FrameStudentInfo sti=new FrameStudentInfo();
                sti.setVisible(true);
                e.getWindow().dispose();
            }
        });
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					readfile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnImport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImport.setBounds(92, 93, 162, 54);
		contentPane.add(btnImport);
	}

}
