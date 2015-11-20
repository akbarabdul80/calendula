package es.usc.citius.servando.calendula.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import es.usc.citius.servando.calendula.persistence.DailyScheduleItem;
import es.usc.citius.servando.calendula.persistence.Schedule;
import es.usc.citius.servando.calendula.persistence.ScheduleItem;

/**
 * Created by joseangel.pineiro on 3/26/15.
 */
public class DailyScheduleItemDao extends GenericDao<DailyScheduleItem, Long> {


    public DailyScheduleItemDao(DatabaseHelper db) {
        super(db);
    }

    @Override
    public Dao<DailyScheduleItem, Long> getConcreteDao() {
        try {
            return dbHelper.getDailyScheduleItemsDao();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating medicines dao", e);
        }
    }

    public DailyScheduleItem findByScheduleItem(ScheduleItem i) {
        return findOneBy(DailyScheduleItem.COLUMN_SCHEDULE_ITEM, i.getId());
    }

    public List<DailyScheduleItem> findAllByScheduleItem(ScheduleItem i) {
        return findBy(DailyScheduleItem.COLUMN_SCHEDULE_ITEM, i.getId());
    }

    public void removeAll() {
        DB.transaction(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for (DailyScheduleItem i : findAll()) {
                    DB.dailyScheduleItems().remove(i);
                }
                return null;
            }
        });
    }

    public void removeAllFrom(final Schedule s) {
        DB.transaction(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for (DailyScheduleItem i : findBy(DailyScheduleItem.COLUMN_SCHEDULE, s)) {
                    DB.dailyScheduleItems().remove(i);
                }
                return null;
            }
        });
    }

    public int removeOlderThan(LocalDate date) {
        try {
            DeleteBuilder<DailyScheduleItem, Long> qb = dao.deleteBuilder();
            qb.setWhere(qb.where().lt(DailyScheduleItem.COLUMN_DATE, date));
            return qb.delete();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }

    public int removeBeyond(LocalDate date) {
        try {
            DeleteBuilder<DailyScheduleItem, Long> qb = dao.deleteBuilder();
            qb.setWhere(qb.where().gt(DailyScheduleItem.COLUMN_DATE, date));
            return qb.delete();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }

    public DailyScheduleItem findByScheduleAndTime(Schedule schedule, LocalTime time) {
        try {
            QueryBuilder<DailyScheduleItem, Long> qb = dao.queryBuilder();
            Where w = qb.where();
            w.and(w.eq(DailyScheduleItem.COLUMN_SCHEDULE, schedule),
                    w.eq(DailyScheduleItem.COLUMN_TIME, time));
            qb.setWhere(w);
            return qb.queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }


    public DailyScheduleItem findByScheduleItemAndDate(ScheduleItem item, LocalDate date) {
        try {
            QueryBuilder<DailyScheduleItem, Long> qb = dao.queryBuilder();
            Where w = qb.where();
            w.and(w.eq(DailyScheduleItem.COLUMN_SCHEDULE_ITEM, item),
                    w.eq(DailyScheduleItem.COLUMN_DATE, date));
            qb.setWhere(w);
            return qb.queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }

    public DailyScheduleItem findBy(Schedule schedule, LocalDate date, LocalTime time) {
        try {
            QueryBuilder<DailyScheduleItem, Long> qb = dao.queryBuilder();
            Where w = qb.where();
            w.and(w.eq(DailyScheduleItem.COLUMN_SCHEDULE, schedule),
                    w.eq(DailyScheduleItem.COLUMN_TIME, time),
                    w.eq(DailyScheduleItem.COLUMN_DATE, date));
            qb.setWhere(w);
            return qb.queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }


    public List<DailyScheduleItem> findBySchedule(Schedule schedule) {
        try {
            QueryBuilder<DailyScheduleItem, Long> qb = dao.queryBuilder();
            Where w = qb.where();
            w.eq(DailyScheduleItem.COLUMN_SCHEDULE, schedule);
            qb.setWhere(w);
            return qb.query();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }
    }

    public boolean isDatePresent(LocalDate date) {
        try {
            QueryBuilder<DailyScheduleItem, Long> qb = dao.queryBuilder();
            qb.setWhere(qb.where().eq(DailyScheduleItem.COLUMN_DATE, date));
            return qb.countOf() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding model", e);
        }

    }
}
