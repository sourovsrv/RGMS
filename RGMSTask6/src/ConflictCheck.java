import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class ConflictCheck {
	private static Connection connect;
	
	public static ResultSet FindCourses(int day,int slot){
		try{
			String query = "Select * from RoutineInfo where Day = ? AND Slot = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, day+""); pst.setString(2, slot+"");
			ResultSet rs = pst.executeQuery();
			return rs;
		} catch(Exception e){
			return null;
		}
	}
	
	public static ResultSet FindCourses(int day,int slot,int pos){
		try{
			connect = DB.connectdb();
			String query = "Select * from RoutineInfo where Day = ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, day+""); pst.setString(2, slot+""); pst.setString(3, pos+"");
			ResultSet rs = pst.executeQuery();
			return rs;
		} catch(Exception e){
			return null;
		}
	}
	
	//Given a course position, return true if same batch has another course on same slot
	public static int BatchConflict(int day, int slot, int pos){
		connect = DB.connectdb();
		try{
			//Given day,slot and pos, find the batch number
			String Batch1="",Course1="",Course2="",Batch2="";
			int flag=0;
			//Finding batch of given course
			ResultSet rs1= FindCourses(day,slot,pos);
			while(rs1.next()){
				Course1 = rs1.getString("CourseID");
			}
			String query = "Select Batch from CourseInfo where CourseID = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Batch1= rs.getString("Batch");
			}
			//Finding all other course of that slot
			String query2 = "SELECT CourseID from RoutineInfo where Day = ? AND Slot = ? AND Not(Pos) = ?";
			PreparedStatement pst2 = connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, slot+""); pst2.setString(3, pos+"");
			
			ResultSet rs2= pst2.executeQuery();
			//Matching batch of other courses with given course
			while(rs2.next()){
				Course2= rs2.getString("CourseID");
				String query3 = "Select Batch from CourseInfo where CourseID = '"+Course2+"' ";
				PreparedStatement pst3 = connect.prepareStatement(query3);
				ResultSet rs3 = pst3.executeQuery(); 
				
				while(rs3.next()){
					Batch2= rs3.getString("Batch");
					if(Batch2==null) continue;
					if(Batch2.equals(Batch1)) flag=1;
				}
				pst3.close();
			}
			pst.close();
			pst2.close();
			return flag;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}
	
	//Given two position of course find number of same student
	public static int StudentConflict(int day, int slot, int pos1, int pos2){
		connect = DB.connectdb();
		try{
			String Course1= "",Course2="";
			ResultSet rs1 = FindCourses(day,slot,pos1);
			ResultSet rs2 = FindCourses(day,slot,pos2);
			while(rs1.next()){
				Course1=rs1.getString("CourseID");
			}
			while(rs2.next()){
				Course2=rs2.getString("CourseID");
			}
			if(Course1.equals("") || Course2.equals("")) return 0;
			if(Course1.equals(Course2)) return 0;
			
			//Select all student from Course1
			String query3 = "Select StudentID from StudentInfo where CourseID = ?";
			PreparedStatement pst3 = connect.prepareStatement(query3);
			pst3.setString(1,Course1);
			ResultSet rs3= pst3.executeQuery();
			String student1[] = new String[1000];
			int i=0,cou=0,ln=0;
			while(rs3.next()){
				student1[ln++]=rs3.getString("StudentID");
			}
			
			//Select all student from Course2
			String query4 = "Select StudentID from StudentInfo where CourseID = ?";
			PreparedStatement pst4 = connect.prepareStatement(query4);
			pst4.setString(1,Course2);
			ResultSet rs4= pst4.executeQuery();
			
			//Finding number of match
			cou=0;
			String student2="";
			while(rs4.next()){
				student2=rs4.getString("StudentID");
				for(i=0;i<ln;i++){
					if(student1[i].equals(student2)){
						cou++;
					}
				}
			}
			pst3.close();
			pst4.close();
			
			return cou;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}
	
	//Given a course position, return true if same teacher has another course on same slot
	public static int TeacherConflict(int day, int slot, int pos){
		connect = DB.connectdb();
		try{
			//Given day,slot and pos, find the batch number
			String Teacher1="",Course1="",Course2="",Teacher2="";
			int flag=0;
			//Finding batch of given course
			ResultSet rs1= FindCourses(day,slot,pos);
			while(rs1.next()){
				Course1 = rs1.getString("CourseID");
			}
			String query = "Select TeacherID from CourseInfo where CourseID = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Teacher1= rs.getString("TeacherID");
			}
			//Finding all other course of that slot
			String query2 = "SELECT CourseID from RoutineInfo where Day = ? AND Slot = ? AND Not(Pos) = ?";
			PreparedStatement pst2 = connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, slot+""); pst2.setString(3, pos+"");
			
			ResultSet rs2= pst2.executeQuery();
			//Matching teacher of other courses with given course
			while(rs2.next()){
				Course2= rs2.getString("CourseID");
				String query3 = "Select TeacherID from CourseInfo where CourseID = '"+Course2+"' ";
				PreparedStatement pst3 = connect.prepareStatement(query3);
				ResultSet rs3 = pst3.executeQuery(); 
				
				while(rs3.next()){
					Teacher2= rs3.getString("TeacherID");
					if(Teacher2==null) continue;
					if(Teacher2.equals(Teacher1)) flag=1;
				}
				pst3.close();
			}
			pst.close();
			pst2.close();
			return flag;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}
	
	//Given a course position, return true if same batch has more than 3 Slot class in a day
	public static int BatchTimeinDay(int day, int slot, int pos){
		connect = DB.connectdb();
		try{
			//Given day,slot and pos, find the batch number
			String Batch1="",Course1="";
			int flag=0;
			//Finding batch of given course
			ResultSet rs1= FindCourses(day,slot,pos);
			while(rs1.next()){
				Course1 = rs1.getString("CourseID");
			}
			String query = "Select Batch from CourseInfo where CourseID = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Batch1= rs.getString("Batch");
			}
			//Finding all other course of that slot
			String query2 = "SELECT Count(Slot) from RoutineInfo, CourseInfo where RoutineInfo.Day = ? AND CourseInfo.CourseID = RoutineInfo.CourseID AND CourseInfo.Batch = ?";
			PreparedStatement pst2 = connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, Batch1); //pst2.setString(3, pos+"");
			
			ResultSet rs2= pst2.executeQuery();
			while(rs2.next()){
				int numberofslot=0;
				numberofslot=rs2.getInt(1);
				if(numberofslot>=4) flag = 1;
			}
			rs.close();
			rs2.close();
			pst.close();
			pst2.close();

			return flag;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}

	//Given a course position, return true if same Teacher has more than 3 Slot class in a day
	public static int TeacherTimeinDay(int day, int slot, int pos){
		connect = DB.connectdb();
		try{
			//Given day,slot and pos, find the batch number
			String Teacher1="",Course1="";
			int flag=0;
			//Finding batch of given course
			ResultSet rs1= FindCourses(day,slot,pos);
			while(rs1.next()){
				Course1 = rs1.getString("CourseID");
			}
			String query = "Select TeacherID from CourseInfo where CourseID = ?";
			PreparedStatement pst = connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Teacher1= rs.getString("TeacherID");
			}
			//Finding all other course of that slot
			String query2 = "SELECT Count(Slot) from RoutineInfo, CourseInfo where RoutineInfo.Day = ? AND CourseInfo.CourseID = RoutineInfo.CourseID AND CourseInfo.TeacherID = ?";
			PreparedStatement pst2 = connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, Teacher1);
			
			ResultSet rs2= pst2.executeQuery();
			while(rs2.next()){
				int numberofslot=0;
				numberofslot=rs2.getInt(1);
				if(numberofslot>=4) flag = 1;
			}
			rs.close();
			rs2.close();
			pst.close();
			pst2.close();

			return flag;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}	
	
	public static void main(String[] args){
		int n= 0;
		/*n = BatchConflict(1,1,1);
		n = StudentConflict(1,1,1,4);
		n = TeacherConflict(1,1,2);
		n = BatchTimeinDay(1,1,1);
		n = TeacherTimeinDay(1,1,1);
		System.out.println(n);*/
		//ResultSet rs=FindCourses(1,1,1);
		
	}

}
