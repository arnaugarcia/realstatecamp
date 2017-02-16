(function () {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
