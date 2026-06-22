<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String mode = (String) request.getAttribute("mode");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>備品更新画面</title>
<!-- Bootstrap 5 CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/Equipment/css/style.css">
<style>
body {
	overflow-y: scroll;
	height: 100vh;
	background-color: #f9f9f9;
	font-family: 'Segoe UI', sans-serif;
}

h3 {
	border-bottom: 2px solid #007bff;
	padding-bottom: 8px;
	color: #000;
}
</style>

<!-- jQuery (for easier DOM manipulation) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		$('form').on('keydown', function(e) {
			if (e.key === 'Enter') {
				e.preventDefault();
			}
		});
	});

	function enforceHalfWidthPrintable(el) {
		el.value = el.value.replace(/[^\x21-\x7E]/g, '');
	}
</script>
</head>
<body class="bg-light">

	<div class="container py-4">
		<c:choose>
			<c:when test="${mode == 'edit'}">
				<h3 class="text-center mb-4">備品の更新</h3>
			</c:when>
			<c:otherwise>
				<h3 class="text-center mb-4">備品の登録</h3>
			</c:otherwise>
		</c:choose>
		<div class="card-block">

			<!-- 登録フォーム -->
			<form id="equipmentForm" method="post">

				<c:if test="${mode == 'edit'}">
					<input type="hidden" name="equipmentId"
						value="${update.equipmentId}" />
				</c:if>
				<input type="hidden" name="mode" value="${mode}" />

				<div class="mb-3">
					<label class="form-label"> 資産分類<span
						class="badge bg-danger ms-1">必須</span></label> <select class="form-select"
						name="assetName">
						<option value="">-- 選択してください --</option>
						<c:forEach var="asset" items="${assetname}">
							<option value="${asset.assetName}"
								<c:if test="${param.assetName == asset.assetName 
			or (param.assetName == null and mode == 'edit' and asset.assetName == update.assetName)}">
			selected
		</c:if>>
								${asset.assetName}</option>
						</c:forEach>
					</select>
					<c:if test="${not empty assetError}">
						<div class="text-danger">
							<c:out value="${assetError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label"> メーカー<span
						class="badge bg-danger ms-1">必須</span></label> <input type="text"
						name="maker" class="form-control" maxlength="100"
						value="${param.maker != null ? param.maker : (mode == 'edit' ? update.maker : '') }"
						autocomplete="off">

					<c:if test="${not empty makerError}">
						<div class="text-danger">
							<c:out value="${makerError}" />
						</div>
					</c:if>

				</div>
				<div class="mb-3">
					<label class="form-label"> 機種<span
						class="badge bg-danger ms-1">必須</span>
					</label> <input type="text" name="model" class="form-control"
						maxlength="100"
						value="${param.model != null ? param.model : (mode == 'edit' ? update.model : '') }">

					<c:if test="${not empty modelError}">
						<div class="text-danger">
							<c:out value="${modelError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label">TYPE</label> <input type="text"
						name="type" class="form-control" pattern="[A-Za-z0-9]+"
						title="半角英数字のみで入力してください"
						oninput="this.value = this.value.replace(/[^A-Za-z0-9]/g, '')"
						maxlength="100"
						value="${param.type != null ? param.type : (mode == 'edit' ? update.type : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label">S/N</label> <input type="text"
						name="serialnumber" class="form-control" pattern="[A-Za-z0-9]+"
						title="半角英数字のみで入力してください"
						oninput="this.value = this.value.replace(/[^A-Za-z0-9]/g, '')"
						maxlength="100"
						value="${param.serialnumber != null ? param.serialnumber : (mode == 'edit' ? update.serialnumber : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label">スペック</label> <input type="text" name="sp"
						class="form-control" pattern="[ -~]*" title="半角英数字と記号のみで入力してください"
						autocomplete="off" maxlength="100"
						value="${param.sp != null ? param.sp : (mode == 'edit' ? update.sp : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label">MACアドレス</label> <input type="text"
						name="macad" class="form-control" maxlength="100"
						autocomplete="off"
						value="${param.macad != null ? param.macad : (mode == 'edit' ? update.macAddress : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label"> 購入日<span
						class="badge bg-danger ms-1">必須</span></label> <input type="date"
						name="purchase_date" class="form-control" max="9999-12-31"
						value="${param.purchase_date != null ? param.purchase_date : (mode == 'edit' ? update.purchaseDate : '') }">
					<c:if test="${not empty purchaseDateError}">
						<div class="text-danger">
							<c:out value="${purchaseDateError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label"> 購入金額<span
						class="badge bg-danger ms-1">必須</span></label> <input type="text"
						name="purchase_price" class="form-control" pattern="[0-9]+"
						title="半角数字のみで入力してください"
						oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0, 7)"
						maxlength="7"
						value="${param.purchase_price != null ? param.purchase_price : (mode == 'edit' ? update.purchasePrice : '') }">
					<c:if test="${not empty purchasePriceError}">
						<div class="text-danger">
							<c:out value="${purchasePriceError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label">使用者</label> <input type="text"
						name="currentuser" class="form-control" maxlength="100"
						value="${param.currentuser != null ? param.currentuser : (mode == 'edit' ? update.currentuser : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label"> 使用場所<span
						class="badge bg-danger ms-1">必須</span></label> <select name="location"
						class="form-select">
						<option value="">選択してください</option>
						<option value="1"
							<c:if test="${param.location == '1' or (param.location == null and mode == 'edit' and update.location == '本社')}">selected</c:if>>本社</option>
						<option value="2"
							<c:if test="${param.location == '2' or (param.location == null and mode == 'edit' and update.location == '仙台支店')}">selected</c:if>>仙台支店</option>
						<option value="3"
							<c:if test="${param.location == '3' or (param.location == null and mode == 'edit' and update.location == '沖縄支店')}">selected</c:if>>沖縄支店</option>
						<option value="4"
							<c:if test="${param.location == '4' or (param.location == null and mode == 'edit' and update.location == '福岡支店')}">selected</c:if>>福岡支店</option>
						<option value="5"
							<c:if test="${param.location == '5' or (param.location == null and mode == 'edit' and update.location == '大阪支店')}">selected</c:if>>大阪支店</option>
					</select>

					<c:if test="${not empty locationError}">
						<div class="text-danger">
							<c:out value="${locationError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label">前使用者</label> <input type="text"
						name="previous_user" class="form-control" maxlength="18"
						value="${param.previous_user != null ? param.previous_user : (mode == 'edit' ? update.previousUser : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label">現在使用開始日</label> <input type="date"
						name="start_date" class="form-control" max="9999-12-31"
						value="${param.start_date != null ? param.start_date : (mode == 'edit' ? update.startDate : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label">現在申請完了日</label> <input type="date"
						name="app_completion_date" class="form-control" max="9999-12-31"
						value="${param.app_completion_date != null ? param.app_completion_date : (mode == 'edit' ? update.applicationCompletionDate : '') }">
				</div>

				<div class="mb-3">
					<label class="form-label"> 備品ステータス<span
						class="badge bg-danger ms-1">必須</span></label> <select
						name="equipmentStatus" class="form-select">
						<option value="">-- 選択してください --</option>
						<c:forEach var="st" items="${statusList}">
							<option value="${st.equipmentStatus}"
								<c:if test="${param.equipmentStatus == st.equipmentStatus or (param.equipmentStatus == null and mode == 'edit' and st.equipmentStatus == update.equipmentStatus)}">selected</c:if>>
								${st.equipmentStatusName}</option>
						</c:forEach>
					</select>
					<c:if test="${not empty equipmentStatusError}">
						<div class="text-danger">
							<c:out value="${equipmentStatusError}" />
						</div>
					</c:if>
				</div>

				<div class="mb-3">
					<label class="form-label">その他</label>
					<textarea name="other" class="form-control" maxlength="100"
						rows="4" style="resize: vertical;"><c:out
							value="${param.other != null ? param.other : (mode == 'edit' ? update.notes : '')}" /></textarea>
				</div>

				<!-- ボタン -->
				<div class="d-flex justify-content-center gap-4 mt-4">

					<button type="submit" formaction="/Equipment/Home" formmethod="get"
						formnovalidate class="btn btn-secondary">戻る</button>

					<c:choose>
						<c:when test="${mode == 'edit'}">
							<button type="submit" formaction="/Equipment/UpdateEquipment"
								formnovalidate="false" class="btn btn-primary">更新</button>
						</c:when>
						<c:otherwise>
							<button type="submit" formaction="/Equipment/AddEquipment"
								formnovalidate="false" class="btn btn-primary">登録</button>
						</c:otherwise>
					</c:choose>

				</div>
			</form>
		</div>
	</div>

</body>
</html>
