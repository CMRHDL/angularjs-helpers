(function() {
  'use strict';
  angular.module('QQappQQ').controller('NavbarCtrl', NavbarCtrl);

  NavbarCtrl.$inject = [ '$location' ];
  function NavbarCtrl($location) {
    var nav = this;

    nav.isActive = isActive;

    // http://getbootstrap.com/components/
    nav.tabs = [
QQnavbarEntrysQQ
    ];

    function isActive(tab) {
      return getLocation() === tab ? 'active' : '';
    }

    function getLocation() {
      return $location.path().substring(1) || 'home';
    }
  }
})();