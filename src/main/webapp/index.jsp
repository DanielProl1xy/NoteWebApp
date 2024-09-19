<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .header {
            background-color: #333;
            color: #fff;
            padding: 20px;
            text-align: center;
            position: sticky;
            top: 0;
            z-index: 1;
        }
        .form-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
        }
        .login-button {
            display: block;
            width: 100%;
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px;
            cursor: pointer;
            font-size: 16px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Login Page</h1>
    </div>
    <div class="form-container">
        <form action="/login" method="get">
            <button type="submit" class="login-button">Login</button>
        </form>
    </div>
</body>
</html>
