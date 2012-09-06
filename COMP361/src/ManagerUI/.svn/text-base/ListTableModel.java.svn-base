package ManagerUI;

import java.util.Collection;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import MusicManager.Song;

@SuppressWarnings("serial")
public class ListTableModel extends AbstractTableModel {
    public static final int TITLE_INDEX = 0;
    public static final int ARTIST_INDEX = 1;
    public static final int ALBUM_INDEX = 2;
    public static final int LENGTH_INDEX = 3;
    
    protected String[] columnNames;
    protected Vector<Song> dataVector;

    public ListTableModel(String[] columnNames, Collection<Song> songs) {
        this.columnNames = columnNames;
        dataVector = new Vector<Song>();
        dataVector.addAll(songs);
        //System.out.println(dataVector.get(0).getTitle());
    }
    
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void setDataVector(Collection<Song> songs)
    {
    	dataVector = new Vector<Song>();
        dataVector.addAll(songs);
    }
    
    @SuppressWarnings("unchecked")
	public Class getColumnClass(int column) {
        switch (column) {
            case TITLE_INDEX:
            case ARTIST_INDEX:
            case ALBUM_INDEX:
            case LENGTH_INDEX:
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
            case LENGTH_INDEX:
            	return record.getPrintedLength();
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

    public void refresh() {
    	fireTableDataChanged();
    }
    
    public void addEmptyRow() {
        dataVector.add(new Song());
        fireTableRowsInserted(
           dataVector.size() - 1,
           dataVector.size() - 1);
    }
}