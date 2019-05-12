package pogodynka;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private List<DataEntry> entries = new ArrayList<DataEntry>();
    public DataSet() {
    }

    public void addEntry(DataEntry entry){
        List<DataEntry> oldValue = entries;
        entries = new ArrayList<DataEntry>(entries);
        entries.add(entry);
    }
    public List<DataEntry> getEntries(){return entries;}

    public void eraseEntries() {
        List<DataEntry> oldValue = entries;
        entries = new ArrayList<DataEntry>();

    }

    public int getEntriesCount() {
        return entries.size();
    }

}
