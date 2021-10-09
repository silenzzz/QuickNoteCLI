@ECHO OFF
FOR %%f IN (C:\Users\%USERNAME%\DeMmAgeSoft\QNC*.jar) DO (
    IF "%%~xf"==".jar" SET jar=%%f
)

java -jar %jar% %*