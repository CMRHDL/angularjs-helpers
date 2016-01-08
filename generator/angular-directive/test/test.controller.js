(function() {
  'use strict';
  angular.module('app').controller('TestCtrl', TestCtrl);

  TestCtrl.$inject = [ 'test' ];
  function TestCtrl(test) {
    var vm = this;
  }
})();
