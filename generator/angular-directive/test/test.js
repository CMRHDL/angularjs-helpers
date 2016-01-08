(function() {
  'use strict';
  angular.module('app').directive('myTest', myTest);

  myTest.$inject = [  ];
  function myTest() {
    return {
      controller : 'TestCtrl',
      controllerAs : 'c',
      restrict: 'A',
      scope: {
        foo: '@',
        bar: '=',
      },
      require: ['ngModel', '^myTest'],
      bindToController: true,
      link: function(scope, element, attrs, ctrls) {

        var ngModel = ctrls[0];
        var ctrl = ctrls[1];
        ctrl.model = ngModel;

        // init Model
        ngModel.$setViewValue();
        ngModel.$render();

        element.bind("change",function(event){
        });
      },
    };
  }
})();
