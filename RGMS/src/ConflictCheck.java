import java.awt.dnd.DnDConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class ConflictCheck {
	public static String Matrix[][][][]=new String[10][10][10][500];//In per Day,Slot,Position it has Course[1], Room[2], Teacher[3], Batch[4], CourseType[5],Number of student[6], Students....
	ButtonHandler btnhnd = new ButtonHandler();
	
	
	//private static Connection connect;
	
	ConflictCheck(){
		//connect=DB.connectdb();
		createMatrix();//Must Call to create
		FillMatrix();//Filling the matrix from routine info database
		

	}
	
	
	//Given a course position, return true if same batch has another course on same slot
	private String BatchConflict(int day,int slot,int pos){
		int posi;
		String message="";
		String s1= Matrix[day][slot][pos][4],s2="";
		for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
			if(pos!=posi){
				s2=Matrix[day][slot][posi][4];
				if(s1==null||s2==null) continue;
				if(s1.equals(s2)) {
					message+="Two course of same batch on same slot: "+Matrix[day][slot][posi][1]+" "+Matrix[day][slot][pos][1];
					message+="<br>";
				}
			}
		}
		message+="";
		return message;
	}
	//Given a course position, return true if this batch has more than 5 day class in week
	private String BatchTimeInWeek(int day,int slot,int pos){
		int dayi,flag,cou=0,sloti,posi;
		String s1= Matrix[day][slot][pos][4],s2="",message="";
		for(dayi=1;dayi<=btnhnd.totalday;dayi++){
			flag=0;
			for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
				for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
					s2=Matrix[dayi][sloti][posi][4];
					if(s1==null||s2==null) continue;
					if(s1.equals(s2)) {
						//System.out.println(s1+" "+s2);
						flag=1;
					}
				}
			}
			if(flag==1) cou++;
		}
		if(cou>5){
			message+="This batch has more than 5 day class in week";
			message+="<br>";
		}
		return message;
	}
	
	//Return message if more than 30% common percentage of conflict of student in that position
	private String StudentConflict(int day,int slot, int pos){
		int posi;
		String s = Matrix[day][slot][pos][6];
		String message="";
		int nmofstdntpos = Integer.parseInt(s);
		for(posi=1;posi<=Home.TotalNumberOfPos;posi++){//Traveling through every position of that slot
			if(posi!=pos){
				s = Matrix[day][slot][posi][6];
				if(s==null) continue;
				int nmofstdnt = Integer.parseInt(s);
				//System.out.println(s);
				int cou=0;
				for(int i=7;i<nmofstdnt;i++){//Traveling  through every student of loop position
					String s1=Matrix[day][slot][posi][i];
					for(int n=7;n<=nmofstdntpos;n++){//Traveling  through every student of main position
						String poss= Matrix[day][slot][pos][n];
						if(s1.equals(poss)) cou++;
					}
				}
				int result= (cou*100)/nmofstdntpos;
				if(result>=30){
					String course=Matrix[day][slot][posi][1];
					message+="This course has more than 30% common student with course in this slot: "+course;
					message+="<br>";
				}
			}		
		}
		return message;
	}
	//Return the List of Common Student in that position
	private String PerStudentConflict(int day,int slot, int pos){
		int posi;
		String s = Matrix[day][slot][pos][6];
		String message="",listmsgstdnt="";
		int nmofstdntpos = Integer.parseInt(s);
		int MaxCou=0;
		for(posi=1;posi<=Home.TotalNumberOfPos;posi++){//Traveling through every position of that slot
			if(posi!=pos){
				listmsgstdnt="";
				s = Matrix[day][slot][posi][6];
				if(s==null) continue;
				int nmofstdnt = Integer.parseInt(s);
				//System.out.println(s);
				int cou=0;
				for(int i=7;i<nmofstdnt;i++){//Traveling  through every student of loop position
					String s1=Matrix[day][slot][posi][i];
					for(int n=7;n<=nmofstdntpos;n++){//Traveling  through every student of main position
						String poss= Matrix[day][slot][pos][n];
						if(s1.equals(poss)){
							listmsgstdnt+=s1+", ";
						}
					}
				}
				if(listmsgstdnt.length()>1){
					message+="Common Student with "+ Matrix[day][slot][posi][1]+" course "+listmsgstdnt;
					message+="<br>";
				}
			}		
		}
		return message;
	}
	
	
	//Given a course position, return true if same teacher has another course on same slot
	private String TeacherConflict(int day,int slot,int pos){
		int posi;
		String s1= Matrix[day][slot][pos][3];//3 for teacher;
		String s2="";
		String message="";
		//System.out.println(btnhnd.totalpos);
		for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
			//System.out.println(s1+" "+s2);
			if(pos!=posi){
				s2=Matrix[day][slot][posi][3];
				if(s1==null||s2==null) continue;
				if(s1.equals(s2)){
					message+="Two course of same Teacher on same slot: "+Matrix[day][slot][posi][1]+" "+Matrix[day][slot][pos][1];
					message+="<br>";
				}
			}
		}
		return message;
	}
	
	//Given a course position, return true if same batch has more than 3 Slot class in a day
	private String BatchTimeinDay(int day, int slot, int pos){
		int sloti,posi;
		String s1= Matrix[day][slot][pos][4];//4 for Batch;
		String s2="",message="";
		int cou=1;
		for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
			for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
				if(sloti==slot&&posi==pos) continue;
				
				s2=Matrix[day][sloti][posi][4];
				if(s1==null||s2==null) continue;
				if(s1.equals(s2)) cou++;
			}
		}
		if(cou>=4){
			message+="Batch has more than 3 slot in same day: "+s1;
			message+="<br>";
		}
		return message;
	}
	
	//Given a course position, return true if same teacher has more than 3 Slot class in a day
	private String TeacherTimeinDay(int day, int slot, int pos){
		int sloti,posi;
		String s1= Matrix[day][slot][pos][3];//3 for teacher
		String s2="",message="";
		int cou=1;
		for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
			for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
				if(sloti==slot&&posi==pos) continue;
				
				s2=Matrix[day][sloti][posi][3];
				if(s1==null||s2==null) continue;
				if(s1.equals(s2)) cou++;
			}
		}
		if(cou>=4){
			message+="Teacher has more than 3 slot in same day: "+s1;
			message+="<br>";
		}
		return message;
	}
	
	//Given a course position, return true if Room Capacity is smaller than the Number of Student 
	private String RoomCapacityConflict(int day,int slot,int pos){
		String s=Matrix[day][slot][pos][6];//6 for number of student
		String rm=Matrix[day][slot][pos][2];//2 for Room
		String c=Matrix[day][slot][pos][1];
		String message="";
		RoomHandler rmhnd=new RoomHandler();
		if(s!=null&&rm!=null){
			int crsstdnt=Integer.parseInt(s);
			//System.out.println(c+" "+s+" "+rm);
			if(rmhnd.roomidmap.get(rm)==null) return message;
			 int rmstdnt=rmhnd.roomcapacity[rmhnd.roomidmap.get(rm)];
			 if(rmstdnt<crsstdnt){
				 message+="Room capacity exceeds: Room Capacity: "+rmstdnt+" Course Student: "+crsstdnt;
				 message+="<br>";
			 }
			 //System.out.println(c+" "+s+" "+rm+" "+rmstdnt);
			
		}
		return message;
	}
	
	//Given a course position, return if it is theory, red, if it is lab, should be consecutive
	private String CourseTypeConflict(int day, int slot, int pos){
		int sloti,posi;
		String s1= Matrix[day][slot][pos][1];//1 for Course ;
		String type= Matrix[day][slot][pos][5];//5 for Course Type
		String s2="",message="";
		for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
			for(posi=1;posi<=Home.TotalNumberOfPos;posi++){
				if(sloti==slot&&posi==pos) continue;
				
				s2=Matrix[day][sloti][posi][1];
				if(s1==null||s2==null) continue;
				if(s1.equals(s2)){
					if(type.equals("Theory")){
						message+="Two slot of Theory is in same day: "+s1;
						message+="<br>";
					}
					else{
						if(Math.abs(slot-sloti)!=1){
							message+="Two slot of Lab should be Consecutive: "+s1;
							message+="<br>";
						}
					}
				}
			}
		}
		return message;
	}
	//Given a course Position, return if the associated room is theroy
	String RoomAndCourseTypeConflict(int day,int slot,int pos){
		String message="";
		String coursetype=Matrix[day][slot][pos][5];// 5 for course type
		String room= Matrix[day][slot][pos][2]; // 2 for Room
		
		
		RoomHandler rmhnd=new RoomHandler();
		if(coursetype!=null&&room!=null){
			if(rmhnd.roomidmap.get(room)==null) return message;
			String roomtype=rmhnd.roomtype[rmhnd.roomidmap.get(room)];
			 //System.out.println(Matrix[day][slot][pos][1]+" "+coursetype+" "+room+" "+roomtype);
			 if(roomtype!=null)
			 if(coursetype.equals("Lab")&&roomtype.equals("Theory")){
				 message+="Room Type: Theory, Coure Type: Lab";
				 message+="<br>";
			 }
		}
		
		return message;
	}
	//Fill with student for a particular course
	void FillWithStudent(int day,int slot,int pos,String course){
		try{
			int cou=7;//
			String query = "Select StudentID from StudentInfo where CourseID = ? ";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, course);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				String s=rs.getString("StudentID");
				Matrix[day][slot][pos][cou++]=s;
				//System.out.print(s+" ");
			}
			Matrix[day][slot][pos][6]=cou+"";
			rs.close();
			pst.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	void FillWithTeacherAndBatchAndType(int day,int slot, int pos, String course)
	{
		try{
			String query = "Select TeacherID, Batch,Type from CourseInfo where CourseID= ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, course);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				String teacher = rs.getString("TeacherID");
				Matrix[day][slot][pos][3]=teacher; //3 for Teacher;
				String batch = rs.getString("Batch");
				Matrix[day][slot][pos][4]=batch; //4 for Batch;
				String type = rs.getString("Type");
				Matrix[day][slot][pos][5]= type; // 5 for Course Type
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void InsertIntoMatrix(int day, int slot, int pos, String course)
	{
		try{
			String query = "insert into RoutineInfo (Day,Slot,Pos,CourseID) values(?,?,?,?)";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, day+"");
			pst.setString(2, slot+"");
			pst.setString(3, pos+"");
			pst.setString(4, course);
			pst.execute();
			//FillWithTeacherAndBatch(day,slot,pos,course);
			Matrix[day][slot][pos][1]=course; //1 for course;
			FillWithTeacherAndBatchAndType(day,slot,pos,course);
			FillWithStudent(day,slot,pos,course);
			
			
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}	
	}
	
	//Update into Database
	public void UpdateInRoutine(int day, int slot,int pos, String course ){
		try{
			String query = "Update RoutineInfo SET CourseID = ? where Day= ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, course);
			pst.setString(2, day+"");
			pst.setString(3, slot+"");
			pst.setString(4, pos+"");
			Matrix[day][slot][pos][1]=course;//1 for course
			FillWithTeacherAndBatchAndType(day,slot,pos,course);
			FillWithStudent(day,slot,pos,course);
			pst.execute();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//Delete from Database
	public void DeleteFromRoutine(int day, int slot,int pos){
		try{
			String query = "Delete from RoutineInfo where Day = ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1, day+"");
			pst.setString(2,slot+"");
			pst.setString(3,pos+"");
			pst.execute();
			//System.out.println(day+" "+slot+" "+pos);
			for(int i=1;i<=20;i++){
				Matrix[day][slot][pos][i]=null;
			}
			
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Delete Based on position from Database
	public void DeleteFromRoutineOnPos(int pos,int totalslot){
		try{
			String query = "Delete from RoutineInfo where Pos = ?";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			pst.setString(1,pos+"");
			pst.execute();
			
			
			int posi,dayi,sloti,i;
			//Setting all the value of day and slot overthe total pos to null
			for(dayi=1;dayi<=btnhnd.totalday;dayi++){
				for(sloti=1;sloti<=totalslot;sloti++){
					//System.out.println(Matrix[dayi][sloti][pos][1]);
					for(i=1;i<=20;i++){ 
						Matrix[dayi][sloti][pos][i]=null;
					}
				}
			}	
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	void FillMatrix(){
		try{
			String query = "Select Day, Slot, Pos, RoutineInfo.CourseID, Room, Batch, Type,TeacherID from RoutineInfo, CourseInfo where RoutineInfo.[CourseID] = CourseInfo.[CourseID]";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				int day =rs.getInt("Day");
				int slot =rs.getInt("Slot");
				int pos = rs.getInt("Pos");
				String course =rs.getString("CourseID");
				String room = rs.getString("Room");
				String teacher = rs.getString("TeacherID");
				String batch = rs.getString("Batch");
				String type = rs.getString("Type");
				
				Matrix[day][slot][pos][1] = course;//CourseID
				Matrix[day][slot][pos][2] = room;//Room
				Matrix[day][slot][pos][3] = teacher;//Teacher
				Matrix[day][slot][pos][4] = batch;//batch
				Matrix[day][slot][pos][5] = type; //Course type
				//System.out.println(day + " "+ slot+ " "+pos+" "+course+ " "+room+" "+teacher+" "+batch+" "+type);
				FillWithStudent(day,slot,pos,course);
			}
			rs.close();
			pst.close();
		} catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	private void createMatrix()
	{
		
		int dayi,posi, sloti, lpi;
		for(dayi=1;dayi<=btnhnd.totalday+2;dayi++){
			for(posi=1;posi<=Home.TotalNumberOfPos+2;posi++){
				for(sloti=1;sloti<=Home.TotalNumberOfSlot;sloti++){
					for(lpi=1;lpi<=400;lpi++){
						Matrix[dayi][sloti][posi][lpi]=null;
					}
				}
			}
		}
	}
	
	public String CheckAllRedConflict(int day,int slot, int pos){
		String RedConflictMessage="";
		String receiveconflict="";
		receiveconflict=BatchConflict(day,slot,pos);
		RedConflictMessage+=receiveconflict;
		receiveconflict=TeacherConflict(day,slot,pos);
		RedConflictMessage+=receiveconflict;
		receiveconflict=StudentConflict(day, slot, pos);
		RedConflictMessage+=receiveconflict;
		receiveconflict=CourseTypeConflict(day,slot,pos);
		RedConflictMessage+=receiveconflict;
		return RedConflictMessage;
	}
	public String CheckAllYellowConflict(int day,int slot,int pos){
		String YellowConflictMessage="";
		String receiveconflict="";
		receiveconflict=BatchTimeInWeek(day,slot,pos);
		YellowConflictMessage+=receiveconflict;
		receiveconflict=RoomCapacityConflict(day,slot,pos);
		YellowConflictMessage+=receiveconflict;
		receiveconflict=RoomAndCourseTypeConflict(day,slot,pos);
		YellowConflictMessage+=receiveconflict;
		return YellowConflictMessage;
	}
	public String CheckAllCyanConflict(int day,int slot,int pos){
		String CyanConflictMessage="";
		String receiveconflict="";
		receiveconflict=BatchTimeinDay(day, slot, pos);
		CyanConflictMessage+=receiveconflict;
		receiveconflict=TeacherTimeinDay(day, slot, pos);
		CyanConflictMessage+=receiveconflict;
		receiveconflict=TeacherTimeinDay(day, slot, pos);
		CyanConflictMessage+=receiveconflict;
		receiveconflict=PerStudentConflict(day, slot, pos);
		CyanConflictMessage+=receiveconflict;
		return CyanConflictMessage;
	}
	public static void main(String[] args){
		ConflictCheck objconf2 = new ConflictCheck();
		int n= 0;
		//n=objconf2.CheckAllRedConflict(1,3,1);
		//n=objconf2.TeacherConflict(1, 3, 1);
		//System.out.println(n);
		//objconf.DeleteFromRoutine(1, 1, 1);
		//objconf.InsertIntoMatrix(1,1,1,"CSE-400");
		//objconf.UpdateInRoutine(1, 1, 1, "CSE-441");
		/*System.out.println(objconf2.Matrix[1][1][1][1]+" "+objconf2.Matrix[1][1][1][2]+" "+objconf2.Matrix[1][1][1][3]+" "+objconf2.Matrix[1][1][1][4]+" "+objconf2.Matrix[1][1][1][5]+" ");
		String s = objconf2.Matrix[1][1][1][5];
		int nmofstdnt = Integer.parseInt(s);
		for(n=6;n<nmofstdnt;n++)
		{
			String s1=objconf2.Matrix[1][1][1][n];
			System.out.print(s1+" ");
		}*/
	}
	
	
	
	private static ResultSet FindCourses(int day,int slot,int pos){
		try{
			String query = "Select * from RoutineInfo where Day = ? AND Slot = ? AND Pos = ?";
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, day+""); pst.setString(2, slot+""); pst.setString(3, pos+"");
			ResultSet rs = pst.executeQuery();
			return rs;
		} catch(Exception e){
			return null;
		}
	}
	
	//Given a course position, return true if same batch has another course on same slot
	private static int BatchConflictfromdb(int day, int slot, int pos){
		//connect = DB.connectdb();
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
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Batch1= rs.getString("Batch");
			}
			//Finding all other course of that slot
			String query2 = "SELECT CourseID from RoutineInfo where Day = ? AND Slot = ? AND Not(Pos) = ?";
			PreparedStatement pst2 = Home.connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, slot+""); pst2.setString(3, pos+"");
			
			ResultSet rs2= pst2.executeQuery();
			//Matching batch of other courses with given course
			while(rs2.next()){
				Course2= rs2.getString("CourseID");
				String query3 = "Select Batch from CourseInfo where CourseID = '"+Course2+"' ";
				PreparedStatement pst3 = Home.connect.prepareStatement(query3);
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
	private static int StudentConflictfromdb(int day, int slot, int pos1, int pos2){
		//connect = DB.connectdb();
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
			PreparedStatement pst3 = Home.connect.prepareStatement(query3);
			pst3.setString(1,Course1);
			ResultSet rs3= pst3.executeQuery();
			String student1[] = new String[1000];
			int i=0,cou=0,ln=0;
			while(rs3.next()){
				student1[ln++]=rs3.getString("StudentID");
			}
			
			//Select all student from Course2
			String query4 = "Select StudentID from StudentInfo where CourseID = ?";
			PreparedStatement pst4 = Home.connect.prepareStatement(query4);
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
	private static int TeacherConflictffromdb(int day, int slot, int pos){
		//connect = DB.connectdb();
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
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Teacher1= rs.getString("TeacherID");
			}
			//Finding all other course of that slot
			String query2 = "SELECT CourseID from RoutineInfo where Day = ? AND Slot = ? AND Not(Pos) = ?";
			PreparedStatement pst2 = Home.connect.prepareStatement(query2);
			pst2.setString(1, day+""); pst2.setString(2, slot+""); pst2.setString(3, pos+"");
			
			ResultSet rs2= pst2.executeQuery();
			//Matching teacher of other courses with given course
			while(rs2.next()){
				Course2= rs2.getString("CourseID");
				String query3 = "Select TeacherID from CourseInfo where CourseID = '"+Course2+"' ";
				PreparedStatement pst3 = Home.connect.prepareStatement(query3);
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
	private static int BatchTimeinDayfromdb(int day, int slot, int pos){
		//connect = DB.connectdb();
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
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Batch1= rs.getString("Batch");
			}
			//Finding all other course of that slot
			String query2 = "SELECT Count(Slot) from RoutineInfo, CourseInfo where RoutineInfo.Day = ? AND CourseInfo.CourseID = RoutineInfo.CourseID AND CourseInfo.Batch = ?";
			PreparedStatement pst2 = Home.connect.prepareStatement(query2);
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
	private static int TeacherTimeinDayfromdb(int day, int slot, int pos){
		//connect = DB.connectdb();
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
			PreparedStatement pst = Home.connect.prepareStatement(query);
			pst.setString(1, Course1+"");		;
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()){
				Teacher1= rs.getString("TeacherID");
			}
			//Finding all other course of that slot
			String query2 = "SELECT Count(Slot) from RoutineInfo, CourseInfo where RoutineInfo.Day = ? AND CourseInfo.CourseID = RoutineInfo.CourseID AND CourseInfo.TeacherID = ?";
			PreparedStatement pst2 = Home.connect.prepareStatement(query2);
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
	
	/*public static int NumberOfPosQuery(){
		int pos=2;
		try{
			//connect = DB.connectdb();
			String query="Select Pos from RoutineInfo";
			PreparedStatement pst= Home.connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs!=null){
				while(rs.next()){
					String s=rs.getString("Pos");
					int nw=Integer.parseInt(s);
					if(pos<nw)
					pos=nw;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pos;
	}*/
}
