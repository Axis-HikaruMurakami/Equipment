<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザ表示画面</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="/Equipment/css/style.css">
<link rel="stylesheet" href="/Equipment/css/main.css">
</head>
<body class="bg-light">

	<div class="container py-4">
		<h3 class="text-center mb-4">ユーザ表示画面</h3>

		<!-- ログインユーザー表示 -->
		<div class="col-12 text-end mt-2">
			<p>ログインユーザー：${user.user_name}</p>
			<form action="/Equipment/Logout" method="post">
				<input type="submit" value="ログアウト" class="btn btn-danger">
			</form>
		</div>

		<!-- 上部のボタン群（CSV出力、削除履歴、全権表示）-->
		<div class="d-flex justify-content-start mb-3">
			<form action="/Equipment/log" method="post" style="display: inline;">
				<button type="submit" class="btn btn-success me-2">全件表示</button>
				<input type="hidden" name="flg" value="true">
			</form>
		</div>


		<h5 class="mb-3">検索条件</h5>

		<!-- 検索フォーム -->
		<form action="/Equipment/UserSearch" method="post" class="row g-3">
			<div class="col-md-4">
				<label class="form-label">ユーザID</label> <input type="text"
					name="userId" class="form-control" maxlength="9" />
			</div>

			<div class="col-md-4">
				<label class="form-label">ユーザ名</label> <input type="text"
					name="userName" class="form-control" maxlength="9" />
			</div>

			<div class="col-md-4">
				<label class="form-label">所属</label> <select class="form-select"
					name="locationName">
					<option value="">-- 選択してください --</option>
					<c:forEach var="location" items="${locationList}">
						<option value="${location}">${location}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-4">
				<label class="form-label">削除フラグ</label> <select name="deleteFlag"
					class="form-select">
					<option value="0"
						${deleteFlag == null || deleteFlag == '0' ? 'selected' : ''}>
						未削除</option>
					<option value="1" ${deleteFlag == '1' ? 'selected' : ''}>
						削除済み</option>
				</select>
			</div>

			<div class="col-12 text-start mt-2">
				<button type="submit" class="btn btn-secondary me-2">検索</button>
			</div>
		</form>

		<!-- <script>いらないが念のため一時的に残しておく
			// IME入力対応：全角・半角合わせて9文字まで入力制限
			const currentuserInput = document.getElementById('currentuser');
			let isComposing = false;

			currentuserInput.addEventListener('compositionstart', () => {
				isComposing = true;
			});

			currentuserInput.addEventListener('compositionend', () => {
				isComposing = false;
				if (currentuserInput.value.length > 9) {
					currentuserInput.value = currentuserInput.value.slice(0, 9);
				}
			});

			currentuserInput.addEventListener('input', () => {
				if (!isComposing && currentuserInput.value.length > 9) {
					currentuserInput.value = currentuserInput.value.slice(0, 9);
				}
			});
		</script> -->

		<script>
			function submitForm(mode){
				const selected = document.querySelector('input[name="select"]:checked');

				//修正ボタンで選択がされていなかった場合アラート
				if (mode === 'edit' && !selected){
					alert("修正するには、表から1件選択してください。");
					return;
					}
			
				//フォームを新規に作成してPOST送信
				const form = document.createElement("form")
				form.method = "POST";
				form.action = "/Equipment/AdminAddUpdate";
				
				//修正ボタンなら選択されたユーザIDを送る
				if(mode === 'adminUpdate'){
					const input = document.createElement("input")
					input.type = "hidden";
					input.name = "id";
					input.value = selected.value;
					form.appendChild(input);//type,name,valueをフォームに追加
					}

				// 追加ボタンが押されたら
			    if (mode === 'add') {
			        const input = document.createElement("input")
			        input.type = "hidden";
			        input.name = "mode";
			        input.value = "add";
			        form.appendChild(input);
			    }
			    
				document.body.appendChild(form);
				form.submit();//フォームの送信
			}
		</script>
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		<!-- 検索結果 -->
		<div class="card-block resultTable mt-4">
			<h5 class="mb-3">検索結果</h5>

			<div class="d-flex mb-2">
				<!-- 隠しフィールドに選択した備品IDを保持 -->
				<input type="hidden" name="selectedId" id="selectedId">

				<!-- 操作ボタン -->
				<button type="button" class="btn btn-primary me-2"
					onclick="submitForm('add')">追加</button>
				<button type="button" class="btn btn-primary me-2"
					onclick="submitForm('adminUpdate')">修正</button>

				<form action="/Equipment/AdminDeleteServlet" method="post"
					style="display: inline;" id="deleteForm">
					<input type="hidden" name="userId" value=""
						id="deleteEquipmentId" />
					<button type="button" class="btn btn-primary me-2"
						onclick="confirmDelete()">削除</button>
				</form>

				<form action="/Equipment/InportServlet" method="post"
					style="display: inline;">
					<button type="submit" class="btn btn-primary me-2">一括登録</button>
				</form>
			</div>


			<div class="table-responsive mt-3">
				<table class="table table-bordered table-sm">
					<thead>
						<tr>
							<th class="sticky-col">選択</th>
							<th>ユーザID</th>
							<th>ユーザ名</th>
							<th>所属</th>
							<th>管理者権限</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${displayList}">
							<tr class="clickable-row">
								<td class="sticky-col"><input type="radio" name="select"
									value="${item.user_id}"></td>
								<td>${item.user_id}</td>
								<td>${item.user_name}</td>
								<td><c:choose>
										<c:when test="${item.location_cd == 1}">本社</c:when>
										<c:when test="${item.location_cd == 2}">仙台支店</c:when>
										<c:when test="${item.location_cd == 3}">沖縄支店</c:when>
										<c:when test="${item.location_cd == 4}">福岡支店</c:when>
										<c:when test="${item.location_cd == 5}">大阪支店</c:when>
										<c:otherwise>不明</c:otherwise>
									</c:choose></td>
									
								<td><c:choose>
										<c:when test="${item.admin_flg == 1}">あり</c:when>
										<c:otherwise>なし</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function () {
		document.querySelectorAll('.clickable-row').forEach(row => {
			row.addEventListener('click', function () {
				const radio = this.querySelector('input[type="radio"]');
				if (radio) {
					radio.checked = true;
				}
			});
		});
	});

	/* function setAction(actionUrl) {おそらくいらないと思われる
		const radios = document.getElementsByName('select');
		let selectedValue = null;
		for (const radio of radios) {
			if (radio.checked) {
				selectedValue = radio.value;
				break;
			}
		}
 
		if (!selectedValue) {
			alert("行を選択してください。");
			return false;
		}
 
		// 選択されたIDを隠しフィールドに設定
		document.getElementById('selectedId').value = selectedValue;
		const form = document.getElementById('actionForm');
		form.action = actionUrl;  // 遷移先サーブレットを設定
		return true;
	} */
	
        // 削除ボタンがクリックされた時の確認ダイアログ
        function confirmDelete() {
            const selectedRadio = document.querySelector('input[name="select"]:checked');
            if (!selectedRadio) {
                alert("選択されていません");
                return;
            }
            if (window.confirm('本当に削除しますか？')) {
                const equipmentId = selectedRadio.value;
                document.getElementById('deleteEquipmentId').value = equipmentId;
                document.getElementById('deleteForm').submit();
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            document.querySelectorAll('.clickable-row').forEach(row => {
                row.addEventListener('click', function () {
                    const radio = this.querySelector('input[type="radio"]');
                    if (radio) {
                        radio.checked = true;
                        const selectedId = radio.value;
                        document.getElementById('updateEquipmentId').value = selectedId;
                        document.getElementById('deleteEquipmentId').value = selectedId;
                    }
                });
            });
        });
    </script>
</body>
</html>
