## Deel 1

 * Resources worden opgehaald met relatief pad in plaats van het classpath.
 * Een lange straat bouwen duurt redelijk lang.

## Deel 2

 * Door het gebruik van `toExternalForm` start je programma niet op in een JAR.
 * Voor de status van de knoppen bij te houden hadden we toch minstens
   het gebruik van een enum verwacht, niet gewoon strings waar je dan
   op `switch`ed.
 * Goeie overerving voor `Tile`s.
 * Vermijd het gebruik van package-private klassen en methoden.
 * Actors gebruiken overerving, maar het gebruik van `getType()`
   verdoezelt `instanceof` in de `act`. Hier had je mooi gebruik
   kunnen maken van Visitors.
