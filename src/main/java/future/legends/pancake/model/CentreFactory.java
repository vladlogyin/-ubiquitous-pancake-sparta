package future.legends.pancake.model;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CentreFactory {
    // an atomic integer is needed because the normal Integer boxed type is immutable which makes it pointless...
    private Map<Class<? extends TraineeCentre>, AtomicInteger> availableCentres; //centre types that can be created
    private Map<Class<? extends TraineeCentre>, CentreType> enumLookup; //centre types that can be created
    public CentreFactory()
    {
        availableCentres = new HashMap<>();
        enumLookup = new HashMap<>();
        for(CentreType tct : CentreType.values())
        {
            availableCentres.put(tct.type, new AtomicInteger(tct.limit));
            enumLookup.put(tct.type, tct);
        }
    }

    public TraineeCentre create() throws NoSuchElementException{
        Random r = new Random();
        while(true) {
            try {
                var keys = new ArrayList<>(availableCentres.keySet());
                int randomIndex = r.nextInt(keys.size());
                var key = keys.get(randomIndex);
                AtomicInteger countLeft = availableCentres.get(key);
                if (countLeft.get() > 0 || countLeft.get() < 0) {
                    countLeft.decrementAndGet(); // equivalent to --x
                    return key.getDeclaredConstructor().newInstance();
                } else {
                    availableCentres.remove(key);
                }
            } catch (Exception e) {
                new RuntimeException(e);
            }
        }
    }
    public void delete(TraineeCentre tr)
    {
        var trClass = tr.getClass();
        if(availableCentres.containsKey(trClass))
        {
            AtomicInteger countLeft = availableCentres.get(trClass);
            CentreType centreType = enumLookup.get(trClass); // this always works
            if( centreType.limit > 0 && countLeft.get() < centreType.limit) {
                // only increment the current available count if this centre type had a limit in the first place
                // the second condition is just a sanity check
                availableCentres.get(trClass).incrementAndGet(); // equivalent to ++x
            }
        }
        else
        {
            // if all available centres of this type have been exhausted, re-add the countLeft value to the Map
            availableCentres.put(trClass, new AtomicInteger(1));
        }
    }

}
