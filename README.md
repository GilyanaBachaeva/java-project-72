### Hexlet tests and linter status:
[![Actions Status](https://github.com/GilyanaBachaeva/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/GilyanaBachaeva/java-project-72/actions)

[![Maintainability](https://api.codeclimate.com/v1/badges/80cb28273733bed893e4/maintainability)](https://codeclimate.com/github/GilyanaBachaeva/java-project-72/maintainability)

[![Test Coverage](https://api.codeclimate.com/v1/badges/80cb28273733bed893e4/test_coverage)](https://codeclimate.com/github/GilyanaBachaeva/java-project-72/test_coverage)
Описание
Анализатор страниц - это веб-сайт, разработанный на фреймворке Javalin. 
Здесь отрабатываются основные принципы построения современных сайтов на архитектуре MVC: работа с маршрутизацией, 
обработчиками запросов и шаблонизатором, взаимодействие с базой данных через ORM. 
Оcновная задача - делать анализ других сайтов на SEO-пригодность.
На главной странице осуществляется подготовка выбранного адреса сайта к проверке: проводится валидность адреса 
(проверка протокола и домена- является ли введенная ссылка адресом сайта), впервые ли он добавляется пользователем. 
Затем выбранный пользователем адрес добавляется в список сайтов, которые подлежат проверке. 
Заводится отдельная страница для каждого адреса сайта, на которой пользователю доступна информаия о дате 
проведения последней проверки и о ее результате, а также функция самой проверки, посредством выполнения которой 
на текущей странице заполняется информация о результатах ее проведения.

Демонстрация проекта: https://page-analyzer-p19k.onrender.com