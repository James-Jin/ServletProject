/*
 * @(#)IndyListSV.java
 *
 * Copyright (c) 1998 Karl Moss. All Rights Reserved.
 * You may study, use, modify, and distribute this software for any
 * purpose provided that this copyright notice appears in all copies.
 * This software is provided WITHOUT WARRANTY either expressed or
 * implied.
 */

import javax.servlet.*;
import javax.servlet.http.*;

import sql.FinderException;
import symphony.Composer;
import symphony.Composition;
import symphony.Movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>This is a simple servlet that will use JDBC to gather all
 * of the Indy 500 winner  information from a database and format it
 * into an HTML table. This servlet uses HttpSessions to keep
 * track of the position within the ResultSet so that the
 * table can be split into several different pages, each with
 * a 'Next n rows' link.
 */
public class SymphonySVWithDAO extends HttpServlet	{

	/**
	 * <p>Initialize the servlet. This is called once when the
	 * servlet is loaded. It is guaranteed to complete before any
	 * requests are made to the servlet
	 * @param cfg Servlet configuration information
	 */
	public void init(ServletConfig cfg)
			throws ServletException		{
		super.init(cfg);
	}

	/**
	* <p>Destroy the servlet. This is called once when the servlet
	* is unloaded.
	*/
	public void destroy()		{
		super.destroy();
	}

	/**
	 * <p>Performs the HTTP POST operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException		{
		/*	Same as get																	*/
		doGet(req, resp);
	}

	/**
	 * <p>Performs the HTTP GET operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		 throws ServletException, java.io.IOException	{
	 	/*	Get the last year shown on the page that
	 	 *	called us. Remember that we are sorting
	 	 *	the years in descending order.
	 	 */
		String composerToShowStr = req.getParameter("composerToShow");	
		int composerToShow ;
		if (composerToShowStr == null ) {
			composerToShow = 0;
		}else{
			composerToShow = Integer.parseInt(composerToShowStr);
		}
		
		String pageNumberStr = req.getParameter("pageNum");
		int pageNum;
		if (pageNumberStr == null){
			pageNum = 1;
		}else{
			pageNum = Integer.parseInt(pageNumberStr);
		}

		/*	Get the URI, Set the content type, and create a PrintWriter		*/
		String uri = req.getRequestURI();
		resp.setContentType("text/html");
		java.io.PrintWriter out = new java.io.PrintWriter(resp.getOutputStream());

		/*	Print the HTML header														*/
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Symphony Composition List</title>");
		out.println("</head>");
		out.println("<h2><center>");
		out.println("Composition List");
		out.println("</center></h2>");
		out.println("<br>");

		/*	Create any addition properties necessary for connecting
		 *	to the database, such as user and password
		 */

		try	{
			
			boolean moreComposer = true;
			ArrayList<Composer> composerList = Composer.findAll();
			Composer composer = composerList.get(composerToShow);
			composerToShow++;
			if(composerToShow >= composerList.size()){
				moreComposer = false;
			}
				String composerName = composer.getComposerName();

				out.println("<p><center>Composer: " + composerName+"</center></p>");
			//Collection<Composition> compositionList = Composition.findAll();
			Collection<Composition> compositionList = Composition.findByComposerName(composerName);
			boolean moreRows = formatTable(compositionList,  out,  uri, pageNum);
			
			if(moreComposer){
				out.println("<form method=POST action=\"" + uri + "\">");
				out.println("<center>");
				out.println("<input type=submit value=\"Next Composer\">");
				out.println("</center>");

				/* Page was filled. Put in the last year that we saw						*/
				out.println("<input type=hidden name=composerToShow value=" + composerToShow + ">");
				out.println("</form>");
			}else{
				out.println("<p><center>Last Composer</center></p>");
			}
			
			if(moreRows){
				pageNum++;
				
				out.println("<form method=POST action=\"" + uri + "\">");
				//out.println("<center>");
				out.println("<input type=submit value=\"Next 10 rows\">");
				//out.println("</center>");

				/* Page was filled. Put in the last year that we saw						*/
				out.println("<input type=hidden name=pageNum value=" + pageNum + ">");
				out.println("</form>");
			}
			
			
		} catch (FinderException fe)	{
			fe.printStackTrace(out);
		} catch (Exception ex)	{
			ex.printStackTrace(out);
	}

		
		/*	Wrap up																		*/
		out.println("</html>");
		out.flush();
		out.close();
	}


	/**
	 * <p>Given a list of indy winners, format them into an HTML table
	 * @param list	Collection of Indy Winners
	 * @param out PrintWriter to use to output the table
	 * @param uri Requesting URI
	 * @return true if has more pages to show
	 */
	private boolean formatTable(Collection<Composition> list,
									java.io.PrintWriter out,
									String uri,
									int pageNum)
			throws Exception		{

		int rowToStart = (pageNum-1)*10;
		int rowToEnd = pageNum *10;
		int rowCounter = 0;

		/*	Keep track of the last year found		
		 * 
		 * 										*/


		/*	Create the table																		*/
		out.println("<center><table border>");

		/*	Start the table row																	*/
		out.println("<tr>");

		/*	Create each table header. Note that the column index is 1-based	*/
		out.println("<th>" + "Composition" + "</th>");
		out.println("<th>" + "Movement" + "</th>");
		out.println("</tr>");
	  

	  for (Composition composition : list)	{
		  

		  	if(rowCounter>=rowToStart && rowCounter < rowToEnd){
				/* Start a table row																	*/
				out.println("<tr>");
				out.println("<td>" + composition.getCompositionName() + "</td>");
				out.println("<td>" + "</td>");
				out.println("</tr>");
		  	}else if(rowCounter >= rowToEnd){
		  		return true;
		  	}else{
		  		//do nothing
		  	}
		  	rowCounter++;
			
			String compositionName = composition.getCompositionName();
			  
			Collection<Movement> movementList = Movement.findByCompositionName(compositionName);

			for(Movement movement: movementList){
			  	if(rowCounter>=rowToStart && rowCounter < rowToEnd){
					/* Start a table row																	*/
					out.println("<tr>");
					out.println("<td>"  + "</td>");
					out.println("<td>" + movement.getMovementName() + "</td>");
					out.println("</tr>");
			  	}else if(rowCounter >= rowToEnd){
			  		return true;
			  	}else{
			  		//do nothing
			  	}
			  	rowCounter++;

			}
			
	  
	  }

		/*	End the table																			*/
		out.println("</table></center>");
		return false;
	}

}