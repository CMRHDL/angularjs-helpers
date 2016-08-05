// CLI
import org.apache.commons.cli.*

// http://mrhaki.blogspot.de/2009/09/groovy-goodness-parsing-commandline.html
def cli = new CliBuilder(usage: 'showdate.groovy -[chflms] [date] [prefix]')
// Create the list of options.
cli.with {
    h longOpt: 'help', 'Show usage information'
    c longOpt: 'format-custom', args: 1, argName: 'format', 'Format date with custom format defined by "format"'
}

def options = cli.parse(args)
if (!options) {
    return
}
// Show usage text when -h or --help option is used.
if (options.h) {
    cli.usage()
    return
}


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

def statement = "SELECT * FROM ah_ersuchen"

sql.rows(statement) { meta ->
  colNames = (1..meta.columnCount).collect {
    meta.getColumnName(it)
  }
}
colNames.each { name ->
  println name
}

sql.close()

// unzip + copy
def ant = new AntBuilder()
ant.unzip( src: 'foo.gz', dest: 'bar', overwrite: 'false' )
ant.copy( file: new File(location + '/some.css'), tofile: new File(otherLocation + 'newname.css'))

// newest file in folder
def newestFile = new File( downloadPath ).listFiles()?.sort { -it.lastModified() }?.head()
