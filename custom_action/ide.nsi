!include "MUI2.nsh"
!define MUI_ICON "D:\current_work\paqt_builder\paqt_builder_v1\src\assets\icon.ico"
!define MUI_HEADERIMAGE 
!define MUI_HEADERIMAGE_BITMAP "D:\current_work\paqt_builder\paqt_builder_v1\src\assets\install.bmp"
!define MUI_WELCOMEFINISHPAGE_BITMAP "D:\current_work\paqt_builder\paqt_builder_v1\src\assets\install.bmp"
!define MUI_HEADERIMAGE_RIGHT 
!define MUI_WELCOMEPAGE_TEXT "This is the installation wizard of Paqt: package installer builder engine. please follow on-screen instruction to get installed. thank you"
!define MUI_FINISHPAGE_TEXT "Thank you so much for installing our software, please report bug if you find any at stargadgetweb@gmail.com"
!define PRODUCT_VERSION "1.0.0.0"
!define VERSION "1.0.0.0"
VIProductVersion "${VERSION}"
VIFileVersion "${VERSION}"
VIAddVersionKey "FileVersion" "${VERSION}"
VIAddVersionKey "LegalCopyright" "copyright 2021 @ Appska && Mohammad Arshad"
VIAddVersionKey "FileDescription" "Developed by Appska  stargadgetweb@gmail.com"
Function .onInit
Exec "D:\current_work\paqt_builder\paqt_builder_v1\custom_action\action.bat"
FunctionEnd
Function finishpageaction
CreateShortcut "$desktop\paqt.lnk" "$INSTDIR\paqt.exe"
FunctionEnd
!define MUI_FINISHPAGE_SHOWREADME 
!define MUI_FINISHPAGE_SHOWREADME_NOTCHECKED 
!define MUI_FINISHPAGE_SHOWREADME_TEXT "Add desktop and taskbar shortcut"
!define MUI_FINISHPAGE_SHOWREADME_FUNCTION finishpageaction
Name "Paqt"
OutFile "E:\arachni-1.5.1-0.5.12-windows-x86_64\Paqt.exe"
Unicode true
BrandingText "Built with PAQT installer builder engine"
InstallDir "C:\appska"
InstallDirRegKey HKLM "software\Paqt" "$INSTDIR"
RequestExecutionLevel admin
!define MUI_ABORTWARNING
!insertmacro MUI_PAGE_WELCOME 
!insertmacro MUI_PAGE_LICENSE "D:\current_work\paqt_builder\paqt_builder_v1\custom_action\license.txt"
!insertmacro MUI_PAGE_DIRECTORY 
!insertmacro MUI_PAGE_INSTFILES 
!insertmacro MUI_PAGE_FINISH 
!insertmacro MUI_UNPAGE_WELCOME 
!insertmacro MUI_UNPAGE_CONFIRM 
!insertmacro MUI_UNPAGE_INSTFILES 
!insertmacro MUI_UNPAGE_FINISH 
!insertmacro MUI_LANGUAGE "English"
Section ""
SetOutPath "$INSTDIR"
File /r "D:\current_work\paqt_builder\production_paqt\"*.*
WriteRegStr HKLM "software\Paqt" "Install_Dir" "$INSTDIR"
WriteUninstaller "$INSTDIR\Uninstall.exe"
WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Paqt" "DisplayName" "Paqt"
WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Paqt" "UninstallString" "$INSTDIR\Uninstall.exe"
SectionEnd
Section "Start Menu Shortcuts"
CreateDirectory "$SMPROGRAMS\Paqt"
CreateShortcut "$SMPROGRAMS\Paqt\uninstall Paqt.lnk" "$INSTDIR\uninstall.exe"
CreateShortcut "$SMPROGRAMS\Paqt\paqt.lnk" "$INSTDIR\paqt.exe"
SectionEnd
Section "Uninstall"
Delete "$INSTDIR\Uninstall.exe"
Delete "$INSTDIR\paqt.exe"
RMDir /r "$INSTDIR"
DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Paqt"
Delete "$SMPROGRAMS\Paqt\*.lnk"
SectionEnd
