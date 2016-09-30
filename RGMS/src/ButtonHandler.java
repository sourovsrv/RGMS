import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;



public class ButtonHandler {
	public JPanel subcontentPane;
	public JScrollPane scrollPane;
	public int totalday=7;
	public int totalslot=7;
	public int totalpos=7;
	
	JComboBox boxRoom[][][] = new JComboBox[10][10][10];
    JButton btn[][][] = new JButton[10][10][10];
    JLabel lblTeacher[][][] = new JLabel[10][10][10];
    JLabel Day[]=new JLabel[10];
    JLabel slotlbl[] = new JLabel[10];
 
    
	
	public void ButtonHandler(){
	}
	
	public void SubPanelCreator(){
		
		//SubcontentPane
		subcontentPane = new JPanel();
		subcontentPane.setLayout(null);
		subcontentPane.setPreferredSize(new Dimension(1700,1500));
		
		//ScrollPane
		scrollPane = new JScrollPane();;
        scrollPane.setViewportView(subcontentPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(120, 30, 1200, 650);
        
        //Add Day Label
        
        
        
        CreateButton();        
        
	}
	public void CreateButton(){
		int dayi,posi,sloti,pos=0;
		int boxleft=40,boxup=35;
		int slotlblpos=130;
		for(sloti=1;sloti<=totalslot;sloti++){
			slotlbl[sloti] = new JLabel("Slot-"+sloti);
			slotlbl[sloti].setBounds(slotlblpos, 5, 50, 25);
			subcontentPane.add(slotlbl[sloti]);
			slotlblpos+=240;
		}
		
        for(dayi=1;dayi<=totalday;dayi++){
            Day[dayi] = new JLabel("Day-"+dayi);
        	Day[dayi].setBounds(0, boxup+30, 35, 25);
            subcontentPane.add(Day[dayi]);
        	for(posi=1;posi<=totalpos;posi++){
        		for(sloti=1;sloti<=totalslot;sloti++){
        			boxRoom[dayi][sloti][posi] = new JComboBox();
                    boxRoom[dayi][sloti][posi].addItem("Com.Lab");
            		boxRoom[dayi][sloti][posi].setBounds(boxleft+5, boxup, 70, 25);
            		subcontentPane.add(boxRoom[dayi][sloti][posi]);
                    
                    btn[dayi][sloti][posi] = new JButton(dayi+"-XXX-"+sloti);
                    btn[dayi][sloti][posi].setBounds(boxleft+80, boxup, 100, 25);
                    btn[dayi][sloti][posi].setTransferHandler(new ValueImportTransferHandler());
                    subcontentPane.add(btn[dayi][sloti][posi]);
                    
                    
                    lblTeacher[dayi][sloti][posi] = new JLabel("TTT");
            		lblTeacher[dayi][sloti][posi].setFont(new Font("Tahoma",Font.PLAIN,14));
            		lblTeacher[dayi][sloti][posi].setBounds(boxleft+185,boxup,35,25);
            		subcontentPane.add(lblTeacher[dayi][sloti][posi]);
            		boxleft+=240;
        		}
        		boxleft=40;
        		boxup+=30;
        		
        	}
        	boxup+=20;
        	
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



}