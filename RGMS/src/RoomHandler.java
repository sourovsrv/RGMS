import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

public class RoomHandler {
	private Connection connect=null;
	ConflictCheck objconf;
	static Map<String, Integer> roomidmap = new HashMap<String, Integer>(200);//Mapping RoomID to a unique number
	static int roomstatus[][][]=new int[200][12][12];//To keep status of room
	String roomtype[]= new String[200];//To Keep type
	int roomcapacity[]=new int[200];//To keep capacity
	/**
	 * @param args
	 */
	//Get all the values of room from database and store in different array
	void RoomSearch(){
		roomidmap.clear();
		try{
			String room,type;
			int day,slot,status,capacity,NmbrOfRoom=1;
			String query = "select * from RoomInfo";
			PreparedStatement pst = connect.prepareStatement(query);
			ResultSet rs= pst.executeQuery();
			if(rs==null) return;
			while(rs.next()){
				room="";capacity=0;day=0;slot=0;status=0; type="";
				room = rs.getString("RoomID");
				capacity = rs.getInt("Capacity");
				type = rs.getString("Type");
				day = rs.getInt("Day");
				slot = rs.getInt("Slot");
				status =rs.getInt("Status");
				//System.out.println(room+" "+capacity+" "+type+" "+day+" "+slot+" "+status);
				if(roomidmap.get(room)==null){
					roomidmap.put(room, NmbrOfRoom);//Mapping a unique number to each room
					NmbrOfRoom++;
				}
				int id=roomidmap.get(room);//Accessing room's unique id
				roomcapacity[id]=capacity;
				roomtype[id]=type;
				roomstatus[id][day][slot]=status;
				
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/*private void FillComboBoxRoom(){
		for(String key: roomidmap.keySet()){
			int id=roomidmap.get(key);
			for(int day=1;day<=7;day++){
				for(int slot=1;slot<=6;slot++){
					System.out.println(roomstatus[id][day][slot]);
				}
			}
		}
	}*/
	
	public void DeleteFromMatrix(int day,int slot,int pos){
		try{
			String query = "Update RoutineInfo SET Room = ? where Day= ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, "");
			pst.setString(2, day+"");
			pst.setString(3, slot+"");
			pst.setString(4, pos+"");
			objconf.Matrix[day][slot][pos][2]=null;//2 for room
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void InsertIntoMatrix(int day,int slot,int pos, String room){
		try{
			String query = "Select * from RoutineInfo where Day= ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, day+"");
			pst.setString(2,slot+"");
			pst.setString(3, pos+"");
			ResultSet rs=pst.executeQuery();
			if(rs.next()==true){
				//System.out.println("Null");
				UpdateIntoMatrix(day,slot,pos,room);
			}
			/*else{
				//System.out.println("Check");
				String query2 = "insert into RoutineInfo (Day,Slot,Pos,Room) values(?,?,?,?)";
				PreparedStatement pst2= connect.prepareStatement(query2);
				pst2.setString(1, day+"");
				pst2.setString(2, slot+"");
				pst2.setString(3, pos+"");
				pst2.setString(4, room);
				pst2.execute();
				objconf.Matrix[day][slot][pos][2]=room; //2 for room;
			}*/
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void UpdateIntoMatrix(int day,int slot,int pos, String room){
		try{
			String query = "Update RoutineInfo SET Room = ? where Day= ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= connect.prepareStatement(query);
			pst.setString(1, room);
			pst.setString(2, day+"");
			pst.setString(3, slot+"");
			pst.setString(4, pos+"");
			objconf.Matrix[day][slot][pos][2]=room;//2 for room
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	RoomHandler(){
		connect= DB.connectdb();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RoomHandler rm=new RoomHandler();
		rm.RoomSearch();
		

	}

}
