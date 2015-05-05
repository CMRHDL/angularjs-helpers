(function() {
    'use strict';
    //service is called with new, therefore everything is binded to "this"
    angular.module('app').service('serviceName', serviceName);

    //serviceName.$inject = [''];
    function serviceName() {

        //http://stackoverflow.com/a/27117322
        var service = this; // jshint ignore:line

        service.variable = ;

        service.functionOne = function () {
        };

        service.functionTwo = function () {
        };
    }
})();