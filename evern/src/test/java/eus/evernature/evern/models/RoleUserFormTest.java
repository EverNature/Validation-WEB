package eus.evernature.evern.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import eus.evernature.evern.models.forms.RoleUserForm;


@SpringBootTest
class RoleUserFormTest {

    @Test
    void roleUserFormEqualsIsOk() throws Exception {
        RoleUserForm roleUserForm = new RoleUserForm();
        roleUserForm.setRoleName("a");
        roleUserForm.setUsername("a");

        assertTrue("Equals not working", roleUserForm.equals(roleUserForm));
    }

    @Test
    void roleUserFormEqualsIsNok() throws Exception {
        RoleUserForm roleUserForm = new RoleUserForm();
        roleUserForm.setRoleName("a");
        roleUserForm.setUsername("a");

        RoleUserForm roleUserForm2 = new RoleUserForm();
        roleUserForm2.setRoleName("a");
        roleUserForm2.setUsername("b");

        assertFalse("Equals not working", roleUserForm.equals(roleUserForm2));

        roleUserForm2.setRoleName("b");

        assertFalse("Equals not working", roleUserForm.equals(roleUserForm2));
    }

    @Test
    void roleUserHaFormshCodeIsOk() throws Exception {
        RoleUserForm roleUserForm = new RoleUserForm();
        roleUserForm.setRoleName("a");
        roleUserForm.setUsername("a");

        assertEquals(roleUserForm.hashCode(), roleUserForm.hashCode());
    }

    @Test
    void roleUserToFormstringIsOk() throws Exception {
        RoleUserForm roleUserForm = new RoleUserForm();
        roleUserForm.setRoleName("a");
        roleUserForm.setUsername("a");

        assertEquals(roleUserForm.toString(), roleUserForm.toString());
    }
}
