# Smart Text editor v. 1.2
SmartText is a simple text editor, but with version 2, a SmartUndo system was added. This system allows users to track and manage the changes they have made to the document by grouping the edits together and undo/discard


## Changelog
April 15, 2021 - include the Smart Undo function

## SmartUndo
Features:
- Sidebar contains history of edits and groups of edits.
- Can select edits (one or many) and undo them all at once
- Can create a group of edits and select which edits to add/remove
- Can undo all edits in a group
- Can delete the record of the edit from SmartUndo so it cannot be undone via SmartUndo
- Can delete groups and/or all the edits in that group

## Set Up Instructions
Git pull from : https://github.com/amiani/smarttext.git

Run the available .jar file or open in a compiler.

Requires Java.

All files found under smarttext/src/main.

## Information
This was developed for COMP354 at Concordia University in the Winter 2021 session.
The team members are:
Seth C. Cole
Amiani Johns
Marc-Andre Meza
Jaime Hechavarria
Brandon Delaportas
Samuel Cardin

## References
The base program was designed by Seth Kenlon and can be found [here](https://opensource.com/article/20/12/write-your-own-text-editor)
