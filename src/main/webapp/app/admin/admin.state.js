(function () {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('admin', {
            abstract: true,
            parent: 'app',
            data: {
                css: [
                    'content/css/style.css',
                    {
                        name: 'arillo',
                        href: 'content/css/style.css'
                    }
                ]
            }
        });
    }
})();
