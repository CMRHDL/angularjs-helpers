(function() {
  'use strict';
  angular.module('QQappQQ').directive('myNav', myNav);

  myNav.$inject = [];
  function myNav() {
  return {
    restrict: 'E',
    templateUrl: 'navbar/navbar.html',
    controller: 'NavbarCtrl',
    controllerAs: 'nav',
    link: function(scope, element, attrs, tabsCtrl) {
    },
  };
  }
})();
