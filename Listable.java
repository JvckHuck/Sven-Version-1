public interface Listable
{
  boolean addItem(String newItem);  // true if success in adding item
  boolean removeItem(String item);  // true if item was not successfully removed
  boolean containsItem(String item);  // true if item is in the list
  String selectItem(int pos);       // return the text description of the item
}
