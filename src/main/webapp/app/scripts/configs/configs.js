/**
 * Конфигурации клиентской части приложения
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
var configs = angular.module('ITMoney.configs', []);

configs.factory('Config', function () {
    return {
        appName: '/it-money/store'
    };
});

