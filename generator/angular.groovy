new File("index.html").createNewFile()
new File("app.js").createNewFile()

new File("../test/spec/controllers/about.js").delete()
new File("../test/spec/controllers/main.js").delete()

new File("navbar").mkdir()
new File("navbar/navbar.html").createNewFile()
new File("navbar/navbar.controller.js").createNewFile()
new File("navbar/navbar.directive.js").createNewFile()
new File("../test/spec/controllers/navbar.controller.js").createNewFile()

new File("navbar/navbar.html").text = """
<div class="header">
  <div class="navbar navbar-default navbar-fixed-top">
    <ul class="nav navbar-nav">
      <li ng-repeat="tab in nav.tabs" ng-class="[nav.isTabActive(tab.name)]" ng-click="nav.setActiveTab(tab.name)" >
        <a ng-href="#/{{tab.name}}"><span class="{{tab.css}}"></span> {{tab.visibleName}}
        </a>
      </li>
    </ul>
  </div>
</div>
"""

new File("navbar/navbar.directive.js").text = """
(function() {
  'use strict';
  angular.module('${this.args[0]}').directive('myNav', myNav);

  //myNav. = [ '' ];
  function myNav() {
    return {
      restrict: 'E',
      templateUrl: 'navbar/navbar.html',
      controller: 'NavbarCtrl',
      controllerAs: 'nav',
      link: function(scope, element, attrs, tabsCtrl) {
      },
    };
  }
})();
"""

new File("../test/spec/controllers/navbar.controller.js").text = """'use strict';

describe('Controller: NavbarCtrl', function () {

  beforeEach(module('${this.args[0]}'));

  var NavbarCtrl, scope;

  beforeEach(inject(function (\$controller, \$rootScope) {
    scope = \$rootScope.\$new();
    NavbarCtrl = \$controller('NavbarCtrl', {
      \$scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(scope.awesomeThings.length).toBe(3);
  });
});

"""

this.args[1..this.args.length-1].each { createTemplate(it) }

def createTemplate(entry) {
    new File(entry).mkdir()
    new File("${entry}/${entry}.html").createNewFile()
    new File("${entry}/${entry}.controller.js").createNewFile()
    new File("../test/spec/controllers/${entry}.controller.js").createNewFile()

    def html = new File("${entry}/${entry}.html")
    def js = new File("${entry}/${entry}.controller.js")
    def spec = new File("../test/spec/controllers/${entry}.controller.js")
    
    def entryCap = entry.capitalize()
    
    js.text = """(function() {
    'use strict';
    angular.module('${this.args[0]}').controller('${entryCap}Ctrl', ${entryCap}Ctrl);

    //${entryCap}Ctrl.\$inject = [ '' ];
    function ${entryCap}Ctrl() {
        var vm = this;

        // variables
        vm.var = '';

        // public functions
        vm.someFunctionOne = someFunctionOne;

        function someFunctionOne() {
        }

    }
})();
"""
    html.text = """<div class="jumbotron">
   <h1>${entryCap}</h1>
</div>"""
    
    spec.text = """'use strict';

describe('Controller: ${entryCap}Ctrl', function () {

  beforeEach(module('${this.args[0]}'));

  var ${entryCap}Ctrl, scope;

  beforeEach(inject(function (\$controller, \$rootScope) {
    scope = \$rootScope.\$new();
    ${entryCap}Ctrl = \$controller('${entryCap}Ctrl', {
      \$scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(scope.awesomeThings.length).toBe(3);
  });
});
"""

}

def includes = ""
def argsOneCap = this.args[1].capitalize()
def whens = """        .when('/', {
          templateUrl: '${this.args[1]}/${this.args[1]}.html',
          controller : '${argsOneCap}Ctrl',
          controllerAs : 'vm',
        })
        .when('/${this.args[1]}', {
          templateUrl: '${this.args[1]}/${this.args[1]}.html',
          controller : '${argsOneCap}Ctrl',
          controllerAs : 'vm',
        })
"""

def navbarEntrys = ""

for (def entry in this.args[1..(this.args.length-1)]) {
    def entryCap = entry.capitalize()
    includes += "\t<script src=\"${entry}/${entry}.controller.js\"></script>\n"
    navbarEntrys += """            {visibleName: '${entryCap}', name: '${entry}', css: ''},
"""
}

for (def entry in this.args[2..(this.args.length-1)]) {
    def entryCap = entry.capitalize()
    whens += """        .when('/${entry}', {
          templateUrl: '${entry}/${entry}.html',
          controller : '${entryCap}Ctrl',
          controllerAs : 'vm',
        })
"""
}

def index = new File("index.html")
def app = new File("app.js")

index.text = """<!doctype html>
<html ng-app="${this.args[0]}">
<head>
    <link rel="stylesheet" href="../bower_components/bootstrap/dist/css/bootstrap.css" />
</head>
<body>
    <my-nav></my-nav>

    <div ng-view></div>

    <div class="footer">
        <p><span class="glyphicon glyphicon-heart"></span>Angular</p>
    </div>

    <script src="../bower_components/angular/angular.js"></script>
    <script src="../bower_components/angular-route/angular-route.js"></script>
    <script src="app.js"></script>
    <script src="navbar/navbar.directive.js"></script>
    <script src="navbar/navbar.controller.js"></script>
${includes}
</body>
</html>
"""

app.text = """(function() {

    function config(\$routeProvider){
      \$routeProvider
${whens}        .otherwise({
          redirectTo:'/'
        });
    }

    angular.module('${this.args[0]}',['ngRoute']);
    angular.module('${this.args[0]}').config(config);

}());
"""

new File("navbar/navbar.controller.js").text = """(function() {
    'use strict';
    angular.module('${this.args[0]}').controller('NavbarCtrl', NavbarCtrl);

    NavbarCtrl.\$inject = [ '\$location' ];
    function NavbarCtrl(\$location) {
        var nav = this;


        var activeTab = \$location.url().substr(1,\$location.url().length) !== '' ? \$location.url().substr(1,\$location.url().length) : 'overview';

        nav.isTabActive = isTabActive;
        nav.setActiveTab = setActiveTab;

        nav.tabs = [
${navbarEntrys}
        ];

        function isTabActive(tab) {
          return activeTab === tab ? 'active' : '';
        }
        function setActiveTab(tab) {
          activeTab = tab;
        }
    }
})();
"""

