package com.khomandiak.courseWork;

public class MazeStringGenerator {

    private static final String NL = System.getProperty("line.separator");

    private MazeStringGenerator() {
    }

    public static String getMazeString1() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#############" + NL);
        stringBuilder.append("   S    #   #" + NL);
        stringBuilder.append("####### # # #" + NL);
        stringBuilder.append("#       # #G#" + NL);
        stringBuilder.append("# ####### ###" + NL);
        stringBuilder.append("#       #   #" + NL);
        stringBuilder.append("#### # #### #" + NL);
        stringBuilder.append("#    #    # #" + NL);
        stringBuilder.append("# ####### # #" + NL);
        stringBuilder.append("#       #    " + NL);
        stringBuilder.append("#############" + NL);
        return stringBuilder.toString();
    }

    public static String getMazeSolutionString1() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#############" + NL);
        stringBuilder.append("   S****#***#" + NL);
        stringBuilder.append("#######*#*#*#" + NL);
        stringBuilder.append("#*******#*#G#" + NL);
        stringBuilder.append("#*#######*###" + NL);
        stringBuilder.append("#****** #***#" + NL);
        stringBuilder.append("#### #*####*#" + NL);
        stringBuilder.append("#    #****#*#" + NL);
        stringBuilder.append("# #######*#*#" + NL);
        stringBuilder.append("#       #*** " + NL);
        stringBuilder.append("#############" + NL);
        return stringBuilder.toString();
    }

    public static String getImpossibleMazeString1() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#############" + NL);
        stringBuilder.append("   S    #   #" + NL);
        stringBuilder.append("####### # ###" + NL);
        stringBuilder.append("#       # #G#" + NL);
        stringBuilder.append("# ####### ###" + NL);
        stringBuilder.append("#       #   #" + NL);
        stringBuilder.append("#### # #### #" + NL);
        stringBuilder.append("#    #    # #" + NL);
        stringBuilder.append("# ####### # #" + NL);
        stringBuilder.append("#       #    " + NL);
        stringBuilder.append("#############" + NL);
        return stringBuilder.toString();
    }

}

