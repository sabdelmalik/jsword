
package org.crosswire.jsword.book.sword;

import java.net.URL;
import java.util.Date;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookDriver;
import org.crosswire.jsword.book.BookMetaData;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.Openness;

/**
 * A basic implementation of BookMetaData from which the Bible etc meta datas
 * can build.
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
 * @see docs.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public abstract class SwordBookMetaData implements BookMetaData
{
    /**
     * Simple ctor
     */
    public SwordBookMetaData(SwordBookDriver driver, SwordConfig config)
    {
        this.driver = driver;
        this.config = config;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getBook()
     */
    public synchronized Book getBook()
    {
        if (book == null)
        {
            book = createBook();
        }

        return book;
    }

    /**
     * A simple concrete implementation ctor
     * @throws BookException
     */
    public abstract Book createBook();

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getDriver()
     */
    public BookDriver getDriver()
    {
        return driver;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getName()
     */
    public String getName()
    {
        return config.getDescription();
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getEdition()
     */
    public final String getEdition()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getFullName()
     */
    public final String getFullName()
    {
        return getName() + ", " + getEdition() + " (" + getDriverName() + ")";
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#isSameFamily(org.crosswire.jsword.book.BookMetaData)
     */
    public final boolean isSameFamily(BookMetaData version)
    {
        return getName().equals(version.getName());
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getInitials()
     */
    public final String getInitials()
    {
        return config.getName();
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getDriverName()
     */
    public final String getDriverName()
    {
        return "Sword";
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getSpeed()
     */
    public final int getSpeed()
    {
        return Books.SPEED_FAST;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getFirstPublished()
     */
    public final Date getFirstPublished()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getOpenness()
     */
    public final Openness getOpenness()
    {
        return Openness.UNKNOWN;
    }

    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.BookMetaData#getLicence()
     */
    public final URL getLicence()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getFullName();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        // Since this can not be null
        if (obj == null)
        {
            return false;
        }

        // Check that 'that' is the same as this
        // Don't use instanceof since that breaks inheritance
        if (!obj.getClass().equals(this.getClass()))
        {
            return false;
        }

        // If super does equals ...
        if (!super.equals(obj))
        {
            return false;
        }

        // The real bit ...
        SwordBookMetaData that = (SwordBookMetaData) obj;

        if (!getName().equals(that.getName()))
        {
            return false;
        }

        return getEdition().equals(that.getEdition());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return (getName() + getEdition()).hashCode();
    }

    /**
     * The driver that works this stuff
     */
    private SwordBookDriver driver;

    /**
     * Our store of config data
     */
    protected SwordConfig config;

    /**
     * The cached bible so we don't have to create too many
     */
    private Book book = null;
}