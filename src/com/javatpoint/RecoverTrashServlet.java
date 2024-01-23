package com.javatpoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RecoverTrashServlet")
public class RecoverTrashServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// request.getRequestDispatcher("header.html").include(request, response);
		// request.getRequestDispatcher("link.html").include(request, response);

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("index.html");
		} else {
			// String email = (String) session.getAttribute("email");
			// out.print("<span style='float:right'>Hi, " + email + "</span>");

			int id = Integer.parseInt(request.getParameter("id")); // fetch id from url

			try {
				Connection con = ConProvider.getConnection();
				int i = RecoverTrash.recover(id);
				if (i > 0) {
					request.setAttribute("msg", "Mail successfully recovered!");

					request.getRequestDispatcher("InboxServlet").forward(request, response);
					// request.getRequestDispatcher("TrashServlet").forward(request, response);
				}
				con.close();
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