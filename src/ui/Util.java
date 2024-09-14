package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public final class Util {
    /**
     * Just to prevent initialization.
     */
    private Util() {
    }

    private static final LibWindow[] allWindows = {
            LibrarySystem.getInstance(),
            LoginWindow.getInstance(),
            AllMembersWindow.getInstance(),
            AddMemberWindow.getInstance(),
            AllBooksWindow.getInstance(),
            AddBookWindow.getInstance(),
            AllCheckoutRecordWindow.getInstance(),
            AddCheckoutsWindow.getInstance(),
    };

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    public static final Color DARK_BLUE = Color.BLUE.darker();
    public static final Color ERROR_MESSAGE_COLOR = Color.RED.darker(); //dark red
    public static final Color INFO_MESSAGE_COLOR = new Color(24, 98, 19); //dark green
    public static final Color LINK_AVAILABLE = DARK_BLUE;
    public static final Color LINK_NOT_AVAILABLE = Color.gray;
    //rgb(18, 75, 14)

    public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
    }

    public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
        if (bigger) {
            Font f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() + 2));
            label.setFont(f);
        } else {
            Font f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() - 2));
            label.setFont(f);
        }
        label.setForeground(color);

    }

    /**
     * Sorts a list of numeric strings in natural number order
     */
    public static List<String> numericSort(List<String> list) {
        Collections.sort(list, new NumericSortComparator());
        return list;
    }

    static class NumericSortComparator implements Comparator<String> {
        @Override
        public int compare(String s, String t) {
            if (!isNumeric(s) || !isNumeric(t))
                throw new IllegalArgumentException("Input list has non-numeric characters");
            int sInt = Integer.parseInt(s);
            int tInt = Integer.parseInt(t);
            if (sInt < tInt) return -1;
            else if (sInt == tInt) return 0;
            else return 1;
        }
    }

    public static boolean isNumeric(String s) {
        if (s == null) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    public static String getRandom(String prefix) {
        int random = new Random().nextInt(1000000, 9999999);
        return prefix + random;
    }

    public static String getRandom() {
        return String.valueOf(new Random().nextInt(1000000, 9999999));
    }

    public void validate(String input, ValidationRule rule) {

    }
}
