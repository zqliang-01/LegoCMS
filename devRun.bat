taskkill /fi "windowtitle eq Lego-CMS"  /f /t
call mvn -f legocms-parent clean package %*
start "Lego-CMS" java -jar legocms-parent\legocms\target\legocms.jar %*
