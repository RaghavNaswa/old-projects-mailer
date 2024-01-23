package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReplyMailServlet")
public class ReplyMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		request.getRequestDispatcher("link.html").include(request, response);

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("index.html");
		} else {
			String email = (String) session.getAttribute("email");
			out.print("<span style='float:right'>Hi, " + email + "</span>");

			String msg = (String) request.getAttribute("msg");
			if (msg != null) {
				out.print("<p>" + msg + "</p>");
			}

			int id = Integer.parseInt(request.getParameter("id")); // fetch id from url

			try {
				Connection con = ConProvider.getConnection();
				PreparedStatement ps = con.prepareStatement("select * from company_mailer_message where id=?");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();

				StringBuffer sub = new StringBuffer(rs.getString("subject"));
				sub.insert(0, "Re : ");

				String sender = email;
				String reciever = rs.getString("sender");

				out.println("<div class=inputform style=float:left;>" + "<h2 class=design>Compose Mail Form</h2>"
						+ "<form action=ComposeServletProcess>" + "<table>"
						+ "<tr><td>To:</td><td><input type=text name=to value = " + reciever + "/></td></tr>"
						+ "<tr><td>Subject:</td><td><input type=text name=subject value=" + sub + "/></td></tr>"
						+ "<tr><td colspan=2>Message:</td><td></tr>"
						+ "<tr><td colspan=2><textarea name=message rows=5 cols=30></textarea></td></tr>"
						+ "<tr><td colspan=2><input id=submit type=submit value=Send Mail/></td></tr>" + "</table>"
						+ "</form>" + "</div>");

//				con.close();
			} catch (Exception e) {
				out.print(e);
			}
		}

		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}