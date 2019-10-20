package Utils;

import java.io.*;
import java.util.*;
import java.awt.*;
public class ObjectCloner
{

   protected ObjectCloner(){}

   static public Object deepCopy(Object oldObj) throws Exception
   {
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      try
      {
         ByteArrayOutputStream bos = 
               new ByteArrayOutputStream();
         oos = new ObjectOutputStream(bos);

         oos.writeObject(oldObj);   
         oos.flush();               
         ByteArrayInputStream bin = 
               new ByteArrayInputStream(bos.toByteArray()); 
         ois = new ObjectInputStream(bin);                 

         return ois.readObject();
      }
      catch(Exception e)
      {
         System.out.println("Exception in Utils.ObjectCloner = " + e);
         throw(e);
      }
      finally
      {
         oos.close();
         ois.close();
      }
   }
   
}