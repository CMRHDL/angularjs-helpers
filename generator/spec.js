'use strict';

describe('Controller: QQentryCapQQCtrl', function () {

  beforeEach(module('QQappQQ'));

  var QQentryCapQQCtrl, scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    QQentryCapQQCtrl = $controller('QQentryCapQQCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(scope.awesomeThings.length).toBe(3);
  });
});