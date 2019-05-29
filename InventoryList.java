import java.util.ArrayList;

public class InventoryList implements Listable
{
    public InventoryList()
    {
      items = new ArrayList<String>();
    }
    
    // interesting methods

    public void displayList()
    {
      for (String nextItem : items)
      {
        System.out.print(" " + nextItem);
      }

      System.out.println();
    }

    public int numberOfItems()
    {
      return items.size();
    }

    // required methods because of implemented interfaces

    public String selectItem(int pos)
    {
      return items.get(pos);
    }

    public boolean removeItem(String item)
    {
      if (items.indexOf(item) >= 0)
      {
        items.remove(item);
        return true;
      }

      return false;
    }

    public boolean addItem(String newItem)
    {
      // TODO - check to see if item is already in the list
      
      if (items.add(newItem))
      {
        return true;
      }

      return false;
    }


    public boolean containsItem(String item)
    {
      if (items.indexOf(item) >= 0)
      {
        return true;
      }

      return false;
    }

    // instance variables

    ArrayList<String> items; 
}
