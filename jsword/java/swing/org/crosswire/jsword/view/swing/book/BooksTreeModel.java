package org.crosswire.jsword.view.swing.book;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.crosswire.jsword.book.Bible;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.BookMetaData;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.BooksEvent;
import org.crosswire.jsword.book.BooksListener;
import org.crosswire.jsword.book.Commentary;
import org.crosswire.jsword.book.Dictionary;

/**
 * A TreeModel that displays the installed Books.
 * The children of the root node are "Bibles", "Commentaries" and
 * "Dictionaries". the children of those nodes are the Books themselves.
 * 
 * <p>I briefly considered making this mode generic by having a set of named
 * BookFilters, however whilst more generic I'm not sure how often this would
 * be used, and it stops me extending this class to deal with uninstalled
 * Books.
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public class BooksTreeModel implements TreeModel
{
    /**
     * Simple ctor
     */
    public BooksTreeModel()
    {
        Books.addBooksListener(new CustomBooksListener());
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot()
    {
        return ROOT;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent)
    {
        if (parent == ROOT)
        {
            return 3;
        }

        if (parent == BIBLES)
        {
            return Books.getBooks(BookFilters.getBibles()).size();
        }

        if (parent == COMMENTARIES)
        {
            return Books.getBooks(BookFilters.getCommentaries()).size();
        }

        if (parent == DICTIONARIES)
        {
            return Books.getBooks(BookFilters.getDictionaries()).size();
        }

        return 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node)
    {
        if (node == ROOT || node == BIBLES || node == COMMENTARIES || node == DICTIONARIES)
        {
            return false;
        }

        return true;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener li)
    {
        listenerList.add(TreeModelListener.class, li);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener li)
    {
        listenerList.remove(TreeModelListener.class, li);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index)
    {
        if (parent == ROOT)
        {
            switch (index)
            {
            case 0:
                return BIBLES;
            case 1:
                return COMMENTARIES;
            case 2:
                return DICTIONARIES;
            default:
                return null;
            }
        }

        if (parent == BIBLES)
        {
            return Books.getBooks(BookFilters.getBibles()).get(index);
        }

        if (parent == COMMENTARIES)
        {
            return Books.getBooks(BookFilters.getCommentaries()).get(index);
        }

        if (parent == DICTIONARIES)
        {
            return Books.getBooks(BookFilters.getDictionaries()).get(index);
        }

        return null;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child)
    {
        if (parent == ROOT)
        {
            if (child == BIBLES)
            {
                return 0;
            }

            if (child == COMMENTARIES)
            {
                return 1;
            }

            if (child == DICTIONARIES)
            {
                return 2;
            }

            return -1;
        }

        if (parent == BIBLES)
        {
            return Books.getBooks(BookFilters.getBibles()).indexOf(child);
        }

        if (parent == COMMENTARIES)
        {
            return Books.getBooks(BookFilters.getCommentaries()).indexOf(child);
        }

        if (parent == DICTIONARIES)
        {
            return Books.getBooks(BookFilters.getDictionaries()).indexOf(child);
        }

        return -1;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue)
    {
        // Ignore because we are not editable
    }

    /**
     * What nodes have been added?
     */
    protected void fireTreeNodesInserted(Object source, Object[] path)
    {
        TreeModelEvent ev = new TreeModelEvent(source, path);
        Object[] listeners = listenerList.getListenerList();

        // Loop through the listeners
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                ((TreeModelListener) listeners[i+1]).treeNodesInserted(ev);
            }          
        }
    }

    /**
     * What nodes have been removed?
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path)
    {
        TreeModelEvent ev = new TreeModelEvent(source, path);
        Object[] listeners = listenerList.getListenerList();

        // Loop through the listeners
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                ((TreeModelListener) listeners[i+1]).treeNodesRemoved(ev);
            }          
        }
    }

    /**
     * Level 2 nodes can be derived from the BookMetaData object of the parent
     * node at level 3.
     */
    protected Object getChildOfRoot(BookMetaData bmd)
    {
        if (bmd instanceof Bible)
        {
            return BIBLES;
        }
        else if (bmd instanceof Commentary)
        {
            return COMMENTARIES;
        }
        else if (bmd instanceof Dictionary)
        {
            return DICTIONARIES;
        }
        else
        {
            return null;
        }
    }

    protected static final String ROOT = "Books";
    protected static final String BIBLES = "Bibles";
    protected static final String DICTIONARIES = "Dictionaries";
    protected static final String COMMENTARIES = "Commentaries";

    /**
     * The list of listeners
     */
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * When new books are added we need to relfect the change in this tree.
     */
    private final class CustomBooksListener implements BooksListener
    {
        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.BooksListener#bookAdded(org.crosswire.jsword.book.BooksEvent)
         */
        public void bookAdded(BooksEvent ev)
        {
            BookMetaData bmd = ev.getBookMetaData();
            Object[] path = new Object[] { ROOT, getChildOfRoot(bmd), bmd };
            fireTreeNodesInserted(ev.getSource(), path);
        }

        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.BooksListener#bookRemoved(org.crosswire.jsword.book.BooksEvent)
         */
        public void bookRemoved(BooksEvent ev)
        {
            BookMetaData bmd = ev.getBookMetaData();
            Object[] path = new Object[] { ROOT, getChildOfRoot(bmd), bmd };
            fireTreeNodesRemoved(ev.getSource(), path);
        }
    }
}
