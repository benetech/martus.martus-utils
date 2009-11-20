repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://artifacts.miradi.org/repo/'

my_layout = Layout.new
my_layout[:source, :main, :java] = 'source'
my_layout[:target, :test, :java] = my_layout[:target, :main, :java]


define "martus-utils", :layout=>my_layout do
  project.version = '1'

  compile.options.target = '1.5'
  compile.with(
  	'junit:junit:jar:3.8.2',
  	'persiancalendar:persiancalendar:jar:2.1',
  	'com.ibm.icu:icu4j:jar:3.4.4'
  )

  package :jar

end


