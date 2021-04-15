package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GroupManagerTest {

    @Test
    void TestCreateGroup() {
        GroupManager gm = new GroupManager();
        gm.createGroup();

        //id 1 is equal to id of first new group
        assertEquals(1, gm.findFromIndex(0).getId());
    }

    @Test
    void TestDeleteGroup() {
        GroupManager gm = new GroupManager();
        gm.createGroup();
        gm.createGroup();
        gm.createGroup();
        gm.deleteGroup(3);

        //next two method calls should not have any impact on groups
        gm.deleteGroup(-1);
        gm.deleteGroup(10);

        //third group has been deleted, so size  = 2
        assertEquals(null, gm.findFromIndex(2));

        //first group id = 1
        assertEquals(1, gm.findFromIndex(0).getId());

        //second group id = 2
        assertEquals(2, gm.findFromIndex(1).getId());
    }

    @Test
    void TestAddEdits() {
        GroupManager gm = new GroupManager();
        EditManager em = new EditManager();

        gm.createGroup();
        gm.createGroup();
        gm.createGroup();

        em.insert(0, "h");
        em.insert(1, "i");


        gm.addEdits(1, new int[]{1, 2});

        //following 4 method calls shoudln't have any impact on the groups
        gm.addEdits(1, new int[]{-1, 2});
        gm.addEdits(-1, new int[]{1, 2});
        gm.addEdits(22, new int[]{1, 2});
        gm.addEdits(1, new int[]{30, 2});

        //verify the right elements and the right size of edits in group
        assertEquals(1, gm.findFromIndex(0).getEdits().get(0));
        assertEquals(2, gm.findFromIndex(0).getEdits().get(1));
        assertEquals(2, gm.findFromIndex(0).getEdits().size());
    }
}