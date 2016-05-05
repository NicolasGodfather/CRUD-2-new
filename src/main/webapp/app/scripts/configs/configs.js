/**
 * Конфигурации клиентской части приложения
 */
var configs = angular.module('ITMoney.configs', []);

configs.factory('Config', function () {
    return {
        appName: '/it-money/store'
    };
});

