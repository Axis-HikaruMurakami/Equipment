package main;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AssetDao;
import dao.EquipmentDao;
import logic.EquipmentStatusValidator;

@WebServlet("/AddEquipment")
public class AddEquipment extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//資産分類
		String 	assetName	= request.getParameter("assetName");
		Integer assetNumber = AssetDao.getAssetNumber(assetName);
		
		// 入力値の取得
		String maker				= request.getParameter("maker");
		String model				= request.getParameter("model");
		String type					= request.getParameter("type");
		String serialnumber			= request.getParameter("serialnumber");
		String sp					= request.getParameter("sp");
		String macAd				= request.getParameter("macad");
		String purchaseDateStr		= request.getParameter("purchase_date");
		String purchasePriceStr		= request.getParameter("purchase_price");
		String currentuser			= request.getParameter("currentuser");
		String location				= request.getParameter("location");
		String previousUser			= request.getParameter("previous_user");
		String startDateStr			= request.getParameter("start_date");
		String appCompletionDateStr	= request.getParameter("app_completion_date");
		String equipmentStatus 		= request.getParameter("equipmentStatus");
		String notes				= request.getParameter("other");
		
		
		//資産分類が読み取れなかった場合
		if (assetNumber == null) {
			request.setAttribute("error", "資産分類が正しく選択されていません。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		//使用場所未入力チェック
		if (location == null || location.isEmpty()) {
		    request.setAttribute("error", "使用場所は必須項目です。");
		    request.getRequestDispatcher("/WEB-INF/update.jsp").forward(request, response);
		    return;
		}
		
		
		int		seq 			= EquipmentDao.maxEquipmentId(location, assetNumber);
		String	equipmentId 	= EquipmentDao.generateEquipmentIdPrefix(location, assetNumber)
							  	+ String.format("%03d", seq);
		
		
		// priceを数値に変換
		int purchasePrice = 0;
		if (purchasePriceStr != null && !purchasePriceStr.isEmpty()) {
			purchasePrice = Integer.parseInt(purchasePriceStr);
		}

		// 文字列を Date 型に変換する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.sql.Date purchaseDate      = null;
		java.sql.Date startDate         = null;
		java.sql.Date appCompletionDate = null;

		try {
			
			if (purchaseDateStr != null && !purchaseDateStr.isEmpty()) {
				purchaseDate = new java.sql.Date(sdf.parse(purchaseDateStr).getTime());
			}
			if (startDateStr != null && !startDateStr.isEmpty()) {
				startDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
			}
			if (appCompletionDateStr != null && !appCompletionDateStr.isEmpty()) {
				appCompletionDate = new java.sql.Date(sdf.parse(appCompletionDateStr).getTime());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "日付のフォーマットが正しくありません。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}


		if (!EquipmentStatusValidator.isValid(equipmentStatus)) {
		    request.setAttribute("error", "使用状況が不正です。");
		    request.getRequestDispatcher("/error.jsp").forward(request, response);
		    return;
		}

		// DAOで登録
		EquipmentDao.insertEquipment(equipmentId, assetNumber, maker, model, type, serialnumber, sp,
				macAd, purchaseDate, purchasePrice, currentuser, location, previousUser,
				startDate, appCompletionDate, equipmentStatus, notes);
		
		// main.jsp にリダイレクト
		response.sendRedirect("/Equipment/Home");
	}
}
