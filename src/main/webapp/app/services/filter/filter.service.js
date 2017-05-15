/**
 * Created by arnau on 13/5/17.
 */
(function () {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .factory('FilterService', FilterService);

    FilterService.$inject = ['$resource'];

    function FilterService ($resource) {

        var service = $resource('api/filter/', {}, {
            'home': {method: 'GET', isArray: false, url: "api/filter/home"}
        });

        return service;
    }
})();
