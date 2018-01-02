# Locations

Locations are all stored as `id.location` files, but are simply JSON with a different file extension. 

## Attributes

* description (str)
	* The description that is read when the player enters the room, or looks around it.
* items (list<str>)
	* A list containing the IDs of the items that start in this room.
* leading_to (dict)
	* Described below.

### Leading_To Attributes

* location (dict)
	* The location should be another location's ID. The attributes of this are described below.

#### Location Attributes

* display_name (str)
	* The name of the location as it will be show in the room's description when the player looks around.
* player_string (regex)
	* A string of regex that the player can type in to get to the particular location you're referencing.
