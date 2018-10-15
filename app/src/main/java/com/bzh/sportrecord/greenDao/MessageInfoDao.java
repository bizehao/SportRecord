package com.bzh.sportrecord.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bzh.sportrecord.greenModel.MessageInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE_INFO".
*/
public class MessageInfoDao extends AbstractDao<MessageInfo, Long> {

    public static final String TABLENAME = "MESSAGE_INFO";

    /**
     * Properties of entity MessageInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Sender = new Property(2, String.class, "sender", false, "SENDER");
        public final static Property DateTime = new Property(3, String.class, "dateTime", false, "DATE_TIME");
        public final static Property Message = new Property(4, String.class, "message", false, "MESSAGE");
        public final static Property ReadSign = new Property(5, boolean.class, "readSign", false, "READ_SIGN");
    }


    public MessageInfoDao(DaoConfig config) {
        super(config);
    }
    
    public MessageInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USERNAME\" TEXT," + // 1: username
                "\"SENDER\" TEXT," + // 2: sender
                "\"DATE_TIME\" TEXT," + // 3: dateTime
                "\"MESSAGE\" TEXT," + // 4: message
                "\"READ_SIGN\" INTEGER NOT NULL );"); // 5: readSign
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MessageInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String sender = entity.getSender();
        if (sender != null) {
            stmt.bindString(3, sender);
        }
 
        String dateTime = entity.getDateTime();
        if (dateTime != null) {
            stmt.bindString(4, dateTime);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(5, message);
        }
        stmt.bindLong(6, entity.getReadSign() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MessageInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String sender = entity.getSender();
        if (sender != null) {
            stmt.bindString(3, sender);
        }
 
        String dateTime = entity.getDateTime();
        if (dateTime != null) {
            stmt.bindString(4, dateTime);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(5, message);
        }
        stmt.bindLong(6, entity.getReadSign() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MessageInfo readEntity(Cursor cursor, int offset) {
        MessageInfo entity = new MessageInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sender
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dateTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // message
            cursor.getShort(offset + 5) != 0 // readSign
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MessageInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSender(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMessage(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setReadSign(cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MessageInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MessageInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MessageInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}