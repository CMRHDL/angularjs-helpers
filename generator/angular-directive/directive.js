(function() {
  'use strict';
  angular.module('App').directive('Directive', Directive);

  Directive.$inject = [ ];
  function Directive() {
    return {
      bindToController: true,
      controller: 'Ctrl',
      controllerAs: 'c',
      require: ['ngModel', '^Directive'],
      restrict: 'A',
      scope: {
        foo: '@',
        bar: '=',
      },
      templateUrl: 'TEMPLATE_PATH',
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
