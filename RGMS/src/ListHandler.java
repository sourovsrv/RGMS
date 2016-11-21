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
	static Connection connect;
	
	ListHandler(){
		connect = DB.connectdb();
		
		
		scrollPaneList = new JScrollPane();;
        scrollPaneList.setViewportView(list);
        //scrollPaneList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneList.setBounds(5, 25, 100, 600);
		
		list.setPreferredSize(new Dimension(80,2000));
        //list.setBounds(5, 25, 100, 600);
		InsertintoList();
		
		
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
	
	
	//Filling List with Courses From CourseInfo Table
	public void InsertintoList(){
		try{
			model.removeAllElements();
			String s="CSE-XXX";
			model.addElement(s);
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
	
	//Export Handler
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
