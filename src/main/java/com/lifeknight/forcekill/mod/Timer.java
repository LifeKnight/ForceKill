package com.lifeknight.forcekill.mod;

import net.minecraft.util.EnumChatFormatting;

import static com.lifeknight.forcekill.mod.Mod.*;

public class Timer {
    private int milliseconds = 0;
    private int seconds;
    private int minutes;
    private int hours;
    private int days;
    public boolean running = false;
    public boolean ended = false;

    public Timer(int seconds) {
        this.seconds = seconds % 60;
        this.minutes = seconds / 60;
        this.hours = 0;
        this.days = 0;
    }

    public Timer() {
        this(0);
    }

    public void start() {
        running = true;
        count();
    }

    public void count() {
        THREAD_POOL.submit(() -> {
           while (running) {
               if (ended) {
                   break;
               }
                   try {
                       Thread.sleep(5);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

                   milliseconds -= 5;

                   if (milliseconds == -5) {
                       milliseconds = 990;
                       seconds--;
                   }

                   if (seconds == -1) {
                       seconds = 59;
                       minutes--;
                   }

                   if (minutes == -1) {
                       minutes = 59;
                       hours--;
                   }

                   if (hours == -1) {
                       hours = 23;
                       days--;
                   }

               if (runMod.getValue() && onHypixel) {
                   if (milliseconds == 0) {
                       if (minutes > 1 && seconds == 0) {
                           Utils.addChatMessage(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + minutes + EnumChatFormatting.RESET +
                                   "" + EnumChatFormatting.YELLOW + " minutes remain.");
                           playDing();
                       } else if (minutes == 1 && seconds == 0) {
                           Utils.addChatMessage(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + minutes + EnumChatFormatting.RESET +
                                   "" + EnumChatFormatting.YELLOW + " minute remains.");
                           playDing();
                       } else if (minutes == 0) {
                           if ((seconds == 30 && timeToKill.getValue() != 30) || (seconds == 15 && timeToKill.getValue() != 15)) {
                               Utils.addChatMessage(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + seconds + EnumChatFormatting.RESET +
                                       "" + EnumChatFormatting.YELLOW + " seconds remain.");
                               playDing();
                           } else if (seconds != 0 && seconds <= 10 && timeToKill.getValue() != seconds) {
                               Utils.addChatMessage(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + seconds);
                               playDing();
                           }
                       }
                   }
               }

                   if (milliseconds == 0 && seconds == 0 && minutes == 0 && hours == 0 && days == 0) {
                       end();
                       break;
                   }
               }
        });
    }

    public void end() {
        ended = true;
    }

    public void toggle() {
        running = !running;

        if (running) {
            count();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean hasEnded() {
        return ended;
    }

    public String getFormattedTime() {
        return appendTime(minutes) + ":" +
                appendTime(seconds) + "." +
                formatMilliseconds();
    }

    public String appendTime(int timeValue) {
        StringBuilder result = new StringBuilder();
        if (timeValue > 9) {
            result.append(timeValue);
        } else {
            result.append("0").append(timeValue);
        }
        return result.toString();
    }

    public String formatMilliseconds() {
        String asString = String.valueOf(milliseconds);

        if (asString.length() == 1) {
            return "00" + milliseconds;
        } else if (asString.length() == 2) {
            return "0" + milliseconds;
        }
        return asString;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}
