(function() {

  function config($routeProvider){
    $routeProvider
QQroutesQQ.otherwise({
      redirectTo:'/'
    });
  }

  angular.module('QQappQQ',['ngRoute']);
  angular.module('QQappQQ').config(config);

}());