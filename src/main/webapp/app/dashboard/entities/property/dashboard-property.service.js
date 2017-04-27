(function() {
    'use strict';
    angular
        .module('assessoriaTorrellesApp')
        .factory('DashboardProperty', DashboardProperty);

    DashboardProperty.$inject = ['$resource', 'DateUtils'];

    function DashboardProperty ($resource, DateUtils) {
        var resourceUrl =  'api/properties/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
