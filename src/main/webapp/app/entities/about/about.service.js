(function() {
    'use strict';
    angular
        .module('assessoriaTorrellesApp')
        .factory('About', About);

    About.$inject = ['$resource'];

    function About ($resource) {
        var resourceUrl =  'api/abouts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
