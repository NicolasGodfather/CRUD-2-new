/**
 * Этот скрипт содержит контроллеры клиентской части приложения
 *
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
var itMoneyModule = angular.module('ITMoney');

itMoneyModule.controller('SaveUserController', function ($scope, $route, $location, User) {
    if ($route.current.params.userId) {
        User.get($route.current.params.userId, function (result) {
            if (result) {
                $scope.user = angular.fromJson(result);
            }
        });
    }

    $scope.save = function () {
        User.save($scope.user);
        $location.path("/");
    }

    $scope.showForm = function () {
        if ($route.current.params.userId) {
            if ($scope.user) {
                return true;
            }
            return false;
        }
        return true;
    }
});
// основная задача - получить модель (Model) с сервера и правильно распорядиться этой информацией
itMoneyModule.controller('AllUsersController', function ($scope, $location, User) {
    $scope.allUsers = [];

    function getAllUsers() {
        User.getAll(function (result) {
            if (result) {
                $scope.allUsers = angular.fromJson(result);
            }
        });
    }

    getAllUsers();

    $scope.delete = function (id) {
        User.delete(id, function (result) {
            getAllUsers();
        });
    }
});
