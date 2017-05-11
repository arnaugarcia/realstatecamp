(function() {
    'use strict';
    angular
        .module('assessoriaTorrellesApp')
        .factory('Contact', Contact);

    Contact.$inject = ['$resource'];

    function Contact ($resource) {
        var resourceUrl =  'api/contact';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'sendMail': { method: 'POST', isArray: false}
        });
    }
})();
