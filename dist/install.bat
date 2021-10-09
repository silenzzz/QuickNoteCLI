@ECHO OFF

SET dir=C:\Users\%USERNAME%\DeMmAgeSoft

IF exist %dir% ( goto DirExists) ELSE ( goto DirNotExists )

:DirNotExists
echo Creating directories...
MKDIR %dir%

:DirExists

echo Copying files...
COPY /Y ..\target\QNC*.jar %dir%\QNC*.jar
COPY /Y qnc.bat %dir%\qnc.bat

echo Adding to path...
Powershell.exe -File AddToPath.ps1 %dir%

IF %ERRORLEVEL% NEQ 0 ( goto Error ) ELSE ( goto Susses )

:Error
echo ERROR OCCURRED DURING INSTALLATION
pause
exit /b 1

:Susses
echo INSTALLATION SUCCESSFUL
pause
exit /b 0