@echo off
setlocal enabledelayedexpansion

REM Caminho até a pasta 'main' 
cd /d "C:\Users\Cliente\nicolegrazzioli\redes-webserver"

set "PASTA=."
set "ARQUIVO_SAIDA=webserver-CODES.txt"

REM Apagar arquivo anterior, se existir
if exist "%ARQUIVO_SAIDA%" del "%ARQUIVO_SAIDA%"

REM Percorrer todos os arquivos .java e .jsp na pasta e subpastas
for /R "%PASTA%" %%F in (*.java *.html) do (
    echo - Endereço: %%F:>>"%ARQUIVO_SAIDA%"
    type "%%F" >>"%ARQUIVO_SAIDA%"
    echo.>>"%ARQUIVO_SAIDA%"
    echo.>>"%ARQUIVO_SAIDA%"
)

echo Finalizado. Arquivo gerado: %CD%\%ARQUIVO_SAIDA%
pause
