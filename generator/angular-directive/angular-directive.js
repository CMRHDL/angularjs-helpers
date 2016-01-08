'use strict';

var fs = require('fs');

var replaceStrings = {
  $App: process.argv[2],
  $Ctrl: process.argv[3] + 'Ctrl',
  $ServiceName: process.argv[3].toLowerCase(),
  $Directive: 'my' + process.argv[3],
}
var dir = replaceStrings.$ServiceName;
var name = replaceStrings.$ServiceName;

var controller, directive, service;

var cb = function(callBack) {
  return function (err, data) {
    if (err) {
      return console.log(err);
    }
    callBack(data.toString());
  }
}

var setController = function(data) { controller = data; fs.readFile('directive.js', cb(setDirective)); }
var setDirective = function(data) { directive = data; fs.readFile('service.js', cb(setService)); }
var setService = function(data) { service = data; replace(); }

fs.readFile('controller.js', cb(setController));

function replace() {
  var arr = [controller, directive, service];
  for(var key in replaceStrings) {
    var regEx = new RegExp(key,"g");
    controller = controller.replace(regEx, replaceStrings[key])
    directive = directive.replace(regEx, replaceStrings[key])
    service = service.replace(regEx, replaceStrings[key])
  }
  makeDir();
}

function makeDir() {
  fs.mkdir(replaceStrings.$ServiceName, function() { write(); });
}

function write() {
  fs.writeFile(dir + "/" + name + ".controller.js", controller);
  fs.writeFile(dir + "/" + name + ".js", directive);
  fs.writeFile(dir + "/" + name + ".service.js", service);
  fs.writeFile(dir + "/" + name + ".html", "");
}
