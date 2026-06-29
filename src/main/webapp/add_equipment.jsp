<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>備品追加画面</title>
<!-- Bootstrap 5 CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
  body {
    overflow-y: scroll;
    height: 100vh;
  }
</style>
</head>
<body class="bg-light">

<div class="container py-4">
  <h3 class="text-center mb-4">備品の追加</h3>

  <!-- 登録フォーム -->
  <form>
    <div class="mb-3">
      <label for="assets" class="form-label">資産分類<span class="text-danger">※</span></label>
      <select id="assets" name="assets" class="form-select" required>
        <option value="">選択してください</option>
        <option value="desktopPC">デスクトップPC</option>
        <option value="notePC">ノートPC</option>
        <option value="mause">マウス</option>
        <option value="keyboard">キーボード</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">メーカー<span class="text-danger">※</span></label>
      <input type="text" name="maker" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">機種<span class="text-danger">※</span></label>
      <input type="text" name="model" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">TYPE<span class="text-danger">※</span></label>
      <input type="text" name="type" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">S/N<span class="text-danger">※</span></label>
      <input type="text" name="serialnumber" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">スペック<span class="text-danger">※</span></label>
      <input type="text" name="sp" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">MACアドレス</label>
      <input type="text" name="macad" class="form-control">
    </div>

    <div class="mb-3">
      <label class="form-label">購入日<span class="text-danger">※</span></label>
      <input type="date" name="purchase_date" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">購入金額</label>
      <input type="number" name="purchase_price" class="form-control">
    </div>

    <div class="mb-3">
      <label class="form-label">使用者</label>
      <input type="text" name="currentuser" class="form-control">
    </div>

	<div class="mb-3">
	    <label class="form-label">使用場所</label>	
	    <select name="location" id="location_cd" class="form-select" required>
	        <option value="">選択してください</option>
	        <option value="1">本社</option>
	        <option value="2">仙台支店</option>
	        <option value="3">沖縄支店</option>
	        <option value="4">福岡支店</option>
	        <option value="5">大阪支店</option>
	    </select>
	</div>

    <div class="mb-3">
      <label class="form-label">前使用者</label>
      <input type="text" name="previous_user" class="form-control">
    </div>

    <div class="mb-3">
      <label class="form-label">現在使用開始日</label>
      <input type="date" name="start_date" class="form-control">
    </div>

    <div class="mb-3">
      <label class="form-label">現在申請完了日</label>
      <input type="date" name="app_completion_date" class="form-control">
    </div>

    <!-- プロジェクト -->
    <div class="mb-3">
      <label class="form-label">プロジェクト</label>
      <input type="text" name="project" class="form-control">
    </div>

    <!-- その他 -->
    <div class="mb-3">
      <label class="form-label">その他</label>
      <textarea name="other" class="form-control" rows="4" style="resize: vertical;"></textarea>
    </div>

    <!-- ボタン -->
    <div class="d-flex justify-content-between mt-4">
      <button type="submit" formaction="/Home" formmethod="get" formnovalidate class="btn btn-secondary w-50 me-2">戻る</button>
      <button type="submit" formaction="/AddEquipment" formmethod="post" class="btn btn-primary w-50">登録</button>
    </div>
  </form>
</div>

</body>
</html>
 --%>