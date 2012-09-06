javac -d %1 -cp ".\;..\libs\jfreechart-1.0.14.jar;..\libs\joda-time-2.0.jar;..\libs\gson-1.7.1.jar;..\libs\tritonus_share.jar;..\libs\jl1.0.1.jar;..\libs\jcommon-1.0.17.jar;..\libs\sqlite-jdbc-3.7.3.jar;..\libs\jl1.0.1.jar" @classes
cd %1
del proj.jar
jar cvfm proj.jar manifest.txt . .\libs\ .\imgs\
