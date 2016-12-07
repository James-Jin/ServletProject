/*
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
 * of the composer, composition and movement information from a database and format it
 * into an HTML table. This servlet uses HttpSessions to keep
 * track of the position within the ResultSet so that the
 * table can be split into several different pages, each with
 * a 'Next Composer' link (if there is next composer), and a 'Next 10 rows' 
 * (if there are more than 10 rows)
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
	 * <p>Performs the HTTP GET operation.</p>
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		 throws ServletException, java.io.IOException	{
	 	/*	Get the parameter in the request if any
	 	 * 	composerToShow : the next composer to show. it starts from 0. 
	 	 *  	if composerToShow >= composer size, then it's end of the list
	 	 *  pageNumber : the current page number (10 rows per page)
	 	 *  	it starts from 1. eg.: page 1 -> row 0-9
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

		/*	Get next Composer and all the Compositions of this composer. 
		 * 		 */
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
			Collection<Composition> compositionList = Composition.findByComposerName(composerName);
			
			/* print the table, 10 rows maximum, return true if there is more rows */
			boolean moreRows = formatTable(compositionList, out,  uri, pageNum);
			
			/* Show next Composer button, if there is next composer,
			 * 		else print Last Composer */
			if(moreComposer){
				out.println("<form method=POST action=\"" + uri + "\">");
				out.println("<center>");
				out.println("<input type=submit value=\"Next Composer\">");
				out.println("</center>");

				/* Put this next composer to show number in the request	*/
				out.println("<input type=hidden name=composerToShow value=" + composerToShow + ">");
				out.println("</form>");
			}else{
				out.println("<p><center>Last Composer</center></p>");
			}
			
			/* Show a 'Next 10 rows' Button if there is more rows */
			if(moreRows){
				pageNum++;
				
				out.println("<form method=POST action=\"" + uri + "\">");
				out.println("<center>");
				out.println("<input type=submit value=\"Next 10 rows\">");
				out.println("</center>");

				/* Page was filled and there are more rows to show. Put this page number in the request*/
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
	 * <p>Given a list of compositions, get all the Movements in them and 
	 * format them into an HTML table</p>
	 * @param list	Collection of Compositions
	 * @param out PrintWriter to use to output the table
	 * @param uri Requesting URI
	 * @return true if has more pages to show
	 */
	private boolean formatTable(Collection<Composition> list,
									java.io.PrintWriter out,
									String uri,
									int pageNum)
			throws Exception		{

		/*	Keep track of the current page		
		 * 
		 * 										*/
		int rowToStart = (pageNum-1)*10;
		int rowToEnd = pageNum *10; 
		int rowCounter = 0;  // counter in this page, range 0 - 9


		/*	Create the table																		*/
		out.println("<center><table border>");

		/*	Start the table row																	*/
		out.println("<tr>");

		/*	Create each table header. Note that the column index is 1-based	*/
		out.println("<th>" + "Composition" + "</th>");
		out.println("<th>" + "Movement" + "</th>");
		out.println("</tr>");
	  

	/* Print the compositions in table, 
	 * get all the movements and print them in table,
	 * stops once 10 rows are printed
	 * */
	  for (Composition composition : list)	{
		  

		  	if(rowCounter>=rowToStart && rowCounter < rowToEnd){
				/* Start a table row																	*/
				out.println("<tr>");
				out.println("<td>" + composition.getCompositionName() + "</td>");
				out.println("<td>" + "</td>");
				out.println("</tr>");
		  	}else if(rowCounter >= rowToEnd){
				out.println("</table></center>");
		  		return true;
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
					out.println("</table></center>");
			  		return true;
			  	}
			  	rowCounter++;

			}

	  
	  }

		/*	End the table																			*/
		out.println("</table></center>");
		return false;
	}

}