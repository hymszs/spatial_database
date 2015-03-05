
import java.sql.*;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

public class hw2 {
public static Connection getConn() {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost :1521:db";// 
        String username = "SYSMAN";//
        String password = "zhaoshuo";//
        Connection conn = null; //
        try {
            Class.forName(driver);
            // new oracle.jdbc.driver.OracleDriver();
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
	}
	
	
	
	
	 public static void main(String []args) {
       Connection conn=getConn();
       if (args[0].equals("window")){show_window(args[1],args[2],args[3],args[4],args[5],conn);}
       else{
    	   if (args[0].equals("within")){show_within(args[1],args[2],args[3],conn);}
    	   else {
    		    if (args[0].equals("nn")){show_nn(args[1],args[2].substring(1),args[3],conn);} 
    		    else{
    		    	if (args[0].equals("demo")){ demo(Integer.parseInt(args[1]),conn);}	
    		        }
    		    }
    	   }
       }

	
	 public static void show_window(String type,String x1,String y1,String x2,String y2,Connection conn ){
		    String sql="";
		 if(type.equals("building")){  sql = "select bid,bname from building b where b.onfire=0 and SDO_INSIDE(b.shape,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY("+x1+","+y1+","+x2+","+y2+"))) = 'TRUE' order by bid asc" ;}
		    else {if 	(type.equals("firehydrant")){ sql = "select fid from firehydrant f where SDO_INSIDE(f.shape,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY("+x1+","+y1+","+x2+","+y2+"))) = 'TRUE' order by fid asc";}
		          else  {if (type.equals("firebuilding")){sql = "select bid,bname from building b where b.onfire=1 and SDO_INSIDE(b.shape,SDO_GEOMETRY(2003, NULL, NULL,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY("+x1+","+y1+","+x2+","+y2+"))) = 'TRUE' order by bid asc";}
		         }}
		    
		    Statement pstmt;
		    try {
		        pstmt = conn.createStatement();
		       //
		        ResultSet rs = pstmt.executeQuery(sql);
		        if(type.equals("firehydrant"))
		        {
		       while (rs.next()) {
		    	    
		            System.out.println("FID: "+rs.getInt("FID") );
		        
		            
		        }
		        }
		        else{
		        while (rs.next()) {
				    	    
				            System.out.println("BID: "+rs.getInt("BID") );
				            System.out.println("BNAME: "+rs.getString("BNAME") );	
		        	
		        }}
		        rs.close();
		        pstmt.close();}
		        
		  
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
			}
	 
	 
	 public static void show_within(String type,String name,String dis,Connection conn ){
		    String sql="";
		 if(type.equals("building")){  sql = "select b1.bid,b1.bname from building b1,building b2 where b2.bname='"+name+"' and b1.onfire=0 and SDO_WITHIN_DISTANCE(b1.shape,b2.shape,'distance="+dis+"') = 'TRUE' order by bid asc" ;}
		    else {if 	(type.equals("firehydrant")){ sql = "select f.fid from firehydrant f,building b2 where b2.bname='"+name+"' and SDO_WITHIN_DISTANCE(f.shape,b2.shape,'distance="+dis+"') = 'TRUE' order by fid asc";}
		          else  {if (type.equals("firebuilding")){sql = "select b1.bid,b1.bname from building b1,building b2 where b2.bname='"+name+"' and b1.onfire=1 and SDO_WITHIN_DISTANCE(b1.shape,b2.shape,'distance="+dis+"') = 'TRUE' order by bid asc";}
		         }}
		    
		   
		    Statement pstmt;
		    try {
		        pstmt = conn.createStatement();
		       // 
		        ResultSet rs = pstmt.executeQuery(sql);
		        if(type.equals("firehydrant"))
		        {
		       while (rs.next()) {
		    	    
		            System.out.println("FID: "+rs.getInt("FID") );
		        
		            
		        }
		        }
		        else{
		        while (rs.next()) {
				    	    
				            System.out.println("BID: "+rs.getInt("BID") );
				            System.out.println("BNAME: "+rs.getString("BNAME") );	
		        	
		        }}
		        rs.close();
		        pstmt.close();}
		        
		  
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
			}
	 public static void show_nn(String type,String id,String k,Connection conn ){
		    String sql="";
		    
		 if(type.equals("building")){  sql = "select * from (select b1.bid,b1.bname from building b1,building b2 where b2.bid="+id+"and b1.bid!="+id+" and b1.onfire=0 and SDO_NN(b1.shape,b2.shape)='TRUE') where rownum<="+k  ;}
		    else {if 	(type.equals("firehydrant")){ sql = "select f.fid from firehydrant f,building b2 where b2.bid="+id+" and SDO_NN(f.shape,b2.shape,'sdo_num_res="+k+"')='TRUE' " ;}
		          else  {if (type.equals("firebuilding")){sql = "select * from (select b1.bid,b1.bname from building b1,building b2 where  SDO_NN(b1.shape,b2.shape)='TRUE' and b1.onfire=1 and b1.bid!="+id+" and b2.bid="+id+") where rownum <= "+k;}
		         }}
		    
		    Statement pstmt;
		    try {
		        pstmt = conn.createStatement();
		       // 
		        ResultSet rs = pstmt.executeQuery(sql);
		        if(type.equals("firehydrant"))
		        {
		       while (rs.next()) {
		    	    
		            System.out.println("FID: "+rs.getInt("FID") );
		        
		            
		        }
		        }
		        else{
		        while (rs.next()) {
				    	    
				            System.out.println("BID: "+rs.getInt("BID") );
				            System.out.println("BNAME: "+rs.getString("BNAME") );	
		        	
		        }}
		        rs.close();
		        pstmt.close();}
		        
		  
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
			}
	 
	 
	 
	public static void demo1(Connection conn){
		
		String sql = "select * from building b where b.bname like 'S%' and b.onfire=0";	
    Statement pstmt;
    try {
        pstmt = conn.createStatement();
       // 
        ResultSet rs = pstmt.executeQuery(sql);
        
        while (rs.next()) {
            System.out.println("BNAME: "+rs.getString("BNAME") );
        }
        rs.close();
        pstmt.close();
       
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
	}
	
	
	
	public static void demo2(Connection conn){
		String sql = "select b.bname,f.fid from building b,firehydrant f where SDO_NN(f.shape,b.shape,'sdo_num_res=5')='TRUE' and b.onfire=1";	
	    Statement pstmt;
	    try {
	        pstmt = conn.createStatement();
	       // 
	        ResultSet rs = pstmt.executeQuery(sql);
	        
	       while (rs.next()) {
	            System.out.println("BNAME: "+rs.getString("BNAME") );
	            System.out.println("FID: "+rs.getString("FID") );
	            
	        }
	        rs.close();
	        pstmt.close();
	        
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static void demo3(Connection conn){
    String sql = "select f.fid,count(b.bname) from building b,firehydrant f where SDO_WITHIN_DISTANCE(f.shape,b.shape,'distance = 120')='TRUE' group by f.fid having count(b.bname)>= all(select count(b.bname) from building b,firehydrant f where SDO_WITHIN_DISTANCE(f.shape,b.shape,'distance = 120')='TRUE' group by f.fid)";	
    Statement pstmt;
    try {
        pstmt = conn.createStatement();
       // 
        ResultSet rs = pstmt.executeQuery(sql);
        
       while (rs.next()) {
            System.out.println("FID: "+rs.getInt("FID") );
            System.out.println("COUNT(B.BNAME): "+rs.getInt("COUNT(B.BNAME)") );
            
        }
        rs.close();
        pstmt.close();
        
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
		
	}
	public static void demo4(Connection conn){
		String sql = "select * from (select f.fid,count(f.fid) from building b,firehydrant f where SDO_NN(f.shape,b.shape,'sdo_num_res=1')='TRUE'  group by f.fid order by count(f.fid) desc) where rownum<=5";	
	    Statement pstmt;
	    int i;
	    try {
	        pstmt = conn.createStatement();
	       //
	        ResultSet rs = pstmt.executeQuery(sql);
	        
	       
	    	   while(rs.next()){
	            System.out.println("FID: "+rs.getInt("FID") );
	            System.out.println("COUNT(F.FID): "+rs.getInt("COUNT(F.FID)") );
	    	   }  
	       
	        rs.close();
	        pstmt.close();
	        
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static void demo5(Connection conn)
	{
		String sql = "SELECT SDO_AGGR_MBR(b.shape) FROM building b where b.bname like '%HE%'";	
	    Statement pstmt;
	    try {
	        pstmt = conn.createStatement();
	       // 
	        ResultSet rs = pstmt.executeQuery(sql);
	        
	       while (rs.next()) { 
	   	    STRUCT st = (oracle.sql.STRUCT) rs.getObject(1);
	   	     JGeometry j_geom = JGeometry.load(st);
	   	     
	   	    double b[]= j_geom.getMBR();
	   	    
	   	    
	   	    for(int i = 0; i<4;i++){
	   		    	System.out.print((int)b[i]);
	   		    	System.out.print("  ");
	   	    }
	            
	        }
	        rs.close();
	        pstmt.close();
	        
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	
	public static void demo(int num,Connection conn) 
	{ switch(num)
		{case 1: demo1(conn);break;
		case 2: demo2(conn);break;
		case 3: demo3(conn);break;
		case 4: demo4(conn);break;
		case 5: demo5(conn);break;
	    }
	}
}
