(function() {
    'use strict';
    angular
        .module('assessoriaTorrellesApp')
        .factory('Notification', Notification);

    Notification.$inject = ['$resource', 'DateUtils'];

    function Notification ($resource, DateUtils) {
        var resourceUrl =  'api/notifications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'byUser': { method: 'GET', isArray: true, url: 'api/notifications/byUser'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
