General Assembly Code Challenge

- Simple Grocery List by David Smith (11/10/15)

GENERAL USAGE NOTES
=======================================================================

- The Application was created using Android 6 Marshmallow.
There currently is no support framework for previous versions 
of Android. I wanted to add this framework but didn’t for two reasons:
	1. I wanted to adhere to the timeframe provided.
	2. I wasn’t sure what sdk packages the reviewer would have.

- There is a known issue with the rendering of the keyboard causing
the Application to randomly crash in some instances. I will attempt to
debug it and provide a fix as soon as possible.

- I wanted to add the visibility of the checkbox on each item to the
listView but couldn’t do to time constraints. The checkbox is accessible
within the more details pane of the item.

=======================================================================


DESCRIPTION
=======================================================================
This Code Challenge Application is designed to create a simple grocery
ArrayList that can be viewed by the user using a listView object. The
Application allows for the user to create a new grocery item and append
the item to the list. Once the list is populated with an item(s), the
user will have the ability to click on the item and display a popup 
window that will provide more detail about the item as well as allow
the user to edit the properties of the item such as:
	- The Grocery Item’s Name
	- The Grocery Item’s Description
	- The number of grocery items
	- Whether that item was checked off

The User then has the option to save or delete the item from the ArrayList
and the Main Activity will render the changes on the homepage.