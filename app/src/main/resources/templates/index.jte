<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главная страница</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Добро пожаловать на главную страницу!</h1>
        <p>Это пример использования Jte с Javalin и Bootstrap.</p>

        <!-- Форма для ввода URL -->
        <form action="/urls" method="post">
            <div class="form-group">
                <label for="url">Введите URL:</label>
                <input type="text" class="form-control" id="url" name="url" required>
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>

        <!-- Вывод flash-сообщений -->
        <div>
            {{ flashMessage }}
        </div>
    </div>
</body>
</html>