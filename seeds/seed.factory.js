(function() {
    'use strict';
    //factory returns something. methods or objects.
    angular.module('app').factory('factoryName', factoryName);

    //factoryName.$inject = [''];
    function factoryName() {

        var factoryObject = {};

        factoryObject.variable = ;

        factoryObject.functionOne = function() {
        };

        factoryObject.functionTwo = function() {
        };

        factoryObject.functionThree = function() {
        };

        return factoryObject;
    }
})();