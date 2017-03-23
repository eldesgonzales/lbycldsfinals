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

import net.tutorial.beans.File;
import net.tutorial.beans.User;
import net.tutorial.utilities.DBService;
import net.tutorial.utilities.UserService;
import net.tutorial.utilities.FileService;

@WebServlet(urlPatterns={"/", "/home", "/register", "/login", "/logout", "/delete", "/files", "/upload"})
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
		boolean verified = false;
/*		String param = req.getParameter("action");
		String id = req.getParameter("id");
		String viewName = "index";
		*/
		User u = new User();
		File f = new File();
		UserService userServe = new UserService();
		FileService fileServe = new FileService();
/*
		if (param != null && param.equals("new")) {
			viewName = "contact";
		} else if (param != null && param.equals("edit")) {

			viewName = "contact";
			db = DBService.getInstance();
			req.setAttribute("document", db.findRecord(Integer.parseInt(id)));

		} else {

			db = DBService.getInstance();

			if (param != null && id != null && param.equals("delete")) {
				db.deleteRecord(Integer.parseInt(id));
			}

			req.setAttribute("contacts", db.allRecords());
		}*/

		switch (action){
			case "/":
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
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));

				int accountid = userServe.addUser(u);
				
				u = userServe.displayUser(accountid);
				u.getUserid();
				session.setAttribute("member", u);
				
				break;
			case "/login":			
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));

				User u2 = userServe.checkLogin(u,0);
				if (u2.getUsername() != null){
					verified = true;
					u = userServe.displayUser(u2.getUserid());
					session.setAttribute("member", u2);
				}
				
				pw.write(String.valueOf(verified));
				break;
			case "/delete":
				u = userServe.checkLogin((User) session.getAttribute("member"),1);
				System.out.println(u);
				
				if (u != null){
					fileServe.deleteFile(req.getParameter("id"));
					
					pw.write("success");
					//getServletContext().getRequestDispatcher("/WEB-INF/views/panel.jsp").forward(req, resp);					
				} else
					pw.write("fail");
				break;
			case "/upload":
				u = userServe.checkLogin((User) session.getAttribute("member"),1);
				
				if (u != null){				
					f.setFilename(req.getParameter("filename"));
					f.setUserId(u.getUserid());
					
					fileServe.addFile(f);
					
					getServletContext().getRequestDispatcher("/files").forward(req, resp);	
					
					pw.write("success");
				} else
					pw.write("fail");
				break;
			case "/files":
				u = userServe.checkLogin((User) session.getAttribute("member"),1);
				
				if (u != null){
					ArrayList<File> fileList = fileServe.displayFiles(u.getUserid());
					
					req.setAttribute("files", fileList);
					getServletContext().getRequestDispatcher("/WEB-INF/views/panel.jsp").forward(req, resp);
				} else
					getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
				break;
			case "/logout":
				req.getSession(false).invalidate();
				getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
				
				break;//*/
		}
		//getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);		
		
		
/*		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");

		Map<String, Object> record = new HashMap<String, Object>();
		DBService db = DBService.getInstance();

		record.put("name", name);
		record.put("email", email);
		record.put("mobile", mobile);

		resp.sendRedirect("home");*/
	}

}
