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
            'insertPhoto':{method : 'POST', url: '/api/properties/:id/photos'}, //TODO remove hardcoded /1/ and replace with actual id
            'getPhotos':{method:'GET',url:'/api/properties/:id/photos',isArray: true},
            'deletePhoto':{method:'DELETE',url:'/api/properties/:id/photos'},
            'makeCover':{method:'POST',url:'/api/properties/:idProperty/cover/:idPhoto'},
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
