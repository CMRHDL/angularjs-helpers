def location = new File(getClass().protectionDomain.codeSource.location.path).parent

createFiles();
createTemplates();
def routes = createRouting(location);
def includesAndEntrys = createIncludesAndEntrys();
def includes = includesAndEntrys[0];
def navbarEntrys = includesAndEntrys[1];

def app = new File("app.js")

app.text = """(function() {

    function config(\$routeProvider){
      \$routeProvider
${routes}        .otherwise({
          redirectTo:'/'
        });
    }

    angular.module('${this.args[0]}',['ngRoute']);
    angular.module('${this.args[0]}').config(config);

}());
"""

new File("navbar/navbar.html").text = new File(location + "/navbar.html").text
new File("style/style.css").text = new File(location + "/style.css").text
new File("navbar/navbar.directive.js").text = new File(location + "/navbar.js").text.replace(/QQappQQ/, this.args[0])
new File("../test/spec/controllers/navbar.controller.js").text = new File(location + "/navbar.spec.js").text.replace(/QQappQQ/, this.args[0])
new File("index.html").text = new File(location + "/index.html").text.replace(/QQappQQ/, this.args[0]).replace(/QQincludeQQ/, includes)
new File("navbar/navbar.controller.js").text =  new File(location + "/navbar.controller.js").text.replace(/QQappQQ/, this.args[0]).replace(/QQnavbarEntrysQQ/, navbarEntrys)

def createFiles() {
    new File("../test/spec/controllers/about.js").delete()
    new File("../test/spec/controllers/main.js").delete()
    new File("navbar").mkdir()
    new File("provider").mkdir()
    new File("style").mkdir()
    new File("index.html").createNewFile()
    new File("app.js").createNewFile()
    new File("style/style.css").createNewFile()
    new File("navbar/navbar.html").createNewFile()
    new File("navbar/navbar.controller.js").createNewFile()
    new File("navbar/navbar.directive.js").createNewFile()
    new File("../test/spec/controllers/navbar.controller.js").createNewFile()
}

def createTemplates() {
    this.args[1..this.args.length-1].each { createTemplate(it) }
}

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

def createRouting(location) {
    def argsOneCap = this.args[1].capitalize()
    def whens =  new File(location + "/firstRoute.txt").text.replace(/QQargs1QQ/, this.args[1]).replace(/QQargs1CapQQ/, argsOneCap)

    if(this.args.length > 2) {
        for (def entry in this.args[2..(this.args.length-1)]) {
            def entryCap = entry.capitalize()
            whens += new File(location + "/route.txt").text.replace(/QQentryQQ/, entry).replace(/QQentryCapQQ/, entryCap)
        }
    }
    return whens;
}

def createIncludesAndEntrys() {
    def cssComponents = [
        'glyphicon glyphicon-plus',
        'glyphicon glyphicon-tint',
        'glyphicon glyphicon-envelope',
        'glyphicon glyphicon-thumbs-up',
        'glyphicon glyphicon-pencil',
        'glyphicon glyphicon-apple',
        'glyphicon glyphicon-ok',
        'glyphicon glyphicon-tag',
        'glyphicon glyphicon-facetime-video'
    ]
    def includes = ""
    def navbarEntrys = ""
    for (def entry in this.args[1..(this.args.length-1)]) {
        def entryCap = entry.capitalize()
        def index = new Random().nextInt(cssComponents.size())
        def css = cssComponents[index]
        cssComponents = cssComponents - css
        includes += "\t<script src=\"${entry}/${entry}.controller.js\"></script>\n"
        navbarEntrys += """            {visibleName: '${entryCap}', name: '${entry}', css: '${css}'},\n"""
    }
    
    return [includes, navbarEntrys];
}
