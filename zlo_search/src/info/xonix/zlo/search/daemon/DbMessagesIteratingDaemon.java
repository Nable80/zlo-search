package info.xonix.zlo.search.daemon;

import info.xonix.zlo.search.dao.DbDict;
import info.xonix.zlo.search.logic.AppLogic;
import info.xonix.zlo.search.model.Message;
import info.xonix.zlo.search.model.Site;
import info.xonix.zlo.search.spring.AppSpringContext;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Author: Vovan
 * Date: 05.04.2008
 * Time: 18:53:55
 */
public abstract class DbMessagesIteratingDaemon extends Daemon {

    private DbDict dbDict = AppSpringContext.get(DbDict.class);
    private AppLogic appLogic = AppSpringContext.get(AppLogic.class);

    private static final Logger logger = Logger.getLogger(DbMessagesIteratingDaemon.class);
    private int processPerTime = 1000; // default

    private class DbMessagesIterationProcess extends Process {

        protected int getFromIndex() {
            return dbDict.getInt(getSite(), getIteratingVariableName(), 0);
        }

        protected int getEndIndex() {
            return appLogic.getLastSavedMessageNumber(getSite());
        }

        protected void perform(int from, int to) {
            doWithMessages(appLogic.getMessages(getSite(), from, to + 1));
            dbDict.setInt(getSite(), getIteratingVariableName(), to);
        }

        protected boolean processException(Exception ex) {
            return false;
        }

        protected void cleanUp() {
        }
    }

    protected DbMessagesIteratingDaemon(Site site) {
        super(site);
        setDoPerTime(processPerTime);
    }

    protected Logger getLogger() {
        return logger;
    }

    protected Process createProcess() {
        return new DbMessagesIterationProcess();
    }

    protected void reset() {
        dbDict.setInt(getSite(), getIteratingVariableName(), 0);
    }

    protected abstract void doWithMessages(List<Message> msgs);

    protected abstract String getIteratingVariableName();
}
