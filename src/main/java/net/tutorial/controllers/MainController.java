package net.tutorial.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import net.tutorial.beans.File;
import net.tutorial.beans.User;
import net.tutorial.utilities.DBService;

@WebServlet({"", "/home", "/register", "/login", "/logout", "/delete", "/files", "/upload"})
public class MainController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RequestDispatcher dispatcher;
	DBService db = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String action = req.getServletPath();
		PrintWriter pw = resp.getWriter();
				
		Gson gson = new Gson();
		String out = "";

		User u = new User();
		File f = new File();
/*		UserService userServe = new UserService();
		FileService fileServe = new FileService();*/
		
		db = DBService.getInstance();

		switch (action){
			case "":
				if (session.getAttribute("member") == null)
					getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
				else
					getServletContext().getRequestDispatcher("/files").forward(req, resp);
				break;		
			case "/home":
				if (session.getAttribute("member") == null)
					getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
				else
					getServletContext().getRequestDispatcher("/files").forward(req, resp);
				break;
			case "/register":	
				boolean notUnique = db.checkExists(req.getParameter("username")); // unique sya
				
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));

				if (!notUnique) {
					int accountid = db.addUser(u);
					
					u = db.displayUser(accountid);
					u.getUserid();
					session.setAttribute("member", u);
				}
				
				out = gson.toJson(notUnique);
				pw.write(out);	
				break;
			case "/login":						
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));

				User u2 = db.checkLogin(u);
				if (u2 != null){
					u = db.displayUser(u2.getUserid());
					session.setAttribute("member", u);
					
					out = gson.toJson(u);
				} else
					out = gson.toJson(null);
				
				pw.write(out);
				break;
			case "/delete":
				u = (User) session.getAttribute("member");

				System.out.println(u);
				
				db.deleteFile(req.getParameter("id"));

				break;
			case "/upload":
				u = (User) session.getAttribute("member");
				
				f.setFilename(req.getParameter("filename"));
				f.setUserId(u.getUserid());
				
				db.addFile(f);
				
				getServletContext().getRequestDispatcher("/files").forward(req, resp);	
				
				pw.write("success");

				break;
			case "/files":
				u = (User) session.getAttribute("member");
				ArrayList<File> fileList = db.displayFiles(u.getUserid());
				
				req.setAttribute("files", fileList);
				getServletContext().getRequestDispatcher("/WEB-INF/views/panel.jsp").forward(req, resp);
				break;
			case "/logout":
				req.getSession(false).invalidate();
				getServletContext().getRequestDispatcher("home").forward(req, resp);
				
				break;//*/
		}
	}

}
