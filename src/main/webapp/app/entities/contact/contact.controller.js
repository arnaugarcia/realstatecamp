(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('ContactController', ContactController);

    ContactController.$inject = ['$scope', '$state', 'Contact', 'Company', 'AlertService'];

    function ContactController ($scope, $state, Contact, Company, AlertService) {

        var vm = this;

        vm.contacts = [];
        vm.company = [];
        vm.formData = {};

        loadAll();

        function loadAll() {
            Company.get(function (result) {
                vm.company = result;
            });
        }

        vm.sendEmail = function() {
            Contact.sendMail({
                "to": vm.companies.email,
                "subject": vm.formData.subject,
                "content": vm.formData.name + " (" + vm.formData.mail + ") says...  " + vm.formData.message
            },{},onSuccess,onError);
            function onSuccess() {
                toastr.success("Email enviado con éxito!");
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
            //We finally clean the fields
            vm.formData = {};
        }

    }
})();
