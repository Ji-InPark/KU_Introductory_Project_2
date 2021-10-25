package src;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OptionTime implements Option {
    private String arg;
    private List<File> fileList;

    private final String dateSplitToken = "T";
    private final String template_yyyyMMdd = "yyyyMMdd";
    private final String template_hhmmss = "hhmmss";

    private final char SYMBOL_AFTER = '+';
    private final char SYMBOL_BEFORE = '-';
    private char target_symbol = 0;

    private Date standardDate = null;

    @Nullable
    private FileList _fileList;

    public OptionTime(@NotNull String arg, @NotNull List<File> fileList) {
        this.arg = arg;
        this.fileList = fileList;

        //remove directories from fileList
        Iterator<File> iterator = this.fileList.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.isDirectory()) {
                iterator.remove();
            }
        }

        checkArg();
    }

    public OptionTime(@NotNull String arg, @NotNull File rootDir) {
        this(arg, Utils.flatFiles(rootDir));
    }

    public OptionTime(@NotNull String arg, @NotNull FileList fileList) {
        this(arg, fileList.getTargetFileList());
        this._fileList = fileList;
    }

    @NotNull
    @Override
    public List<File> analyze() {
        List<File> results = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (File file : fileList) {
            calendar.setTimeInMillis(file.lastModified());
            Date fileLastModifiedDate = calendar.getTime();
            if (target_symbol == SYMBOL_AFTER && fileLastModifiedDate.after(standardDate)) {
                results.add(file);
            } else if (target_symbol == SYMBOL_BEFORE && fileLastModifiedDate.before(standardDate)) {
                results.add(file);
            } else if (target_symbol == 0 && file.lastModified() / 1000 == standardDate.getTime() / 1000) {
                results.add(file);
            } else {
                offTargetFile(file);
            }

        }
        return results;
    }

    private void offTargetFile(File file) {
        if (this._fileList != null) {
            int fileIndex = this._fileList.getFileIndex(file);
            if (fileIndex != -1) {
                this._fileList.setResult(fileIndex, false);
            }
        }
    }

    private Date parseDate() throws IllegalArgumentException {
        StringTokenizer spliter = new StringTokenizer(arg, dateSplitToken, false);

        String yyyyMMdd = arg.charAt(0) == 'T' ? parse_yyyyMMdd("") : parse_yyyyMMdd(spliter.nextToken());
        String hhmmss = parse_hhmmss("");
        if (spliter.hasMoreTokens()) {
            hhmmss = parse_hhmmss(spliter.nextToken());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(template_yyyyMMdd + template_hhmmss);

        try {
            return dateFormat.parse(yyyyMMdd + hhmmss);
        } catch (ParseException e) {
            throw new IllegalArgumentException("올바른 날짜 서식이 아닙니다");
        }
    }

    private String parse_yyyyMMdd(String arg_yyyyMMdd) throws IllegalArgumentException {
        if (arg_yyyyMMdd.length() == 0) {
            return getDateString("yyyyMMdd");
        }
        if (arg_yyyyMMdd.length() == 2) {
            return getDateString("yyyyMM") + arg_yyyyMMdd;
        }
        if (arg_yyyyMMdd.length() == 4) {
            return getDateString("yyyy") + arg_yyyyMMdd;
        }
        if (arg_yyyyMMdd.length() == 8) {
            return arg_yyyyMMdd;
        }

        throw new IllegalArgumentException(String.format("허용서식: yyyyMMdd, MMdd, dd. (입력값: %s)", arg_yyyyMMdd));
    }

    private String parse_hhmmss(String arg_hhmmss) throws IllegalArgumentException {
        if (arg_hhmmss.length() == 0) {
            return getDateString("hhmmss");
        }
        if (arg_hhmmss.length() == 2) {
            return getDateString("hhmm") + arg_hhmmss;
        }
        if (arg_hhmmss.length() == 4) {
            return getDateString("hh") + arg_hhmmss;
        }
        if (arg_hhmmss.length() == 6) {
            return arg_hhmmss;
        }

        throw new IllegalArgumentException(String.format("허용서식: hhmmss, hhmm, hh. (입력값: %s)", arg_hhmmss));
    }

    @NotNull
    private String getDateString(@NotNull String format) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(currentDate);
    }

    //CHECK argument validation here
    @Override
    public void checkArg() throws IllegalArgumentException {
        if (arg.isEmpty()) {
            throw new IllegalArgumentException("인자값이 없습니다");
        }

        if (!arg.contains(dateSplitToken))
            throw new IllegalArgumentException("T를 기준으로 yyyyMMdd[T]hhmmss 처럼 전달되어야 합니다");

        if (arg.charAt(0) == SYMBOL_AFTER || arg.charAt(0) == SYMBOL_BEFORE) {
            target_symbol = arg.charAt(0);
            arg = arg.substring(1);
        }
        if (arg.replace("T", "").matches("/[^0-9]/")) {
            throw new IllegalArgumentException("T를 기준으로 yyyyMMdd[T]hhmmss 처럼 전달되어야 합니다");
        }

        this.standardDate = parseDate();
    }

    @Override
    public String getSymbol() {
        return "-mtime";
    }
}
