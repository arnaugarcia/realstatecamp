(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['Principal', 'Auth', 'Company','JhiLanguageService', '$translate', 'AlertService'];

    function SettingsController (Principal, Auth, Company, JhiLanguageService, $translate, AlertService) {
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


        var copyCompany = function () {
            console.log();
            return {
                id: companyId.value,
                name: companyName.value,
                phone: companyPhone.value,
                email: companyEmail.value,
                cif: companyCif.value
            };
        };

        vm.saveCompany = function () {
            //I'm sorry about that
            Company.update({
                "id" : copyCompany().id,
                "name" : copyCompany().name,
                "phone" : copyCompany().phone,
                "email" : copyCompany().email,
                "cif" : copyCompany().cif,
                "location" : {
                    "id" : 6,
                    "ref" : "ref-260",
                    "province" : "Barcelona",
                    "town" : "Torrelles de Llobregat",
                    "typeOfRoad" : "PLACE",
                    "nameRoad" : "Plaza del Ayuntamiento",
                    "number" : 2,
                    "apartment" : null,
                    "building" : null,
                    "door" : null,
                    "stair" : null,
                    "urlgmaps" : "https://www.google.es/maps/place/Grup+Palet+Gestor%C3%ADa+S.L./@41.3579495,1.9810097,15z/data=!4m5!3m4!1s0x0:0xbcd5ffa3a92d1531!8m2!3d41.3579495!4d1.9810097",
                    "latitude" : 41.3579495,
                    "longitude" : 1.9810097,
                    "cp" : null
                }
            }, onSuccess, onError);

            function onSuccess() {
                toastr.success('Success!', 'Company edited');
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
