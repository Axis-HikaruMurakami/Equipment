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
			<c:when test="${mode == 'adminUpdate'}">
				<h3 class="text-center mb-4">備品の更新</h3>
			</c:when>
			<c:otherwise>
				<h3 class="text-center mb-4">備品の登録</h3>
			</c:otherwise>
		</c:choose>
		<div class="card-block">

			<!-- 登録フォーム -->
			<form id="equipmentForm" method="post">

				<c:if test="${mode == 'adminUpdate'}">
					<input type="hidden" name="userId" value="${update.userId}" />
				</c:if>
				<input type="hidden" name="mode" value="${mode}" />

				<div class="mb-3">
					<label class="form-label"> ユーザID<span
						class="badge bg-danger ms-1">必須</span></label> <input type="text"
						name="user_id" class="form-control" maxlength="100"
						value="${param.user_id != null ? param.user_id : (mode == 'adminUpdate' ? update.user_id : '') }"
						autocomplete="off">

					<c:if test="${not empty makerError}">
						<div class="text-danger">
							<c:out value="${makerError}" />
						</div>
					</c:if>

				</div>

				<div class="mb-3">
					<label class="form-label"> ユーザ名<span
						class="badge bg-danger ms-1">必須</span></label> <input type="text"
						name="user_name" class="form-control" maxlength="100"
						value="${param.user_name != null ? param.user_name : (mode == 'adminUpdate' ? update.user_name : '') }"
						autocomplete="off">

					<c:if test="${not empty makerError}">
						<div class="text-danger">
							<c:out value="${makerError}" />
						</div>
					</c:if>

				</div>
				<!--パスワードtype="text"→type="password"に今後する可能性あり  -->
				<div class="mb-3">
					<label class="form-label"> パスワード<span
						class="badge bg-danger ms-1">必須</span></label> <input type="text"
						name="password" class="form-control" maxlength="100"
						value="${param.password != null ? param.password : (mode == 'adminUpdate' ? update.password : '') }"
						autocomplete="off">

					<c:if test="${not empty makerError}">
						<div class="text-danger">
							<c:out value="${makerError}" />
						</div>
					</c:if>

				</div>

				<div class="mb-3">
					<label class="form-label"> 所属<span
						class="badge bg-danger ms-1">必須</span>
					</label> <select name="location_cd" class="form-select">
						<option value="">選択してください</option>

						<option value="1"
							<c:if test="${param.location_cd == '1'
				or (param.location_cd == null
				and mode == 'adminUpdate'
				and update.location_cd == '1')}">
				selected
			</c:if>>
							本社</option>

						<option value="2"
							<c:if test="${param.location_cd == '2'
				or (param.location_cd == null
				and mode == 'adminUpdate'
				and update.location_cd == '2')}">
				selected
			</c:if>>
							仙台支店</option>

						<option value="3"
							<c:if test="${param.location_cd == '3'
				or (param.location_cd == null
				and mode == 'adminUpdate'
				and update.location_cd == '3')}">
				selected
			</c:if>>
							沖縄支店</option>

						<option value="4"
							<c:if test="${param.location_cd == '4'
				or (param.location_cd == null
				and mode == 'adminUpdate'
				and update.location_cd == '4')}">
				selected
			</c:if>>
							福岡支店</option>

						<option value="5"
							<c:if test="${param.location_cd == '5'
				or (param.location_cd == null
				and mode == 'adminUpdate'
				and update.location_cd == '5')}">
				selected
			</c:if>>
							大阪支店</option>

					</select>

					<c:if test="${not empty locationError}">
						<div class="text-danger">
							<c:out value="${locationError}" />
						</div>
					</c:if>

				</div>

				<div class="mb-3">
					<label class="form-label"> 管理者権限<span
						class="badge bg-danger ms-1">必須</span>
					</label> <select name="admin_flg" class="form-select">
						<option value="">選択してください</option>

						<option value="0"
							<c:if test="${param.admin_flg == '0'
				or (param.admin_flg == null
				and mode == 'adminUpdate'
				and update.admin_flg == 0)}">
				selected
			</c:if>>
							なし</option>

						<option value="1"
							<c:if test="${param.admin_flg == '1'
				or (param.admin_flg == null
				and mode == 'adminUpdate'
				and update.admin_flg == 1)}">
				selected
			</c:if>>
							あり</option>

					</select>
					
					<c:if test="${not empty locationError}">
						<div class="text-danger">
							<c:out value="${locationError}" />
						</div>
					</c:if>
					
				</div>

				<!-- ボタン -->
				<div class="d-flex justify-content-center gap-4 mt-4">

					<button type="submit" formaction="/Equipment/AdminHome" formmethod="get"
						formnovalidate class="btn btn-secondary">戻る</button>

					<c:choose>
						<c:when test="${mode == 'adminUpdate'}">
							<button type="submit" formaction="/Equipment/UpdateUser"
								formnovalidate="false" class="btn btn-primary">更新</button>
						</c:when>
						<c:otherwise>
							<button type="submit" formaction="/Equipment/AddUser"
								formnovalidate="false" class="btn btn-primary">登録</button>
						</c:otherwise>
					</c:choose>

				</div>
			</form>
		</div>
	</div>

</body>
</html>
