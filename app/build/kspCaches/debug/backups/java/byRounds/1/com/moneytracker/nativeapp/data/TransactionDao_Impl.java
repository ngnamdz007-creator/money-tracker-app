package com.moneytracker.nativeapp.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Transaction> __insertionAdapterOfTransaction;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Transaction> __deletionAdapterOfTransaction;

  private final EntityDeletionOrUpdateAdapter<Transaction> __updateAdapterOfTransaction;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransaction = new EntityInsertionAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`amount`,`type`,`category`,`title`,`description`,`date`,`createdAt`,`isRecurring`,`recurringPeriod`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromBigDecimal(entity.getAmount());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        final String _tmp_1 = __converters.fromTransactionType(entity.getType());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp_1);
        }
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getTitle());
        statement.bindString(6, entity.getDescription());
        final String _tmp_2 = __converters.fromLocalDate(entity.getDate());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
        final int _tmp_4 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(9, _tmp_4);
        if (entity.getRecurringPeriod() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getRecurringPeriod());
        }
      }
    };
    this.__deletionAdapterOfTransaction = new EntityDeletionOrUpdateAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransaction = new EntityDeletionOrUpdateAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`amount` = ?,`type` = ?,`category` = ?,`title` = ?,`description` = ?,`date` = ?,`createdAt` = ?,`isRecurring` = ?,`recurringPeriod` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.fromBigDecimal(entity.getAmount());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        final String _tmp_1 = __converters.fromTransactionType(entity.getType());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp_1);
        }
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getTitle());
        statement.bindString(6, entity.getDescription());
        final String _tmp_2 = __converters.fromLocalDate(entity.getDate());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_3);
        }
        final int _tmp_4 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(9, _tmp_4);
        if (entity.getRecurringPeriod() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getRecurringPeriod());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transactions WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Transaction transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransaction.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Transaction transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransaction.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Transaction transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransaction.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Transaction>> getAllTransactions() {
    final String _sql = "SELECT * FROM transactions ORDER BY date DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<Transaction>>() {
      @Override
      @Nullable
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_1 = __converters.toBigDecimal(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_1;
            }
            final TransactionType _tmpType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_3 = __converters.toTransactionType(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_3;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_5 = __converters.toLocalDate(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_5;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Transaction>> getTransactionsByDateRange(final LocalDate startDate,
      final LocalDate endDate) {
    final String _sql = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<Transaction>>() {
      @Override
      @Nullable
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_3 = __converters.toBigDecimal(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_3;
            }
            final TransactionType _tmpType;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_5 = __converters.toTransactionType(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_5;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_7 = __converters.toLocalDate(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_7;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_10 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Transaction>> getTransactionsByDateRangeFlow(final LocalDate startDate,
      final LocalDate endDate) {
    final String _sql = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_3 = __converters.toBigDecimal(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_3;
            }
            final TransactionType _tmpType;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_5 = __converters.toTransactionType(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_5;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_7 = __converters.toLocalDate(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_7;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_10 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTransactionsByDateRangeSync(final LocalDate startDate, final LocalDate endDate,
      final Continuation<? super List<Transaction>> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_3 = __converters.toBigDecimal(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_3;
            }
            final TransactionType _tmpType;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_5 = __converters.toTransactionType(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_5;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_7 = __converters.toLocalDate(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_7;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_10 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Transaction>> getTransactionsByType(final TransactionType type,
      final LocalDate startDate, final LocalDate endDate) {
    final String _sql = "SELECT * FROM transactions WHERE type = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    final String _tmp = __converters.fromTransactionType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(startDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    _argIndex = 3;
    final String _tmp_2 = __converters.fromLocalDate(endDate);
    if (_tmp_2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_2);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<Transaction>>() {
      @Override
      @Nullable
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_4 = __converters.toBigDecimal(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_4;
            }
            final TransactionType _tmpType;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_6 = __converters.toTransactionType(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_6;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_8 = __converters.toLocalDate(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_8;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_9;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_10 = __converters.toLocalDateTime(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_10;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_11;
            _tmp_11 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_11 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTransactionsByCategory(final String category, final LocalDate startDate,
      final LocalDate endDate, final Continuation<? super List<Transaction>> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE category = ? AND date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 3;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_3 = __converters.toBigDecimal(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_3;
            }
            final TransactionType _tmpType;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_5 = __converters.toTransactionType(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_5;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_7 = __converters.toLocalDate(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_7;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_9;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_10 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTransactionById(final long id,
      final Continuation<? super Transaction> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Transaction>() {
      @Override
      @Nullable
      public Transaction call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final Transaction _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_1 = __converters.toBigDecimal(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_1;
            }
            final TransactionType _tmpType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_3 = __converters.toTransactionType(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_3;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_5 = __converters.toLocalDate(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_5;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _result = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getIncomeExpenseForPeriod(final LocalDate startDate, final LocalDate endDate,
      final Continuation<? super IncomeExpenseResult> $completion) {
    final String _sql = "SELECT SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) as totalIncome, SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) as totalExpense FROM transactions WHERE date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<IncomeExpenseResult>() {
      @Override
      @Nullable
      public IncomeExpenseResult call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotalIncome = 0;
          final int _cursorIndexOfTotalExpense = 1;
          final IncomeExpenseResult _result;
          if (_cursor.moveToFirst()) {
            final BigDecimal _tmpTotalIncome;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTotalIncome)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfTotalIncome);
            }
            _tmpTotalIncome = __converters.toBigDecimal(_tmp_2);
            final BigDecimal _tmpTotalExpense;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTotalExpense)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTotalExpense);
            }
            _tmpTotalExpense = __converters.toBigDecimal(_tmp_3);
            _result = new IncomeExpenseResult(_tmpTotalIncome,_tmpTotalExpense);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getExpenseByCategory(final LocalDate startDate, final LocalDate endDate,
      final Continuation<? super List<CategoryTotal>> $completion) {
    final String _sql = "SELECT category, SUM(amount) as total FROM transactions WHERE type = 'EXPENSE' AND date BETWEEN ? AND ? GROUP BY category ORDER BY total DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CategoryTotal>>() {
      @Override
      @NonNull
      public List<CategoryTotal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfTotal = 1;
          final List<CategoryTotal> _result = new ArrayList<CategoryTotal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryTotal _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final BigDecimal _tmpTotal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTotal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfTotal);
            }
            final BigDecimal _tmp_3 = __converters.toBigDecimal(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpTotal = _tmp_3;
            }
            _item = new CategoryTotal(_tmpCategory,_tmpTotal);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalByType(final TransactionType type, final LocalDate startDate,
      final LocalDate endDate, final Continuation<? super BigDecimal> $completion) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE type = ? AND date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    final String _tmp = __converters.fromTransactionType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDate(startDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    _argIndex = 3;
    final String _tmp_2 = __converters.fromLocalDate(endDate);
    if (_tmp_2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_2);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BigDecimal>() {
      @Override
      @Nullable
      public BigDecimal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final BigDecimal _result;
          if (_cursor.moveToFirst()) {
            final String _tmp_3;
            if (_cursor.isNull(0)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(0);
            }
            _result = __converters.toBigDecimal(_tmp_3);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecurringTransactions(
      final Continuation<? super List<Transaction>> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE isRecurring = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Transaction>>() {
      @Override
      @NonNull
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPeriod");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final BigDecimal _tmpAmount;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAmount);
            }
            final BigDecimal _tmp_1 = __converters.toBigDecimal(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.math.BigDecimal', but it was NULL.");
            } else {
              _tmpAmount = _tmp_1;
            }
            final TransactionType _tmpType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfType);
            }
            final TransactionType _tmp_3 = __converters.toTransactionType(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.moneytracker.nativeapp.data.TransactionType', but it was NULL.");
            } else {
              _tmpType = _tmp_3;
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDate _tmpDate;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfDate);
            }
            final LocalDate _tmp_5 = __converters.toLocalDate(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDate', but it was NULL.");
            } else {
              _tmpDate = _tmp_5;
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPeriod;
            if (_cursor.isNull(_cursorIndexOfRecurringPeriod)) {
              _tmpRecurringPeriod = null;
            } else {
              _tmpRecurringPeriod = _cursor.getString(_cursorIndexOfRecurringPeriod);
            }
            _item = new Transaction(_tmpId,_tmpAmount,_tmpType,_tmpCategory,_tmpTitle,_tmpDescription,_tmpDate,_tmpCreatedAt,_tmpIsRecurring,_tmpRecurringPeriod);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
