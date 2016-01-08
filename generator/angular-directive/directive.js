(function() {
  'use strict';
  angular.module('$App').directive('$Directive', $Directive);

  $Directive.$inject = [  ];
  function $Directive() {
    return {
      controller : '$Ctrl',
      controllerAs : 'c',
      restrict: 'A',
      scope: {
        foo: '@',
        bar: '=',
      },
      require: ['ngModel', '^$Directive'],
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
