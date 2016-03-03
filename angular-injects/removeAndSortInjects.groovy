import java.util.regex.Matcher
import groovy.io.FileType

/**
 * Remove unused Dependency Injections and sort remaining Dependency Injections
 * @author Jonathan Fuchs, Christoph Müller
 */

def location = new File(getClass().protectionDomain.codeSource.location.path).parent

def path = new File(location + "/path.txt").text
ignores = new File(location + "/ignorefilenames.txt").text.split(',')

new File(path).eachFileRecurse (FileType.FILES) { file ->
    if(validFilename(file)) {
        println file.name
        proceedFurther = true
        content = ""
        content = file.text
        checkForInjects(file)
        if(proceedFurther) { getAllInjects(); }
        if(proceedFurther) { getInjectBlock(); }
        if(proceedFurther) { getUsedInjects(); }
        if(proceedFurther) { sortUsedInjects(); }
        if(proceedFurther) { replaceInjects(); }
        if(proceedFurther) { writeNewContent(file); }
    }
}

def validFilename(file) {
    def valid = true;
    ignores.each { entry ->
        if(file.name.contains(entry)) {
            valid = false;
        }
    }
    return valid && file.name.contains('.js')
}

def checkForInjects(file) {
    def matcher = content =~ /(\s*?)(.*?)(\..inject.*\])/
    if(matcher && matcher[0][3]) {
        functionName = matcher[0][2]
        injectString = matcher[0][3]
    } else {
        injectString = ''
        proceedFurther = false
    }
}

def getAllInjects() {
    def list = [];
    def matcher = injectString =~ /'(.*?)'/
    if(matcher && matcher[0][0]) {
        matcher.findAll { entry ->
            list.push(entry[1])
        }
    }
    injects = list;
}

def getInjectBlock() {
    def matcher = injectString =~ /(\[.*?\])/
    if(matcher[0][0].replace(/ /, '').length() > 2) {
        injectBlock = matcher[0][0]
    } else {
        injectBlock = ''
        proceedFurther = false
    }
}

def getUsedInjects() {
    usedInjects = []
    injects.each { entry ->
        content = content.replace(/$/, 'DOLLAR')
        def matcher = content =~ /(${entry.replace(/$/, 'DOLLAR')}(\.|\())/
        content = content.replace(/DOLLAR/, '$')
        if(matcher && matcher[0][1]) {
            usedInjects.push(entry);
        }
    }
}

def sortUsedInjects() {
    usedInjects = usedInjects.sort()
}

def replaceInjects() {
    findFunctionBlock()
    createSortedInjectBlock()
    createSortedFunctionBlock()
    content = content.replace("${injectBlock}", sortedInjectBlock)
    content = content.replace("${functionBlock}", sortedFunctionBlock)
}

def findFunctionBlock() {
    def matcher = content =~ /function ${functionName.trim()}(\(.*?\))/
    if(matcher && matcher[0][1]) {
        functionBlock = matcher[0][1]
    }
}

def createSortedInjectBlock() {
    sortedInjectBlock = '[ '
    usedInjects.each { entry ->
        sortedInjectBlock += '\'' + entry + '\', '
    }
    sortedInjectBlock += ']'
    sortedInjectBlock = sortedInjectBlock.replace(', ]', ' ]')
}

def createSortedFunctionBlock() {
    sortedFunctionBlock = '('
    usedInjects.each { entry ->
        sortedFunctionBlock += entry + ', '
    }
    sortedFunctionBlock += ')'
    sortedFunctionBlock = sortedFunctionBlock.replace(', )', ')')
}

def writeNewContent(file) {
    file.newWriter().withWriter {w -> w << content}
}