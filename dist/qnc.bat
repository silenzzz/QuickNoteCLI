@ECHO OFF
FOR %%f IN (QNC*.jar) DO (
    IF "%%~xf"==".jar" java -jar %%f $@
)