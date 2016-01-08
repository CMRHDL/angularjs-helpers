(function() {
  'use strict';
  angular.module('QQApp').directive('QQDirective', QQDirective);

  QQDirective.$inject = [  ];
  function QQDirective() {
    return {
      controller : 'QQCtrl',
      controllerAs : 'c',
      restrict: 'A',
      scope: {
        foo: '@',
        bar: '=',
      },
      require: ['ngModel', '^QQDirective'],
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
