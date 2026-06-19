<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <title>ログイン </title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet" />
  <style>
    body {
      margin: 0; padding: 0; height: 100vh;
      background: linear-gradient(to right, #000000, #1a1a40);
      overflow: hidden;
      display: flex; justify-content: center; align-items: center;
      font-family: 'Segoe UI', sans-serif;
      position: relative;
    }
    .stars {
      position: absolute; width: 100%; height: 100%;
      background: url('https://www.transparenttextures.com/patterns/stardust.png') repeat;
      animation: moveStars 200s linear infinite;
      z-index: 0;
    }
    @keyframes moveStars {
      from { background-position: 0 0; }
      to { background-position: 10000px 10000px; }
    }
    .login-box {
      position: relative;
      z-index: 1;
      background: rgba(255, 255, 255, 0.1);
      padding: 40px;
      border-radius: 20px;
      box-shadow: 0 0 30px rgba(255, 255, 255, 0.2);
      width: 90%;
      max-width: 400px;
      color: #fff;
      backdrop-filter: blur(10px);
      animation: fadeInDown 1s;
      transition: transform 0.7s ease, opacity 0.7s ease;
      transform-origin: center center;
    }
    .form-control {
      background-color: rgba(255, 255, 255, 0.2);
      border: none;
      color: #fff;
    }
    .form-control::placeholder {
      color: #ddd;
    }
    .form-floating > label {
      color: #ccc;
    }
    .btn-login {
      background-color: #6c5ce7;
      border: none;
      font-weight: bold;
      letter-spacing: 0.05em;
      transition: box-shadow 0.3s ease, transform 0.15s ease;
    }
    .btn-login:hover {
      background-color: #a29bfe;
      box-shadow: 0 0 15px #a29bfe;
    }
  </style>
</head>
<body>

  <div class="stars"></div>

  <div class="login-box animate__animated animate__fadeInDown" id="loginBox">
    <h3 class="text-center mb-4">ログイン画面</h3>
    <form action="<%=request.getContextPath()%>/Home" method="post" id="loginForm">
      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="id" name="id" placeholder="ログインID" required
               oninput="enforceHalfWidth(this)" autocomplete="off" maxlength="4"/>
        <label for="id">ログインID</label>
      </div>
      <div class="form-floating mb-4">
        <input type="password" class="form-control" id="password" name="password" placeholder="パスワード" required autocomplete="off" maxlength="32"/>
        <label for="password">パスワード</label>
      </div>
      <button type="submit" class="btn btn-login w-100 py-2">ログイン</button>
    </form>
  </div>

  <!-- ログインエラー時モーダル表示 -->
  <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true"
    data-bs-backdrop="static" data-bs-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header bg-danger text-white">
          <h5 class="modal-title" id="errorModalLabel">ログインエラー</h5>
        </div>
        <div class="modal-body">
          ログインIDまたはパスワードが間違っています。
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
        </div>
      </div>
    </div>
  </div>

  <% if ("true".equals(request.getAttribute("error"))) { %>
  <script>
    window.addEventListener('DOMContentLoaded', function () {
      var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
      errorModal.show();
    });
  </script>
  <% } %>

  <script>
    // 半角ASCII文字以外を除外（半角英数字+記号を許可）
    function enforceHalfWidth(input) {
      input.value = input.value.replace(/[^\x20-\x7E]/g, '');
    }

    document.getElementById('loginForm').addEventListener('submit', function(e){
      e.preventDefault(); // 通常送信を止める

      const box = document.getElementById('loginBox');
      box.style.transition = 'transform 0.7s ease, opacity 0.7s ease';
      box.style.transform = 'rotate(360deg) scale(0)';
      box.style.opacity = '0';

      setTimeout(() => {
        e.target.submit();
      }, 700);
    });
  </script>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
