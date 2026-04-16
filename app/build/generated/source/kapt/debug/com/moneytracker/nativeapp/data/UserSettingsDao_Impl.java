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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserSettingsDao_Impl implements UserSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserSettings> __insertionAdapterOfUserSettings;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<UserSettings> __updateAdapterOfUserSettings;

  public UserSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserSettings = new EntityInsertionAdapter<UserSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_settings` (`id`,`userName`,`currency`,`language`,`monthlyBudget`,`notificationsEnabled`,`darkModeEnabled`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettings entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserName());
        }
        if (entity.getCurrency() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCurrency());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getLanguage());
        }
        final String _tmp = __converters.fromBigDecimal(entity.getMonthlyBudget());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final int _tmp_1 = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final int _tmp_2 = entity.getDarkModeEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
      }
    };
    this.__updateAdapterOfUserSettings = new EntityDeletionOrUpdateAdapter<UserSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_settings` SET `id` = ?,`userName` = ?,`currency` = ?,`language` = ?,`monthlyBudget` = ?,`notificationsEnabled` = ?,`darkModeEnabled` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettings entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserName());
        }
        if (entity.getCurrency() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCurrency());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getLanguage());
        }
        final String _tmp = __converters.fromBigDecimal(entity.getMonthlyBudget());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final int _tmp_1 = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        final int _tmp_2 = entity.getDarkModeEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final UserSettings settings, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserSettings.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final UserSettings settings, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserSettings.handle(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public LiveData<UserSettings> getSettings() {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_settings"}, false, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfMonthlyBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyBudget");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final BigDecimal _tmpMonthlyBudget;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMonthlyBudget)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMonthlyBudget);
            }
            _tmpMonthlyBudget = __converters.toBigDecimal(_tmp);
            final boolean _tmpNotificationsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp_1 != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_2 != 0;
            _result = new UserSettings(_tmpId,_tmpUserName,_tmpCurrency,_tmpLanguage,_tmpMonthlyBudget,_tmpNotificationsEnabled,_tmpDarkModeEnabled);
          } else {
            _result = null;
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
  public Object getSettingsSync(final Continuation<? super UserSettings> arg0) {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfMonthlyBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyBudget");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfDarkModeEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "darkModeEnabled");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpCurrency;
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _tmpCurrency = null;
            } else {
              _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            }
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final BigDecimal _tmpMonthlyBudget;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMonthlyBudget)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMonthlyBudget);
            }
            _tmpMonthlyBudget = __converters.toBigDecimal(_tmp);
            final boolean _tmpNotificationsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp_1 != 0;
            final boolean _tmpDarkModeEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDarkModeEnabled);
            _tmpDarkModeEnabled = _tmp_2 != 0;
            _result = new UserSettings(_tmpId,_tmpUserName,_tmpCurrency,_tmpLanguage,_tmpMonthlyBudget,_tmpNotificationsEnabled,_tmpDarkModeEnabled);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
