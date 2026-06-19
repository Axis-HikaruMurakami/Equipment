<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>一括登録画面</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      opacity: 0;
      animation: fadeIn 1s forwards;
    }
    @keyframes fadeIn {
      to { opacity: 1; }
    }
  </style>
</head>
<body class="bg-light">

<div class="container py-5">
    <h3 class="mb-4 text-center">一括登録</h3>

    <form action="<%=request.getContextPath()%>/CSVImportServlet" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="csvfile" class="form-label">CSVファイルを選択してください<span class="text-danger">※</span></label>
            <input type="file" id="csvfile" name="csvfile" accept=".csv,text/csv" class="form-control" required>
        </div>

        <div class="d-flex justify-content-between">
            <button type="button" class="btn btn-secondary" onclick="location.href='<%=request.getContextPath()%>/Home';">戻る</button>
            <button type="submit" class="btn btn-primary">取り込む</button>
        </div>
    </form>

        <!-- CSVフォーマット説明 -->
    <div class="alert alert-info mt-4">
        <h5>CSVファイルのフォーマットについて</h5>
        <p>以下の項目がカンマ区切りで17列必要です。1行目はヘッダー行としてスキップされます。</p>
        <div class="table-responsive">
            <table class="table table-sm table-bordered" style="min-width:900px;">
                <thead class="table-light">
                    <tr>
                        <th>列番号</th><th>項目名</th><th>説明</th>
                    </tr>
                </thead>
                <tbody>
                    <tr><td>1</td><td>備品番号</td><td>例: 2, 4, 7 ...</td></tr>
                    <tr><td>2</td><td>資産分類</td><td>例: デスクトップPC, プリンター</td></tr>
                    <tr><td>3</td><td>メーカー</td><td>例: H, EPSON, DELL</td></tr>
                    <tr><td>4</td><td>機種</td><td>例: EliteDesk, PX-105, XPS13</td></tr>
                    <tr><td>5</td><td>TYPE</td><td>例: Desktop, Printer, Laptop</td></tr>
                    <tr><td>6</td><td>S/N (シリアル番号)</td><td>例: SN12345678</td></tr>
                    <tr><td>7</td><td>スペック</td><td>例: 16GB RAM / 512GB SSD</td></tr>
                    <tr><td>8</td><td>MACアドレス</td><td>例: 00:11:22:33:44:55 （空欄可）</td></tr>
                    <tr><td>9</td><td>購入日</td><td>yyyy/MM/dd形式。例: 2023/01/10</td></tr>
                    <tr><td>10</td><td>経過年</td><td>例: 2 （整数）</td></tr>
                    <tr><td>11</td><td>購入金額</td><td>整数。例: 150000</td></tr>
                    <tr><td>12</td><td>使用者</td><td>例: tanaka, suzuki</td></tr>
                    <tr><td>13</td><td>使用場所</td><td>例: 東京本社, 大阪支社</td></tr>
                    <tr><td>14</td><td>前使用者</td><td>空欄可</td></tr>
                    <tr><td>15</td><td>現在使用開始日</td><td>yyyy/MM/dd形式。空欄可</td></tr>
                    <tr><td>16</td><td>現在申請完了日</td><td>yyyy/MM/dd形式。空欄可</td></tr>
                    <tr><td>17</td><td>備品ステータス</td><td>例: 未使用，使用中，故障中，廃棄</td></tr>
                    <tr><td>18</td><td>その他</td><td>備考・メモなど自由記入</td></tr>
                </tbody>
            </table>
        </div>
        <p><strong>注意：</strong>CSVは必ず<strong>カンマ区切り形式</strong>でアップロードしてください。</p>
    </div>
</div>
</div>

<!-- トースト配置 -->
<div class="position-fixed top-0 end-0 p-3" style="z-index: 9999;">
    <div id="toastBox" class="toast align-items-center text-bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body" id="toastMessage">
                <!-- メッセージ挿入 -->
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="閉じる"></button>
        </div>
    </div>
</div>

<!-- Bootstrap & Toast スクリプト -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    <% String errorMessage = (String) request.getAttribute("errorMessage");
       String successMessage = (String) request.getAttribute("successMessage");

       if ((errorMessage != null && !errorMessage.isEmpty()) || 
           (successMessage != null && !successMessage.isEmpty())) { %>
        window.addEventListener('DOMContentLoaded', function () {
            const toastElement = document.getElementById('toastBox');
            const toastMessage = document.getElementById('toastMessage');
            const toast = new bootstrap.Toast(toastElement);

            <% if (successMessage != null && !successMessage.isEmpty()) { %>
                toastMessage.textContent = "<%= successMessage.replace("\"", "\\\"") %>";
                toastElement.classList.remove("text-bg-danger");
                toastElement.classList.add("text-bg-success");
            <% } else if (errorMessage != null && !errorMessage.isEmpty()) { %>
                toastMessage.textContent = "<%= errorMessage.replace("\"", "\\\"") %>";
                toastElement.classList.remove("text-bg-success");
                toastElement.classList.add("text-bg-danger");
            <% } %>

            toast.show();
        });
    <% } %>
</script>

</body>
</html>
