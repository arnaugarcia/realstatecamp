(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['Principal', 'Auth', 'Company','JhiLanguageService', '$translate'];

    function SettingsController (Principal, Auth, Company, JhiLanguageService, $translate) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;
        vm.company = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };

        loadCompany();

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });

        function loadCompany () {
            Company.query({

            }, onSuccess, onError);

            function onSuccess(data) {

                vm.company = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                });
                JhiLanguageService.getCurrent().then(function(current) {
                    if (vm.settingsAccount.langKey !== current) {
                        $translate.use(vm.settingsAccount.langKey);
                    }
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }

    }
})();
