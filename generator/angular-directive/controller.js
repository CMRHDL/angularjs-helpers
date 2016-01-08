(function() {
  'use strict';
  angular.module('$App').controller('$Ctrl', $Ctrl);

  $Ctrl.$inject = [ '$ServiceName' ];
  function $Ctrl($ServiceName) {
    var vm = this;
  }
})();
