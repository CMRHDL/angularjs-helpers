(function() {
  'use strict';
  angular.module('QQappQQ').controller('NavbarCtrl', NavbarCtrl);

  NavbarCtrl.$inject = [ '$location', '$scope' ];
  function NavbarCtrl($location, $scope) {
    var nav = this;

    nav.isActive = isActive;

    var activeTab = getLocation();

    $scope.$on('$locationChangeSuccess', function() {
      activeTab = getLocation();
    });

    // http://getbootstrap.com/components/
    nav.tabs = [
QQnavbarEntrysQQ
    ];

    function isActive(tab) {
      return activeTab === tab ? 'active' : '';
    }

    function getLocation() {
      return $location.url().substr(1,$location.url().length) !== '' ? $location.url().substr(1,$location.url().length) : nav.tabs[0].name;
    }
  }
})();