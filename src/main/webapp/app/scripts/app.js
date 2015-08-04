/**
 * Этот скрипт инициализирует клиентскую часть этого приложения
 *
 * И routes (urls) по которым будем определять какой контроллер будет обрабатывать
 * действия пользователя
 *
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
var iTMoneyModule = angular.module("ITMoney", ["ngRoute", 'ITMoney.services']);

var routesConfig = function ($routeProvider) {
    $routeProvider.
        when("/", {controller: "AllUsersController", templateUrl: "views/allUsers.html"}).
        when("/user/:userId", {controller: "SaveUserController", templateUrl: "views/saveUser.html"}).
        when("/addUser/", {controller: "SaveUserController", templateUrl: "views/saveUser.html"}).
        otherwise({redirectTo: "/"});
};

iTMoneyModule.config(routesConfig);