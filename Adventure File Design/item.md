# Item

Items are all stored as `id.item` files, but are simply JSON with a different file extension. 

## Attributes

* display_name (str)
	* The name that will be displayed in the player's inventory, or when they look around the room.
* aliases (regex)
	* A string of regex that the player could use to refer to the item.
* pickup_text (str)
	* The text that will be output when the player picks up the item.
* description (str)
	* The text that's output when the item is examined.

## Example

Filename: `test_item.item`
```json
{
	"display_name": "test",
	"aliases": "^(test|test item)$",
	"pickup_text": "You pick up the {{name}} and slip it into your inventory.",
	"description": "A simple test item."
}
```

