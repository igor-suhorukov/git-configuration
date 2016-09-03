import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader
@Grab(group='org.codehaus.plexus', module='plexus-archiver', version='2.10.2')
import org.codehaus.plexus.archiver.zip.ZipUnArchiver
@Grab(group='org.codehaus.plexus', module='plexus-container-default', version='1.6')
import org.codehaus.plexus.logging.console.ConsoleLogger
@Grab(group = 'org.eclipse.jetty', module = 'jetty-runner', version = '9.3.7.RC1' )
import org.eclipse.jetty.runner.Runner

def gitblit = new File(MavenClassLoader.using('http://gitblit.github.io/gitblit-maven').resolveArtifact('com.gitblit:gitblit:war:1.7.1').getFile())

File gitblitDirectory = new File(System.getProperty('user.home'), gitblit.getName().replace('.war',''))

if(!gitblitDirectory.exists()){
    gitblitDirectory.mkdir()
    ZipUnArchiver unArchiver = new ZipUnArchiver()
    unArchiver.setSourceFile(gitblit)
    unArchiver.enableLogging(new ConsoleLogger(ConsoleLogger.LEVEL_DEBUG,"Logger"))
    unArchiver.setDestDirectory(gitblitDirectory)
    unArchiver.extract()

    def dataPath = new File(System.getProperty('user.home'), '.gitblit_data')
    if(!dataPath.exists()){ dataPath.mkdir() }
    def webXml = new File(gitblitDirectory.getAbsoluteFile(), 'WEB-INF/web.xml')
    webXmlText = webXml.text
    webXml.withWriter { w -> w << webXmlText.replace('${contextFolder}/WEB-INF/data', dataPath.getAbsolutePath()) }
}

Runner.main([gitblitDirectory] as String[])
