def location = new File(getClass().protectionDomain.codeSource.location.path).parent

Properties properties = new Properties()
File propertiesFile = new File(location + "/foo.properties")
propertiesFile.withInputStream {
    properties.load(it)
}

// write to/replace in/replace file
file.newWriter().withWriter { w -> w << inhalt }
new AntBuilder().replace(file: file, token: "if(", value: "if (")
new AntBuilder().replaceregexp(match: /[ \t]+$/, replace: '', byline:true, flags: 'g') {
   fileset(file: file)
}
  
// RegEx
def matcher = (currentLine =~ /(^\s*)/)
println matcher[0][0]
  
// Files -R
import groovy.io.FileType
new File('PATH').eachFileRecurse (FileType.FILES) { file ->
    file.eachLine { currentLine, lineNumber -> }
}

// SQL
import groovy.sql.Sql
this.getClass().classLoader.rootLoader.addURL(new File("driver.jar").toURL())
def sql = Sql.newInstance(
    'url',
    '<username>',
    '<password>',
    'oracle.jdbc.driver.OracleDriver'
)

def statement = "select foo.bar as bar from foo"

sql.eachRow(statement) { row ->
    println row.bar
}

sql.close()