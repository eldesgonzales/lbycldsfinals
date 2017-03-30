package net.tutorial.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import net.tutorial.beans.User;
import net.tutorial.utilities.DBService;
import net.tutorial.utilities.ObjectStorageService;
import net.tutorial.utilities.UserService;

@WebServlet({"", "/home", "/register", "/login", "/logout", "/delete", "/files", "/upload", "/download"})
@MultipartConfig
public class MainController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RequestDispatcher dispatcher;
	DBService db = null;
	ObjectStorageService os;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String action = req.getServletPath();
	
		Gson gson = new Gson();
		String out = "";

		User u = new User();
		UserService userServe = new UserService();
		
		db = DBService.getInstance();
		this.os = new ObjectStorageService();

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
				boolean notUnique = userServe.checkExists(req.getParameter("username")); // unique sya
				PrintWriter pw = resp.getWriter();
				
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));

				if (!notUnique) {
					int accountid = userServe.addUser(u);
					
					u = userServe.displayUser(accountid);
					u.getUserid();
					session.setAttribute("member", u);
				}
				
				out = gson.toJson(notUnique);
				pw.write(out);	
				pw.close();
				break;
			case "/login":						
				u.setUsername(req.getParameter("username"));
				u.setPassword(req.getParameter("password"));
				pw = resp.getWriter();

				User u2 = userServe.checkLogin(u);
				if (u2 != null){
					u = userServe.displayUser(u2.getUserid());
					session.setAttribute("member", u);
					
					out = gson.toJson(u);
				} else
					out = gson.toJson(null);
				
				pw.write(out);
				pw.close();
				break;
			case "/files":
				u = (User) session.getAttribute("member");		
				List<String> documents = this.os.getDocumentList("rainfall");
				
				req.setAttribute("files", documents);
				getServletContext().getRequestDispatcher("/WEB-INF/views/panel.jsp").forward(req, resp);
				break;				
			case "/delete":
				u = (User) session.getAttribute("member");
			
				String fileName = req.getParameter("id");
				this.os.deleteFile("rainfall", fileName);
				
				req.getRequestDispatcher("/files").forward(req, resp);

				break;
			case "/upload":
				u = (User) session.getAttribute("member");
				
				final Part filePart = req.getPart("file");
				this.os = new ObjectStorageService();
				this.os.uploadFile("rainfall", filePart);
				
				getServletContext().getRequestDispatcher("/files").forward(req, resp);
				break;
			case "/download":
				u = (User) session.getAttribute("member");
				fileName = req.getParameter("id");
				
				ServletOutputStream out2 = resp.getOutputStream();
				InputStream in = this.os.downloadFile("rainfall", fileName);
				resp.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out2.write(buffer, 0, length);
				}
				out2.close();
				in.close();
				
				break;				
			case "/logout":
				//req.getSession(false)
				session.invalidate();
				getServletContext().getRequestDispatcher("/home").forward(req, resp);
				
				break;
		}
	}

}
