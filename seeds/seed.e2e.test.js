(function() {
    'use strict';

    var TestPage = require('./seed.testPage.js');

    describe('app', function() {
        var page;

        beforeEach(function() {
            page = new TestPage();
        });

        afterEach(function() {
            browser.driver.get('logoutpage');
        });

        describe('Something', function() {
            it('should do something', function() {
                page.button.click();
                browser.driver.sleep(1000);
                expect(page.someColumn.getText()).toBe('something');
            });
        });
    });
})();