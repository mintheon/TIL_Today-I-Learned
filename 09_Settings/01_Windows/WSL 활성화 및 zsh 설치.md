#### 윈도우에 리눅스 환경 설치 (zsh 사용가능)

https://medium.com/@boystyou82/%EC%9C%88%EB%8F%84%EC%9A%B010-frontend-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85-2-d82b47136e63

#### 이클립스에 wsl 터미널 설정하기

`Windows` - `Preferences` - `Terminal` - `Local Terminal`  
`Add` 버튼 눌러서 설정
(name : wsl, path: C:\Windows\System32\wsl.exe, icon: D:\01_Develope\02_Util\bash-icon.png)

#### git-bash와 비슷하게 Context Menu에 등록하는 법

Step 1. On your desktop right click "New"->"Text Document" with name OpenGitBash.reg

Step 2. Right click the file and choose "Edit"

Step 3. Copy-paste the code below, save and close the file

Step 4. Execute the file by double clicking it

Note: You need administrator permission to write to the registry.

```bash
Windows Registry Editor Version 5.00
; Open files
; Default Git-Bash Location C:\Program Files\Git\git-bash.exe

[HKEY_CLASSES_ROOT\*\shell\Open Git Bash]
@="Open Git Bash"
"Icon"="C:\\Program Files\\Git\\git-bash.exe"

[HKEY_CLASSES_ROOT\*\shell\Open Git Bash\command]
@="\"C:\\Program Files\\Git\\git-bash.exe\" \"--cd=%1\""

; This will make it appear when you right click ON a folder
; The "Icon" line can be removed if you don't want the icon to appear

[HKEY_CLASSES_ROOT\Directory\shell\bash]
@="Open Git Bash"
"Icon"="C:\\Program Files\\Git\\git-bash.exe"

[HKEY_CLASSES_ROOT\Directory\shell\bash\command]
@="\"C:\\Program Files\\Git\\git-bash.exe\" \"--cd=%1\""

; This will make it appear when you right click INSIDE a folder
; The "Icon" line can be removed if you don't want the icon to appear

[HKEY_CLASSES_ROOT\Directory\Background\shell\bash]
@="Open Git Bash"
"Icon"="C:\\Program Files\\Git\\git-bash.exe"

[HKEY_CLASSES_ROOT\Directory\Background\shell\bash\command]
@="\"C:\\Program Files\\Git\\git-bash.exe\" \"--cd=%v.\""
```

해당 순서대로 진행하면 되나 `Icon` 경로는 내가 설정할 `.ico`경로로 설정하고, `command` 폴더의 (defaultValue)의 데이터는 `bash.exe`로만 설정하면 된다.  
해당 코드로 `reg`파일 만들어서 처리해도 됨.

[출처](https://stackoverflow.com/questions/24386657/how-to-add-a-open-git-bash-here-context-menu-to-the-windows-explorer/44019893#44019893)
