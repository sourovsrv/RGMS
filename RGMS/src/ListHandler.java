import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;




public class ListHandler {
	static DefaultListModel<String> model = new DefaultListModel<>();
	static JList<String> list = new JList<>( model );
	JScrollPane scrollPaneList;
	//static Connection connect;
	
	static Map<String, Integer> TotalSlotPerWeekCourse = new HashMap<String, Integer>(200);//Total Number of Slot for Any Course
	static Map<String, Integer> CurrentSlotPerWeekCourse = new HashMap<String, Integer>(200);//Current Number Of Slot 
	static Map<String, Integer> CourseIsPresentInList = new HashMap<String, Integer>(200);//Whether it is present in List or Not
	
	ListHandler(){
		//connect = DB.connectdb();
		
		
		scrollPaneList = new JScrollPane();;
        scrollPaneList.setViewportView(list);
        scrollPaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneList.setBounds(5, 25, 100, 600);
		
		list.setPreferredSize(new Dimension(120,2000));
        //list.setBounds(5, 25, 100, 600);
		FillTotalNumberOFSlot();
		UpdateCurrentSlotPerWeek();
		InsertIntoList();
		
		
		list.setTransferHandler(new ValueExportTransferHandler());
		
		
		//Adding Mouse Listener
		 list.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JList lst = (JList) e.getSource();
                TransferHandler handle = lst.getTransferHandler();
                handle.exportAsDrag(lst, e, TransferHandler.COPY);
            }
        });//
	}
	
	private void FillTotalNumberOFSlot(){
		try{
			TotalSlotPerWeekCourse.clear();
			String s="";
			int slot=0;
			String query = "Select CourseID,SlotPerWeek from CourseInfo";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				s=rs.getString("CourseID");
				slot=rs.getInt("SlotPerWeek");
				TotalSlotPerWeekCourse.put(s,slot);
				//System.out.println(s+" "+slot);
			}
			rs.close();
			pst.close();
			/*for(String key: TotalSlotPerWeekCourse.keySet()){
				slot=TotalSlotPerWeekCourse.get(key);
				System.out.println(key+" "+slot);
			}*/
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Update the maps of Current slot list
	private static void UpdateCurrentSlotPerWeek(){
		int slot,dayi,posi,sloti;
		String course;
		CurrentSlotPerWeekCourse.clear();
		//Putting all from total to current
		for(String key: TotalSlotPerWeekCourse.keySet()){
			slot=TotalSlotPerWeekCourse.get(key);
			//System.out.println(key+" "+slot);
			CurrentSlotPerWeekCourse.put(key,slot);
		}
		//Reducing it according to matrix;
		ButtonHandler btnhnd=new ButtonHandler();
		//int totalslot=FrameMainRoomInfo.NumberOfSlotQuery();
		for(dayi=1;dayi<=btnhnd.totalday;dayi++){
			for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
				for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
					course=ConflictCheck.Matrix[dayi][sloti][posi][1];//1 for course
					if(course!=null){
						slot=CurrentSlotPerWeekCourse.get(course);
						slot--;
						CurrentSlotPerWeekCourse.put(course,slot);
						//System.out.println(course+" "+CurrentSlotPerWeekCourse.get(course));
						
					}
					
				}
			}
		}
		/*for(String key: CurrentSlotPerWeekCourse.keySet()){
			slot=CurrentSlotPerWeekCourse.get(key);
			System.out.println(key+" "+slot);
		}*/
	}
	
	
	//Filling List with Courses From CourseInfo Table
	public static void InsertIntoList(){
		try{
			int slot;
			model.removeAllElements();
			String s="CSE-XXX";
			model.addElement(s);
			for(String key: CurrentSlotPerWeekCourse.keySet()){
				slot=CurrentSlotPerWeekCourse.get(key);
				if(slot>0){
					model.addElement(key+"---"+slot);
				}
				//System.out.println(key+" "+slot);
			}
			
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//Export Handler
	public static class ValueExportTransferHandler extends TransferHandler {

        public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
        private String value;

        public ValueExportTransferHandler() {
            //this.value = value;
        }

        public String getValue() {
        	value= list.getSelectedValue();
        	String s="";
        	if(value==null) {
        		return value;
        	}
        	for(int i=0;i<value.length();i++){
        		if(value.charAt(i)=='-'&&value.charAt(i+1)=='-'&&value.charAt(i+2)=='-') break;
        		s+=value.charAt(i);
        	}
        	value=s;
        	if(value.equals("CSE-XXX")) value="---";
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
            UpdateCurrentSlotPerWeek();
            InsertIntoList();
            //JOptionPane.showMessageDialog(null, value);
            //model.removeElement(value);
        }

    }

}
