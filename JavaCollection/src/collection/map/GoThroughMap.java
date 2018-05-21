package collection.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 遍历Map
 * 以HashMap为例
 */
public class GoThroughMap {
    Map<String, String> map = new HashMap<>();
    
    public void useKeySet() {
        for(String key : map.keySet()) {
            
        }
    }
    
    public void useValues() {
        for(String value : map.values()) {
            
        }
    }
    
    public void useEntrySet() {
        for(Map.Entry<String, String> entry : map.entrySet()) {
            
        }
    }
    
    public void useIterator() {
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        //Iterator<String> iterator = map.keySet().iterator();
        //Iterator<String> iterator = map.values().iterator();
        while(iterator.hasNext()) {
            
        }
    }
    
    public void useForEach() {
        /*
         * 实际上该方法仍然是遍历的map.entrySet()
         */
        map.forEach((K,V)->{});
    }
    
}
