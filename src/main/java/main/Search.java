package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import been.Asset;
import been.Display;
import been.User;
import dao.Displaydao;
import dao.Userdao;

@WebServlet("/Search")
public class Search extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String assetName		= request.getParameter("assetName");
		String startDateStr		= request.getParameter("startDate");
		String endDateStr		= request.getParameter("endDate");
		String currentuser		= request.getParameter("currentuser");
		String deleteFlagStr	= request.getParameter("deleteFlag"); 		// ← 追加
		String equipmentStatus	= request.getParameter("equipmentStatus");	// ← 2026やり残し対応追加

		// 削除フラグ判定（null or 空ならnullに）
		Boolean deleteFlag = null;
		if (deleteFlagStr != null && !deleteFlagStr.isEmpty()) {
			deleteFlag = Boolean.parseBoolean(deleteFlagStr);
		}

		Date startDate 			= null;
		Date endDate 			= null;
		SimpleDateFormat sdf	= new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (startDateStr != null && !startDateStr.isEmpty()) {
				startDate = sdf.parse(startDateStr);
			}
			if (endDateStr != null && !endDateStr.isEmpty()) {
				endDate = sdf.parse(endDateStr);
			}
			
		    HttpSession session = request.getSession(false);
		    User user = (User) session.getAttribute("user");
		    
		    // ★ ログイン後に location_cd を取得
		    String location_cd = Userdao.getLocation_cd(user.getUser_id());
		    user.setLocation_cd(location_cd);
		    
			// DAOを呼び出し（削除フラグ, ステータスも渡す）
			List<Display> searchList 	= Displaydao.searchDisplay(location_cd, assetName, startDate, endDate, currentuser, deleteFlag, equipmentStatus);
			List<Asset> assetList 		= Displaydao.assetName();

			// 検索結果と資産名リストをセット
			request.setAttribute("displayList", searchList);
			request.setAttribute("assetname", assetList);

			// 検索条件の保持
			request.setAttribute("assetName", assetName);
			request.setAttribute("startDate", startDateStr);
			request.setAttribute("endDate", endDateStr);
			request.setAttribute("currentuser", currentuser);
			request.setAttribute("deleteFlag", deleteFlagStr);
			request.setAttribute("equipmentStatus", equipmentStatus);

			session = request.getSession();
			session.setAttribute("displayList", searchList);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
			rd.forward(request, response);

		} catch (ParseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
