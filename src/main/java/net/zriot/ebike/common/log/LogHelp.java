package net.zriot.ebike.common.log;

import net.zriot.ebike.common.exception.GException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class LogHelp {

    /**
     * 打印DB操作
     *
     * @param info
     */
    public static void doLogDB(String... info) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        Logger log = LoggerFactory.getLogger("DB");
        if (info != null) {
            String inf = "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            for (int i = 0; i < info.length; i++) {
                inf += "|" + info[i];
            }
            log.debug(inf);
        }
    }

    /**
     * 打印流水操作
     *
     * @param info
     */
    public static void doLogFlow(String... info) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        Logger log = LoggerFactory.getLogger("Flow");
        if (info != null) {
            String inf = "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            for (int i = 0; i < info.length; i++) {
                inf += "|" + info[i];
            }
            log.debug(inf);
        }
    }


    /**
     * 打印异步发送日志
     *
     * @param info
     */
    public static void doLogAsync(String... info) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        Logger log = LoggerFactory.getLogger("Async");
        if (info != null) {
            String inf = "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            for (int i = 0; i < info.length; i++) {
                inf += "|" + info[i];
            }
            log.debug(inf);
        }
    }


    /**
     * 打印App
     *
     * @param info
     */
    public static void doLogApp(String... info) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        Logger log = LoggerFactory.getLogger("App");
        if (info != null) {
            String inf = "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            for (int i = 0; i < info.length; i++) {
                inf += "|" + info[i];
            }
            log.debug(inf);
        }
    }

    public static void doLogException(Throwable t) {
        Logger log = LoggerFactory.getLogger("SystemErr");
        log.error(t.getMessage(), t);
    }

    public static void doLogAppErr(GException e) {
        Logger log = LoggerFactory.getLogger("AppErr");
        log.error("error code:" + e.getErr() + "|error msg:" + e.getMsg(), e);
    }

    public static void doLogAppErr(String... info) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        Logger log = LoggerFactory.getLogger("AppErr");
        if (info != null) {
            String inf = "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            for (int i = 0; i < info.length; i++) {
                inf += "|" + info[i];
            }
            log.error(inf);
        }
    }
}

