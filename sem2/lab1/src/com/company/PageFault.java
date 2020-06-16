package com.company;
/* It is in this file, specifically the replacePage function that will
   be called by MemoryManagement when there is a page fault.  The 
   users of this program should rewrite PageFault to implement the 
   page replacement algorithm.
*/

import java.util.*;

public class PageFault {
    /**
     * @param mem            is the vector which contains the contents of the pages
     *                       in memory being simulated.  mem should be searched to find the
     *                       proper page to remove, and modified to reflect any changes.
     * @param replacePageNum is the requested page which caused the
     *                       page fault.
     * @param controlPanel   represents the graphical element of the
     *                       simulator, and allows one to modify the current display.
     */
    public static void replacePage(Vector<Page> mem, int replacePageNum, ControlPanel controlPanel,
                                   int time, int threshold) {
        System.out.println("replacePage " + replacePageNum + " " + time + " " + threshold);
        Page oldPage = null;
        boolean found = false;
        for (Page page : mem) {
            if (page.physical != -1) {
                if (!found) {
                    System.out.println("page: " + page.id + " " + page.R + " " + page.M + " " + page.lastUseTime);
                    boolean update = false;
                    if (oldPage == null)
                    {
                        update = true;
                        System.out.println(1);
                    }
                    else if(oldPage.R == 1)
                    {
                        if(page.R == 0)
                        {
                            update = true;
                            System.out.println(2);
                        }
                        else if (oldPage.M == 1 && page.M == 0)
                        {
                            update = true;
                            System.out.println(3);
                        }
                    }
                    else if(page.R == 0)
                    {
                        if(page.lastUseTime < oldPage.lastUseTime)
                        {
                            update = true;
                            System.out.println(4);
                        }
                    }
                    if(update)
                    {
                        oldPage = page;
                        int age = time - page.lastUseTime;
                        if (page.R == 0 && age > threshold)
                            found = true;
                    }
                }
            }
        }
        assert oldPage != null;

        Page nextPage = mem.elementAt(replacePageNum);
        nextPage.physical = oldPage.physical;
        controlPanel.addPhysicalPage(replacePageNum, nextPage.physical);

        controlPanel.removePhysicalPage(oldPage.id);
        oldPage.lastUseTime = 0;
        oldPage.R = 0;
        oldPage.M = 0;
        oldPage.physical = -1;

    }
}
