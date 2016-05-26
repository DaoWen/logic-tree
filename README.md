Logic Tree v1.0 Documentation
=============================

![Logic Tree Logo](./docs/docPics/TreeLogo.gif)

Table of Contents
-----------------

-   [Program Information](#program-information)
-   [Expression Parser Syntax](#expression-parser-syntax)
-   [Creating a New Expression tree](#creating-a-new-expression-tree)
-   [Animating the Expression Tree](#animating-the-expression-tree)
-   [Changing the Display Colors](#changing-the-display-colors)
-   [Exporting an Image](#exporting-an-image)
-   [Displaying the Truth Table](#displaying-the-truth-table)
-   [Contact and Support](#contact-and-support)

Program Information
-------------------

![Image of Philosophical GNU](./docs/docPics/philGNU.gif)

Logic Tree is a free program for modeling complex logic gate networks. Logic Tree displays and animates representations of Boolean expressions inputted by the user. This program is free to edit and redistribute under the conditions outlined in the [GNU General Public License](http://www.gnu.org/copyleft/gpl.html) agreement.

Expression Parser Syntax
------------------------

Logic Tree's expression parser accepts Boolean algebra expressions written in infix notation. There are eight supported Boolean operators:

| Symbol | Function                    | Symbol | Function                             |
|--------|-----------------------------|--------|--------------------------------------|
| AND    | Binary And Operator         | OR     | Binary Or Operator                   |
| NAND   | Inverse Binary And Operator | NOR    | Inverse Binary Or Operator           |
| NOT    | Unary Not Operator          | XOR    | Binary Exclusive Or Operator         |
| ( )    | Parenthesis                 | NXOR   | Inverse Binary Exclusive Or Operator |

Operator precedence from lowest to highest is: `XOR`/`NXOR`, `OR`/`NOR`, `AND`/`NAND`, `NOT`, `(` `)`. Each operator can take constants, variables, or parenthesized expressions as operands. A variable is denoted by a `$` (dollar sign) followed by any number of alpha-numeric characters. Some examples of variable names are `$a`, `$1`, `$3apples`, and `$number25`. The two constant values of true and false are represented by `$_TRUE` and `$_FALSE`. Here are some examples of valid expressions:

```
($_TRUE XOR $_FALSE) NAND $apples OR $oranges

$_TRUE

$variable325

($a AND $b OR $c XOR $a AND $b) AND (($c XOR $a AND $b) OR $a AND $b)
```

Creating a New Expression Tree
------------------------------

1.  Begin by clicking the <var>File</var> menu and select the <var>New Logic Tree</var> option.

    ![Step 1: Click File&rarr;New Logic Tree](./docs/docPics/1_step1.gif)

2.  Enter a Boolean expression into the dialog box using the [proper syntax](#syntax).

    ![Step 2: Enter a Boolean expression](./docs/docPics/1_step2.gif)

3.  The expression tree should now be displayed in the window! Resize and scroll as necessary.

    ![Step 3: Tree displayed!](./docs/docPics/1_step3.gif)

Changing Input Values
---------------------

1.  After [creating an expression tree](#newTree), click the <var>Tree Actions</var> menu and select the <var>Edit Variables</var> option.

    ![Step 1: Click Tree Actions&rarr;Edit Variables](./docs/docPics/2_step1.gif)

2.  You should now see a dialog window with all your variable names next to checkboxes. All variable values default to true, so all the checkboxes should start out checked. If you want to switch any values to false simply uncheck the box next to the variable's name. If you want the changes to apply hit OK, and if not, hit Cancel.

    ![Step 2: Change variable values with checkboxes](./docs/docPics/2_step2.gif)

3.  If you hit OK then the input displays will be updated, red for false and blue for true.

    ![Step 3: Input displays should update](./docs/docPics/2_step3.gif)

Animating the Expression Tree
-----------------------------

1.  Begin by <var>Tree Actions</var> menu and selecting the <var>Animate Tree</var> option.

    ![Step 1: Click Tree Actions&rarr;Animate Tree](./docs/docPics/3_step1.gif)

2.  Now wait as the expression tree animates the different values passing through the gates.

    ![Step 2: Wait while the tree animates](./docs/docPics/3_step2.gif)

3.  You can reset the display after (or during) the animation by clicking the <var>Tree Actions</var> menu and selecting the <var>Reset Tree</var> option. Resetting while the animation is running will stop it.

    ![Step 3: To reset the display click Tree Actions&rarr;Reset Tree](./docs/docPics/3_step3.gif)

Changing the Display Colors
---------------------------

1.  To change the default colors for the display components in the expression tree click the <var>File</var> menu and select the <var>Color Options</var> option. (Note: this must be done *before* you create a new expression tree otherwise you will be unable to edit the colors)

    ![Step 1: Click File&rarr;Color Options](./docs/docPics/6_step1.gif)

2.  When the dialog appears it will have 3 buttons along the top filled with the current values for drawing true, false, and null values. Click one of these buttons to edit the default color.

    ![Step 2: Click One of the 3 colored buttons](./docs/docPics/6_step2.gif)

3.  Use one of the three Color Chooser tabs to select a new color and then hit OK.

    ![Step 2: Select a new color](./docs/docPics/6_step3.gif)

4.  Once you've decided on your colors click OK to set them or Cancel to void the changes.

    ![Step 2: Click OK when you're finished](./docs/docPics/6_step4.gif)

5.  Now you have a custom color-scheme for the expression tree display and animation!

    ![Step 5: Click OK when you're finished](./docs/docPics/6_step5.gif)

Exporting an Image
------------------

1.  To export an image of the display click the <var>File</var> menu and select the <var>Export Image</var> option.

    ![Step 1: Click File&rarr;Export Image](./docs/docPics/4_step1.gif)

2.  In the Save dialog, navigate to where you want your image saved and type in a name. All image files are saved in [PNG format](http://en.wikipedia.org/wiki/PNG).

    ![Step 2: Use the Save dialog to export your PNG image](./docs/docPics/4_step2.gif)

Displaying the Truth Table
--------------------------

1.  To display your expression tree's truth table, click the <var>File</var> menu and select the <var>Show Truth Table</var> option.

    ![Step 1: Click File&rarr;Show Truth Table](./docs/docPics/5_step1.gif)

2.  Your default browser should then open to display the truth table in HTML format and you can use your browser to save a copy of the truth table for future use.

    ![Step 2: Your default browser should display the Truth Table](./docs/docPics/5_step2.gif)

3.  Using the "Web Page, HTML Only" option while saving is recommended over "Web Page, Complete" since the truth table contains no external objects.

    ![Step 3: Use the "Web Page, HTML Only" when saving](./docs/docPics/5_step3.gif)

Contact and Support
-------------------

If you have any questions, concerns, suggestions, or a bug to report, please visit <http://ouuuuch.phoenixteam.org/>.

Copyright &copy; 2006 Nick Vrvilo
