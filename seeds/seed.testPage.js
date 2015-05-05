(function() {
    'use strict';

    var TestPage = function () {
        browser.driver.manage().window().maximize();
        browser.driver.get('startpage');
        browser.driver.findElement(by.css('[value="Anmelden"]')).click();
        browser.get('destination');
    };

    TestPage.prototype = Object.create({}, {
        //http://www.thoughtworks.com/insights/blog/using-page-objects-overcome-protractors-shortcomings
        domElement: { get: function () { return browser.driver.findElement(by.css('')); }},
        domElement: { get: function () { return browser.driver.findElement(by.id('')); }},
        domElement: { get: function () { return element(by.model('')); }},
        domElement: { get: function () { return element(by.css('')); }},
        domElement: { get: function () { return element(by.binding('')).getText(); }},
        domElementList: { get: function () { return element.all(by.repeater('element in object')); }}
    });

    module.exports = TestPage;
})();