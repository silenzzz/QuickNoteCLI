# QuickNoteCLI

<details>
<summary><b>Table of Contents</b></summary>
<!-- MarkdownTOC -->

* [Introduction](#Introduction)
    * [Usage](#Usage)
    * [Examples](#Examples)
* [Installation](#Installation)
    * [Build and install via Maven](#Build-and-Install-via-Maven)

<!-- /MarkdownTOC -->
</details>

## Introduction

QuickNoteCLI is a command line interface app for quick data saving.

### Usage

```
usage: QNC
 -a,--append         Append text to the last note
 -c,--clear          Clear all notes
 -d,--delete <arg>   Delete note by index or name
 -e,--erase          Erase last note
 -h,--help           Print help
 -l,--list           Print note list
 -n,--name <arg>     Specify note name
 -N,--nano           Open in nano editor (If installed)
 -p,--print          Print last note text
 -r,--rename <arg>   Rename last note
 -s,--show <arg>     Show note by name
 -S,--dbs            Start DB server

Run QNC without arguments to create new note
Please report issues at https://github.com/DeMmAge/QuickNoteCLI/issues/new
```

### Examples

<details>
<summary><b>Examples</b></summary>
<!-- MarkdownTOC -->

Write **NE** on new line to save note.

* **Create simple note with specified name**

```shell
QNC --name shoppint_list
Start typing text. To save write NE on new line
Tomatoes 5x
Beer 15x
NE
```

* **Append new content to the last note**

```shell
QNC --append
Start typing text. To save write NE on new line
Orange 10x
NE
```

* **Print last created note**

```shell
QNC --print
```

**Output:**

```shell
| HASH  | NAME          | DATE                      |
|===================================================|
| D41D8 | shoppint_list | 2021-10-09 17:40:33.39333 |

Tomatos 5x
Beer 15x
Orange 10x
```

* **Print all notes list**

```shell
QNC --list
```

**Output:**

```shell
    ___________________________________________________________________________
    | HASH  | NAME                | DATE                       | SHORT CONTENT |
    |==========================================================================|
 1. | 54D1A | test_specified_name | 2021-10-03 03:25:40.285    | NOTE CONTENT  |
 2. | 0762C | NOTE 03:32          | 2021-10-03 03:32:24.097    | NOTE CONTENT  |
 3. | 3F978 | note_name           | 2021-10-03 03:39:23.07     | NOTE CONTENT  |
 4. | 9970D | note_name_2         | 2021-10-03 03:43:09.645    | NOTE CONTENT  |
 5. | 01B82 | NOTE 03:49          | 2021-10-03 03:49:18.005    | NOTE CONTENT  |
 6. | 9970D | NOTE 05:57          | 2021-10-03 05:57:16.605006 | NOTE CONTENT  |
 7. | 83F56 | NOTE 07:24          | 2021-10-03 07:24:11.32427  | PASSWORD      |
 8. | 73FB9 | NOTE 07:25          | 2021-10-03 07:25:53.456844 | LOGIN         |
 9. | 7C670 | NOTE 07:27.00       | 2021-10-03 07:27:00.298042 | QUICK NOTE    |
10. | 88DFE | NOTE 07:31.03       | 2021-10-03 07:31:03.58814  | blablablabla  |
```

* **Print note by name or index in the list**

```shell
QNC --show test_specified_name
```

**Output:**

```shell
________________________________________________________
| HASH  | NAME                | DATE                    |
|=======================================================|
| 54D1A | test_specified_name | 2021-10-03 03:25:40.285 |


Facebook:
Login: MarkZuckerberg
Password: qwerty123

Instagram:
Login: MarkZuckerberg
Password: qwerty123
```

* **Create note in nano editor _(if installed)_.**

Just write your text in editor, press `CTRL + X`, press `Y`, and press `ENTER`.

**Output:**

```shell
QNC --name note_in_nano! -N
```

```
  GNU nano 2.5.3                                                           File: tmp18278661428566629933.tmp


    You can write content here!
    
    ¯\_(ツ)_/¯ 
    




         












                                                                                                                             [ Read 0 lines ]
^G Get Help      ^O Write Out     ^W Where Is      ^K Cut Text      ^J Justify       ^C Cur Pos       ^Y Prev Page     M-\ First Line   M-W WhereIs Next ^^ Mark Text     M-} Indent Text  M-U Undo         ^B Back          M-Space Prev Word^A Home
^X Exit          ^R Read File     ^\ Replace       ^U Uncut Text    ^T To Spell      ^_ Go To Line    ^V Next Page     M-/ Last Line    M-] To Bracket   M-^ Copy Text    M-{ Unindent TextM-E Redo         ^F Forward       ^Space Next Word ^E End
```

<!-- /MarkdownTOC -->
</details>

## Installation

### Build and Install via Maven

**Requirements**:

* Maven must be installed on your PC
* Java 8+

__Windows:__

```bat
cd dist
build.bat
install.bat
```

__UNIX:__

```shell
cd dist/
./build.sh
./install.sh
```