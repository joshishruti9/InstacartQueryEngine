import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class LifeCycleServlet extends HttpServlet{

	ServletConfig config = null; 
	  
    // init method 
    public void init(ServletConfig sc) 
    { 
        config = sc; 
        System.out.println("in init"); 
    } 
  
    // service method 
    public void service(ServletRequest req, ServletResponse res) 
        throws ServletException, IOException 
    { 
        res.setContentType("text/html"); 
        PrintWriter pw = res.getWriter(); 
        pw.println("<h2>hello from life cycle servlet</h2>"); 
        System.out.println("in service"); 
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
	
}
