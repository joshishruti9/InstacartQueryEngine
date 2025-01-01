import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet2 extends HttpServlet{
	
	ServletConfig config = null; 
	  
    // init method 
    public void init(ServletConfig sc) 
    { 
        config = sc; 
        System.out.println("in init"); 
    } 
  
    // service method 
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException  
    { 
    	PrintWriter pw = null;
    	String someMessage = "Wrong Input";
        res.setContentType("text/html"); 
        pw = res.getWriter();
        //pw.println("<h2>hello from life cycle servlet</h2>"); 
        System.out.println("in service");
        String q = req.getParameter("query");
        //System.out.println(q);
        String nameOfDatabase = req.getParameter("rdbms");
        System.out.println(q+" "+nameOfDatabase);
        try {
			ResultSet rs = databaseConnection(nameOfDatabase,q,pw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			res.sendRedirect("http://databaseapp-env.eba-wkwfd7cx.us-west-2.elasticbeanstalk.com/");
		} catch (SQLException e) {
			res.sendRedirect("http://databaseapp-env.eba-wkwfd7cx.us-west-2.elasticbeanstalk.com/");
		}
    	
    	
    } 
  
    // destroy method 
    public void destroy() 
    { 
        System.out.println("in destroy"); 
    } 
    public String getServletInfo() 
    { 
        return "LifeCycleServlet"; 
    } 
    public ServletConfig getServletConfig() 
    { 
        return config; // getServletConfig 
    } 
    
    public ResultSet databaseConnection(String nameOfDatabase, String query,PrintWriter pw) throws SQLException, ClassNotFoundException 
    {
    	Connection conn=null;
    	ResultSet rs=null;
    	
    		if(nameOfDatabase.equals("mysql"))
    		{
    			Class.forName("com.mysql.jdbc.Driver");  
        		conn=DriverManager.getConnection(  
        		"jdbc:mysql://projectdbds.cx5p70csuy86.us-west-2.rds.amazonaws.com:3306/project1DB","project1","Project1");  	
        		//System.out.println("hey");
    		} 	
    		else
    		{
    			System.out.println("Its redshift");
    			Class.forName("com.amazon.redshift.jdbc.Driver");
    			conn = DriverManager.getConnection("jdbc:redshift://redshift-cluster-1.ctd66a7el65m.us-west-2.redshift.amazonaws.com:5439/dev","project1","Project1");
    		} 
    		Statement stmt=conn.createStatement();  
    		//rs=stmt.executeQuery(query);
    		stmt.execute(query);
    		rs = stmt.getResultSet();
    		ResultSetMetaData rsmd = rs.getMetaData();
    		//System.out.println(rsmd.getColumnCount()); 
    		int columnCount = rsmd.getColumnCount();
    		String colNames[] = new String[columnCount];
    		for(int i=0;i<columnCount;i++)
    		{
    			colNames[i]=rsmd.getColumnName(i+1); // index starts with 1
    		}
    		//System.out.println("Hello");
    		//System.out.println(columnCount);
    		int count=0;
    		
    		/*while(rs.next()&&count<=10)  
    		{
    			count=count+1;
    			System.out.println(rs.getString("aisle_id"));
    			System.out.println(rs.getString("aisle"));  
    		}*/
    		
    		count=0;
    		rs.next();

            	//rs.first();
            	pw.println("<html>");
            	pw.println("<head>");
            	pw.println("<style>");
            	pw.println("div{align-items:center;}");
            	pw.println("table{width:auto;}");
            	pw.println("th{border:1px solid red; background-color:white}");
            	pw.println("tr{border:1px solid red; background-color:grey}");
            	pw.println("td{color:white}");
            	pw.println("body{background-color:black;}");
            	pw.println("</style>");
            	pw.println("</head>");
            	pw.println("<body>");
            	pw.println("<div>");
            	pw.println("<table BORDER=1 CELLPADDING=0 CELLSPACING=0>");
            	pw.print("<tr>");
            	for(int i=0;i<columnCount;i++)
            	{
            		//pw.print("<th>Aisle_Id</th><th>Aisle</th>");
            		pw.print("<th>"+colNames[i]+"</th>");
            	}
            	pw.print("</tr>");
            	count=0;
            	do
            	{
            		count++;
            		pw.println("<tr>");
            		for(int j=0;j<columnCount;j++)
            		{
            			pw.print("<td><center>"+rs.getString(colNames[j])+"</center></td>");
            		}
            		pw.print("</tr>");
            	}while(rs.next()/*&&count<=10*/);
            	
    			/*do{
    				count++;
    			    pw.println("<tr><td><center>"+rs.getString("aisle_id")+"</center></td>"
    			            + "<td><center>"+rs.getString("aisle")+"</center></td></tr>");
    			}while(rs.next()&&count<=5);*/
    			
    			pw.println("</table>");
    			pw.println("</div>");
    			pw.println("</body>");
    			pw.println("</html>");
    		
    		conn.close();  
    	
    	return rs;
    }

}


//projectdbds.cx5p70csuy86.us-west-2.rds.amazonaws.com



