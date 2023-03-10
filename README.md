The first screen should contain two tabs. One of them is a list of elements from the response. The other one is the favorites tab.
The first tab:
There should be a search bar. When a user types a string with more than 3 characters, you should search for that string. (Eg: GtaV should list gtav search content.)
Cells should contain image, name, genre(If more than one genre exists, please combine their names like “Action, Shooter”), metacritic ratings.
You should show a different background color for the items’ cell that the user has already opened its detail. (use the color we have sent you on figma.)
Add pagination support for the search screen.
When the user taps an item, it opens the detail screen. (push or present, no matter.)

Bonus: Offline first approach will let you get more points. 

The second tab:
	That’s the favorites tab. 
	There is no need for the background color change operation we mentioned for the first screen.
	Users can remove a favorite game using swipe to delete. (Please ask the user if it is sure.)
	Favoriting a game does not need a service call. You can store that information anywhere on the client-side.

The detail screen:
	When the user taps an item, it opens the detail screen. Detail screen will need another service call which we will share below.
If the game has already been favorited, write “Favorited”, write “Favorite” vice versa on the top right bar button. Bar button should do the work that's been written on it. 
There is no need for an in-app browser for the links “Visit website”, “Visit Reddit”. You can open safari directly.
	Game description area can support 4 lines.



Bonus: Adding a “Read More” option for the game description cell lets you get more points.
Bonus: Use a single column for portrait mode. Supporting double columns in landscape mode will let you get more points.
