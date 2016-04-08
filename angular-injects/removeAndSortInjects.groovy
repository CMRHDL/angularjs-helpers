import java.util.regex.Matcher
import groovy.io.FileType

def location = new File(getClass().protectionDomain.codeSource.location.path).parent

Properties properties = new Properties()
File propertiesFile = new File(location + "/injects.properties")
propertiesFile.withInputStream {
    properties.load(it)
}

def path = properties.path
def ignores = properties.ignorefilenames.split(',')

new File(path).eachFileRecurse (FileType.FILES) { file ->
    if(validFilename(file, ignores)) {
        checkForInjects(file, file.text)
    }
}

def validFilename(file, ignores) {
    def valid = true;
    ignores.each { entry ->
        if(file.name.contains(entry)) {
            valid = false;
        }
    }
    return valid && file.name.contains('.js')
}

def checkForInjects(file, content) {
    def matcher = content =~ /(\s*?)(.*?)(\..inject.*\])/
    if(matcher && matcher[0][3]) {
        def functionName = matcher[0][2]
        def injectString = matcher[0][3]
        getAllInjects(file, content, functionName, injectString);
    }
}

def getAllInjects(file, content, functionName, injectString) {
    def injects = [];
    def matcher = injectString =~ /'(.*?)'/
    if(matcher && matcher[0][0]) {
        matcher.findAll { entry ->
            injects.push(entry[1])
        }
    }
    getInjectBlock(file, content, functionName, injects, injectString)
}

def getInjectBlock(file, content, functionName, injects, injectString) {
    def matcher = injectString =~ /(\[.*?\])/
    if(matcher[0][0].replace(/ /, '').length() > 2) {
        injectBlock = matcher[0][0]
        getUsedInjects(file, content, functionName, injects, injectBlock)
    }
}

def getUsedInjects(file, content, functionName, injects, injectBlock) {
    def usedInjects = []
    injects.each { entry ->
        content = content.replace(/$/, 'DOLLAR')
        def matcher = content =~ /(${entry.replace(/$/, 'DOLLAR')}(\.|\())/
        content = content.replace(/DOLLAR/, '$')
        if(matcher && matcher[0][1]) {
            usedInjects.push(entry);
        }
    }
    usedInjects = usedInjects.sort()
    replaceInjects(file, content, functionName, usedInjects, injectBlock)
}

def replaceInjects(file, content, functionName, usedInjects, injectBlock) {
    def functionBlock = findFunctionBlock(content, functionName)
    def sortedInjectBlock = createSortedInjectBlock(usedInjects)
    def sortedFunctionBlock = createSortedFunctionBlock(usedInjects)
    content = content.replace("${injectBlock}", sortedInjectBlock)
    content = content.replace("${functionBlock}", sortedFunctionBlock)

    file.newWriter().withWriter {w -> w << content}
}

def findFunctionBlock(content, functionName) {
    def matcher = content =~ /function ${functionName.trim()}(\(.*?\))/
    if(matcher && matcher[0][1]) {
        return matcher[0][1]
    }
}

def createSortedInjectBlock(usedInjects) {
    def sortedInjectBlock = '[ '
    usedInjects.each { entry ->
        sortedInjectBlock += '\'' + entry + '\', '
    }
    sortedInjectBlock += ']'
    sortedInjectBlock = sortedInjectBlock.replace(', ]', ' ]')
    return sortedInjectBlock;
}

def createSortedFunctionBlock(usedInjects) {
    def sortedFunctionBlock = '('
    usedInjects.each { entry ->
        sortedFunctionBlock += entry + ', '
    }
    sortedFunctionBlock += ')'
    sortedFunctionBlock = sortedFunctionBlock.replace(', )', ')')
    return sortedFunctionBlock;
}
