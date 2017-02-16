(function() {
    'use strict';
    angular
        .module('assessoriaTorrellesApp')
        .factory('Photo', Photo);

    Photo.$inject = ['$resource', 'DateUtils'];

    function Photo ($resource, DateUtils) {
        var resourceUrl =  'api/photos/:id';

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
