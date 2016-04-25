import groovy.io.FileType

location = new File(getClass().protectionDomain.codeSource.location.path).parent

createFolders();
createTemplates();
createRouting();
createIncludesAndEntrys();

new File("app.js").text = new File(location + "/app.js").text.replace(/QQappQQ/, this.args[0]).replace(/QQroutesQQ/, routes)

new File("navbar/navbar.html").text = new File(location + "/navbar.html").text
new File("style/style.css").text = new File(location + "/style.css").text
new File("navbar/navbar.directive.js").text = new File(location + "/navbar.js").text.replace(/QQappQQ/, this.args[0])
new File("navbar/navbar.controller.spec.js").text = new File(location + "/navbar.spec.js").text.replace(/QQappQQ/, this.args[0])
new File("index.html").text = new File(location + "/index.html").text.replace(/QQappQQ/, this.args[0]).replace(/QQincludeQQ/, includes)
new File("navbar/navbar.controller.js").text =  new File(location + "/navbar.controller.js").text.replace(/QQappQQ/, this.args[0]).replace(/QQnavbarEntrysQQ/, navbarEntrys)

def createFolders() {
    /*new File("../test/spec/controllers").eachFileRecurse (FileType.FILES) { file ->
        file.delete()
    }*/
    new File("navbar").mkdir()
    new File("provider").mkdir()
    new File("style").mkdir()
    //println new File("../test/karma.conf.js").text
    //new File("../test/karma.conf.js").text.replace(/test\/mock\/\*\*\/\*\.js/, "app/**/*.spec.js")
    //new File("../test/karma.conf.js").text.replace(/'test\/spec\/\*\*\/\*\.js'/, "")
}

def createTemplates() {
    this.args[1..this.args.length-1].each { createTemplate(it) }
}

def createTemplate(entry) {
    new File(entry).mkdir()
    new File("${entry}/${entry}.html").createNewFile()
    new File("${entry}/${entry}.controller.js").createNewFile()
    new File("${entry}/${entry}.controller.spec.js").createNewFile()

    def html = new File("${entry}/${entry}.html")
    def js = new File("${entry}/${entry}.controller.js")
    def spec = new File("${entry}/${entry}.controller.spec.js")

    def entryCap = entry.capitalize()

    js.text = new File(location + "/templates.js").text.replace(/QQappQQ/, this.args[0]).replace(/QQentryCapQQ/, entryCap)

    html.text = """<div class="jumbotron">
   <h1>${entryCap}</h1>
</div>"""

    spec.text = new File(location + "/spec.js").text.replace(/QQappQQ/, this.args[0]).replace(/QQentryCapQQ/, entryCap)
}

def createRouting() {
    def argsOneCap = this.args[1].capitalize()
    routes =  new File(location + "/firstRoute.txt").text.replace(/QQargs1QQ/, this.args[1]).replace(/QQargs1CapQQ/, argsOneCap)

    if(this.args.length > 2) {
        for (def entry in this.args[2..(this.args.length-1)]) {
            def entryCap = entry.capitalize()
            routes += new File(location + "/route.txt").text.replace(/QQentryQQ/, entry).replace(/QQentryCapQQ/, entryCap)
        }
    }
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
    includes = ""
    navbarEntrys = ""
    for (def entry in this.args[1..(this.args.length-1)]) {
        def entryCap = entry.capitalize()
        def index = new Random().nextInt(cssComponents.size())
        def css = cssComponents[index]
        cssComponents = cssComponents - css
        includes += "\t<script src=\"${entry}/${entry}.controller.js\"></script>\n"
        navbarEntrys += """            {visibleName: '${entryCap}', name: '${entry}', title: '${entryCap}', css: '${css}'},\n"""
    }
}
