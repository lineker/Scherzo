package UI;

import java.util.Collection;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import MusicManager.Song;
import javax.swing.table.TableColumn;

public class StreamingTableModel extends AbstractTableModel {
    public static final int TITLE_INDEX = 0;
    public static final int ARTIST_INDEX = 1;
    public static final int ALBUM_INDEX = 2;
    public static final int BUTTON_INDEX = 3;
    public static final int HIDDEN_INDEX = 4;
    
    protected String[] columnNames;
    protected Vector dataVector;

    public StreamingTableModel(String[] columnNames, Collection<Song> songs) {
        this.columnNames = columnNames;
        dataVector = new Vector();
        dataVector.addAll(songs);
    }
    

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        if (column == HIDDEN_INDEX) return false;
        else if(column == BUTTON_INDEX) return true;
        else return false;
    }
    
    public void setDataVector(Collection<Song> songs)
    {
    	dataVector = new Vector();
        dataVector.addAll(songs);
    }
    
    public Class getColumnClass(int column) {
        switch (column) {
            case TITLE_INDEX:
            case ARTIST_INDEX:
            case ALBUM_INDEX:
            case BUTTON_INDEX:
               return String.class;
            default:
               return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
    	
        Song record = (Song)dataVector.get(row);
        
        //System.out.println("getting value id:"+record.getStreamingID());
        switch (column) {
            case TITLE_INDEX:
               return record.getTitle();
            case ARTIST_INDEX:
               return record.getArtist();
            case ALBUM_INDEX:
               return record.getAlbum();
            case BUTTON_INDEX:
                return "Play";
            case HIDDEN_INDEX:
            	return record.getId();
            default:
               return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
    	System.out.println("setting value");
        Song record = (Song)dataVector.get(row);
        switch (column) {
            case TITLE_INDEX:
               record.setTitle((String)value);
               break;
            case ARTIST_INDEX:
               record.setArtist((String)value);
               break;
            case ALBUM_INDEX:
               record.setAlbum((String)value);
               break;
            default:
               System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public boolean hasEmptyRow() {
        if (dataVector.size() == 0) return false;
        Song audioRecord = (Song)dataVector.get(dataVector.size() - 1);
        if (audioRecord.getTitle().trim().equals("") &&
           audioRecord.getArtist().trim().equals("") &&
           audioRecord.getAlbum().trim().equals(""))
        {
           return true;
        }
        else return false;
    }

    public void addEmptyRow() {
        dataVector.add(new Song());
        fireTableRowsInserted(
           dataVector.size() - 1,
           dataVector.size() - 1);
    }
}