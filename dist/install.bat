@echo off

set dir=C:\Users\%USERNAME%\DeMmAgeSoft

if exist %dir% ( goto Exists ) ELSE ( goto NotExists )

:NotExists
echo Creating directories...
MKDIR %dir%

:Exists

echo Copying files...
copy /Y QNC*.jar %dir%\QNC*.jar
copy /Y .\util\QNC.bat %dir%\QNC.bat

echo Adding to PATH...
Powershell.exe -File .\util\AddToPath.ps1 %dir%

if %ERRORLEVEL% NEQ 0 ( goto Error ) ELSE ( goto Success )

:Error
echo ERROR OCCURRED DURING INSTALLATION
exit /b 1

:Success
echo INSTALLATION SUCCESSFUL
exit /b 0

::todo Short instruction