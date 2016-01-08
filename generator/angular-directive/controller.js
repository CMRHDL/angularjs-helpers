(function() {
  'use strict';
  angular.module('QQApp').controller('QQCtrl', QQCtrl);

  QQCtrl.$inject = [ 'QQServiceName' ];
  function QQCtrl(QQServiceName) {
    var vm = this;
  }
})();
