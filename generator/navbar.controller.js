(function() {
    'use strict';
    angular.module('QQappQQ').controller('NavbarCtrl', NavbarCtrl);

    NavbarCtrl.$inject = [ '$location' ];
    function NavbarCtrl($location) {
        var nav = this;


        var activeTab = $location.url().substr(1,$location.url().length) !== '' ? $location.url().substr(1,$location.url().length) : 'overview';

        nav.isTabActive = isTabActive;
        nav.setActiveTab = setActiveTab;

        // http://getbootstrap.com/components/
        nav.tabs = [
QQnavbarEntrysQQ
        ];

        function isTabActive(tab) {
          return activeTab === tab ? 'active' : '';
        }
        function setActiveTab(tab) {
          activeTab = tab;
        }
    }
})();